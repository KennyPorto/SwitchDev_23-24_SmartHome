package SmartHome.service;

import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.device.Device;
import SmartHome.domain.repository.*;
import SmartHome.domain.room.Room;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.valueObjects.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Double.NaN;
import static java.lang.Double.parseDouble;

@Service
public class EnvironmentalTemperatureService
{
    private final RoomRepository roomRepository;
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRep;
    private final DeviceRepository deviceRepository;
    private final SensorActivityLogRepository sensorActivityLogRepository;

    public EnvironmentalTemperatureService(@Qualifier("roomRepoSpringDataImpl") RoomRepository roomRepository,
                                           @Qualifier("sensorRepoSpringDataImpl") SensorRepository sensorRepository,
                                           @Qualifier("sensorTypeRepoSpringDataImpl") SensorTypeRepository sensorTypeRep,
                                           @Qualifier("deviceRepoSpringDataImpl") DeviceRepository deviceRepository,
                                           @Qualifier("sensorActivityLogRepoSpringDataImpl") SensorActivityLogRepository sensorActivityLogRepository)
    {
        this.roomRepository = roomRepository;
        this.sensorRepository = sensorRepository;
        this.sensorTypeRep = sensorTypeRep;
        this.deviceRepository = deviceRepository;
        this.sensorActivityLogRepository = sensorActivityLogRepository;
    }

    public double getMaximumTemperatureDifference(long deviceID,
                                                  String startDate, String endDate, long maximumSecondsAllowedBetweenReadings)
    {
        validateArguments(deviceID, startDate, endDate);

        List<SensorActivityLog> indoorLogs = getTheExistingTemperatureSensorsInADeviceAndTheirLogsInTheGivenPeriod(deviceID, startDate, endDate);

        Room room = findRoomByDeviceId(deviceID);
        Iterable<Device> devicesInOutdoorRooms = findAllDevicesInOutdoorRooms(room);
        List<SensorActivityLog> outdoorLogs = getTheExistingTemperatureSensorsInAListOfDevicesAndTheirLogsInTheGivenPeriod(devicesInOutdoorRooms, startDate, endDate);

        return calculateMaximumTemperatureDifference(indoorLogs, outdoorLogs, maximumSecondsAllowedBetweenReadings);
    }

    private void validateArguments(long deviceID,
                                   String startDate, String endDate) {
        if (!isIndoorDevice(deviceID)) {
            throw new IllegalArgumentException("The device must be in an indoor room");
        }
        if (!validateTimePeriod(startDate, endDate)) {
            throw new IllegalArgumentException("The start date must be before the end date");
        }

    }

    private List<SensorActivityLog> getTheExistingTemperatureSensorsInADeviceAndTheirLogsInTheGivenPeriod(long deviceID, String startDate, String endDate) {
        Iterable<Sensor> indoorSensors = findAllTemperatureSensorByDeviceId(deviceID);
        if (!indoorSensors.iterator().hasNext()) {
            throw new IllegalArgumentException("There must be at least 1 indoor temperature sensor");
        }
        List<SensorActivityLog> indoorLogs = getLogsInAGivenTimePeriod(indoorSensors,
                ZonedDateTime.parse(startDate), ZonedDateTime.parse(endDate));
        if (!indoorLogs.iterator().hasNext()) {
            throw new IllegalArgumentException("There must be at least 1 indoor log in the given time period");
        }
        return indoorLogs;
    }

    private List<Device> findAllDevicesInOutdoorRooms(Room room) {
        Iterable<Room> outdoorRooms = roomRepository.findAllByHouseIdAndOutdoorIndoor(room.getHouseId().id,
                OutdoorIndoor.OUTDOOR);
        if (!outdoorRooms.iterator().hasNext()) {
            throw new IllegalArgumentException("There must be at least 1 outdoor room in the house");
        }
        List<Device> devices = findAllDevicesInARoomList(outdoorRooms);
        if (!devices.iterator().hasNext()) {
            throw new IllegalArgumentException("There must be at least 1 device in the outdoor room");
        }
        return devices;
    }

    private List<SensorActivityLog> getTheExistingTemperatureSensorsInAListOfDevicesAndTheirLogsInTheGivenPeriod(Iterable<Device> devicesInOutdoorRooms, String startDate, String endDate) {
        Iterable<Sensor> outdoorSensors = findAllTemperatureSensorInAListOfDevices(devicesInOutdoorRooms);
        if (!outdoorSensors.iterator().hasNext()) {
            throw new IllegalArgumentException("There must be at least 1 outdoor temperature sensor");
        }
        List<SensorActivityLog> outdoorLogs = getLogsInAGivenTimePeriod(outdoorSensors,
                ZonedDateTime.parse(startDate), ZonedDateTime.parse(endDate));
        if (!outdoorLogs.iterator().hasNext()) {
            throw new IllegalArgumentException("There must be at least 1 outdoor log in the given time period");
        }
        return outdoorLogs;
    }

    private List<Device> findAllDevicesInARoomList(Iterable<Room> rooms)
    {
        List<Device> devicesToReturn = new ArrayList<>();
        for (Room room : rooms) {
            RoomId roomId = room.identity();
            Iterable<Device> devices = deviceRepository.findAllByRoomId(roomId.id);
            for (Device device : devices) {
                devicesToReturn.add(device);
            }
        }
        return devicesToReturn;
    }

    private boolean isIndoorDevice(long deviceId)
    {
        DeviceID devId = new DeviceID(deviceId);
        Optional<Device> device = deviceRepository.findById(devId);
        if (device.isPresent()) {
            RoomId roomId = device.get().getRoomId();
            Optional<Room> room = roomRepository.findById(roomId);
            if (room.isPresent()) {
                OutdoorIndoor outdoorIndoor = room.get().getOutdoorIndoor();
                return outdoorIndoor.equals(OutdoorIndoor.INDOOR);
            }
        }
        return false;
    }

    private boolean validateTimePeriod(String startDate, String endDate)
    {
        ZonedDateTime start = ZonedDateTime.parse(startDate);
        ZonedDateTime end = ZonedDateTime.parse(endDate);
        return (start.isBefore(end));
    }

    private List<Sensor> findAllTemperatureSensorInAListOfDevices(Iterable<Device> devices)
    {
        List<Sensor> sensorsToReturn = new ArrayList<>();
        for (Device device : devices) {
            List<Sensor> sensors = findAllTemperatureSensorByDeviceId(device.identity().id);
            sensorsToReturn.addAll(sensors);
        }
        return sensorsToReturn;
    }

    private List<Sensor> findAllTemperatureSensorByDeviceId(long deviceId)
    {
        List<Sensor> temperatureSensors = new ArrayList<>();
        Iterable<Sensor> sensors = sensorRepository.findAllByDeviceId(deviceId);
        for (Sensor sensor : sensors) {
            SensorTypeId sensorTypeId = sensor.getSensorTypeId();
            Optional<SensorType> sensorType = sensorTypeRep.findById(sensorTypeId);
            if (sensorType.isPresent() && (sensorType.get().getMeasurementUnit().equals(MeasurementUnit.Celsius))) {
                    temperatureSensors.add(sensor);
            }
        }
        return temperatureSensors;
    }

    private List<SensorActivityLog> getLogsInAGivenTimePeriod(Iterable<Sensor> sensors, ZonedDateTime startDate, ZonedDateTime endDate)
    {
        List<SensorActivityLog> sensorActivityLogs = new ArrayList<>();

        for (Sensor sensor : sensors) {
            Iterable<SensorActivityLog> logs = sensorActivityLogRepository
                    .findAllBySensorIdAndTimestampBetween(sensor.identity().id, startDate, endDate);
            for (SensorActivityLog sensorActivity : logs) {
                sensorActivityLogs.add(sensorActivity);
            }
        }

        return sensorActivityLogs;
    }

    private Room findRoomByDeviceId(long deviceID)
    {
        DeviceID devId = new DeviceID(deviceID);
        Optional<Device> device = deviceRepository.findById(devId);
        if (device.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Optional<Room> room = roomRepository.findById(device.get().getRoomId());
        if (room.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return room.get();
    }
    public double calculateMaximumTemperatureDifference(List<SensorActivityLog> indoorSensors,
                                                        List<SensorActivityLog> outdoorSensors,
                                                        long maximumSecondsAllowedBetweenReadings) {
        int[] indoorAndOutdoorSensorsIndexes = getReadingsWithTheLeastTimeDifferenceBetweenThem(indoorSensors,
                outdoorSensors, maximumSecondsAllowedBetweenReadings);
        if (indoorAndOutdoorSensorsIndexes[0] == NaN) {
            throw new IllegalArgumentException("There are no valid readings to make the calculation");
        }

        return getTemperatureDifferenceBetweenTwoReadings(indoorSensors, outdoorSensors,
                indoorAndOutdoorSensorsIndexes);
    }

    public int[] getReadingsWithTheLeastTimeDifferenceBetweenThem(List<SensorActivityLog> indoorSensors,
                                                                  List<SensorActivityLog> outdoorSensors,
                                                                  long maximumSecondsAllowedBetweenReadings) {
        long minimumTimeDifferenceBetweenReadings = Long.MAX_VALUE;
        long maximumMillisecondsAllowedBetweenReadings = maximumSecondsAllowedBetweenReadings * 1000;
        int[] indoorAndOutdoorSensorsIndexes = new int[2];
        for (int indoorSensorLog = 0; indoorSensorLog < indoorSensors.size(); indoorSensorLog++) {
            for (int outdoorSensorLog = 0; outdoorSensorLog < outdoorSensors.size(); outdoorSensorLog++) {
                long timeDifferenceBetweenReadings = Math.abs(
                        indoorSensors.get(indoorSensorLog).getLogTime().value.toInstant().toEpochMilli()
                        - outdoorSensors.get(outdoorSensorLog).getLogTime().value.toInstant().toEpochMilli());
                if (timeDifferenceBetweenReadings > maximumMillisecondsAllowedBetweenReadings) {
                    continue;
                }
                if (timeDifferenceBetweenReadings < minimumTimeDifferenceBetweenReadings) {
                    minimumTimeDifferenceBetweenReadings = timeDifferenceBetweenReadings;
                    indoorAndOutdoorSensorsIndexes[0] = indoorSensorLog;
                    indoorAndOutdoorSensorsIndexes[1] = outdoorSensorLog;
                }
            }
        }
        return indoorAndOutdoorSensorsIndexes;
    }

    public double getTemperatureDifferenceBetweenTwoReadings(List<SensorActivityLog> indoorSensors,
                                                             List<SensorActivityLog> outdoorSensors,
                                                             int[] indoorAndOutdoorSensorsIndexes) {
        double firstReadingTemperature = parseDouble(
                indoorSensors.get(indoorAndOutdoorSensorsIndexes[0]).getMeasurement().measurement);
        double secondReadingTemperature = parseDouble(outdoorSensors.get(
                indoorAndOutdoorSensorsIndexes[1]).getMeasurement().measurement);

        return Math.abs(firstReadingTemperature - secondReadingTemperature);
    }
}