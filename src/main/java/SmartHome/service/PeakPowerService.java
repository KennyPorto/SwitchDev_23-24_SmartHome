package SmartHome.service;

import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.device.Device;
import SmartHome.domain.repository.*;
import SmartHome.domain.room.Room;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.HouseId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PeakPowerService {
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;
    private final SensorActivityLogRepository sensorActivityLogRepository;
    private final static String POWER_METER = "Power Meter";
    private final static String SENSOR_TYPE_ID = "Instant Power";
    private final static long SEC_TO_EPOCH_MILLIS = 1000;
    private final static int DELTA_MIN_SECONDS = 0;
    private final static int DELTA_MAX_SECONDS = 900;
    private List<SensorActivityLog> meterLogsCombinedList = new ArrayList<>();
    private List<SensorActivityLog> consumptionLogsCombinedList = new ArrayList<>();

    public PeakPowerService(@Qualifier("houseRepoSpringDataImpl") HouseRepository houseRepository,
                            @Qualifier("roomRepoSpringDataImpl") RoomRepository roomRepository,
                            @Qualifier("deviceRepoSpringDataImpl") DeviceRepository deviceRepository,
                            @Qualifier("sensorRepoSpringDataImpl") SensorRepository sensorRepository,
                            @Qualifier("sensorActivityLogRepoSpringDataImpl") SensorActivityLogRepository sensorActivityLogRepository) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
        this.sensorActivityLogRepository = sensorActivityLogRepository;
    }

    public double findPeakPower(HouseId houseId, ZonedDateTime start, ZonedDateTime end, long delta) {
        validateInputs(houseId, delta);

        Iterable<Room> rooms = roomRepository.findAllByHouseId(houseId.id);

        for (Room room : rooms) {
            Iterable<Device> devices = deviceRepository.findAllByRoomId(room.identity().id);
            for (Device device : devices) {
                Iterable<Sensor> instantPowerSensors = sensorRepository
                        .findAllByDeviceIdAndSensorTypeId(device.identity().id, SENSOR_TYPE_ID);

                Iterable<Sensor> meterSensors = sensorRepository
                        .findAllByDeviceIdAndSensorTypeId(device.identity().id, POWER_METER);

                findPowerSensorsAndFillCombinedLists(start, end, instantPowerSensors);
                findPowerSensorsAndFillCombinedLists(start, end, meterSensors);
            }

        }

        return getInstantPowerConsumption(consumptionLogsCombinedList, meterLogsCombinedList, delta);
    }

    private void findPowerSensorsAndFillCombinedLists(ZonedDateTime start, ZonedDateTime end,
                                                      Iterable<Sensor> instantPowerSensors) {
        for (Sensor sensor : instantPowerSensors) {
            fetchAndAddLogsToCombinedList(sensor, start, end, false);
            if (sensor.getSensorTypeId().id.equals(POWER_METER)) {
                fetchAndAddLogsToCombinedList(sensor, start, end, true);
            }
        }
    }

    private void validateInputs(HouseId houseId, long delta) {
        if (delta > DELTA_MAX_SECONDS || delta < DELTA_MIN_SECONDS) {
            throw new IllegalArgumentException("Maximum delta is 900 seconds (15 minutes) " +
                    "or delta can't be less than zero");
        }

        if (!houseRepository.existsById(houseId))
            throw new IllegalArgumentException("House id doesn't exists");
    }

    private void fetchAndAddLogsToCombinedList(Sensor sensor, ZonedDateTime start, ZonedDateTime end,
                                               boolean isMeterLog) {
        Iterable<SensorActivityLog> logs =
                sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(sensor.identity().id, start, end);

        for (SensorActivityLog log : logs) {
            if (isMeterLog) {
                meterLogsCombinedList.add(log);
                continue;
            }
            consumptionLogsCombinedList.add(log);
        }
    }

    protected double getInstantPowerConsumption(List<SensorActivityLog> consumptionLogs,
                                                List<SensorActivityLog> meterLogs,
                                                long delta) {
        double max = 0;

        for (SensorActivityLog meterLog : meterLogs) {
            long meterLogTimeMs = meterLog.getLogTime().value.toInstant().toEpochMilli();
            for (SensorActivityLog consumptionLog : consumptionLogs) {
                long powerLogTimeMs = consumptionLog.getLogTime().value.toInstant().toEpochMilli();
                if (Math.abs(meterLogTimeMs - powerLogTimeMs) > delta * SEC_TO_EPOCH_MILLIS) continue;

                double result = Double.parseDouble(meterLog.getMeasurement().measurement)
                        + Double.parseDouble(consumptionLog.getMeasurement().measurement);

                max = Math.max(max, result);

                break;
            }
        }

        consumptionLogsCombinedList = new ArrayList<>();
        meterLogsCombinedList = new ArrayList<>();

        return max;
    }
}
