package SmartHome.service;

import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.repository.*;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.DeviceID;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceLogService {
    private final DeviceRepository _deviceRepository;
    private final SensorRepository _sensorRepository;
    private final ActuatorRepository _actuatorRepository;
    private final SensorActivityLogRepository _sensorActivityLogRepository;
    private final ActuatorActivityLogRepository _actuatorActivityLogRepository;

    public DeviceLogService(@Qualifier("deviceRepoSpringDataImpl") DeviceRepository deviceRepository,
                            @Qualifier("sensorRepoSpringDataImpl") SensorRepository sensorRepository,
                            @Qualifier("actuatorRepoSpringDataImpl") ActuatorRepository actuatorRepository,
                            @Qualifier("sensorActivityLogRepoSpringDataImpl") SensorActivityLogRepository
                                    sensorActivityLogRepository,
                            @Qualifier("actuatorActivityLogRepoSpringDataImpl") ActuatorActivityLogRepository
                                    activityLogRepository)
    {
        this._deviceRepository = deviceRepository;
        this._sensorRepository = sensorRepository;
        this._actuatorRepository = actuatorRepository;
        this._sensorActivityLogRepository = sensorActivityLogRepository;
        this._actuatorActivityLogRepository = activityLogRepository;
    }

    public Pair<Iterable<ActuatorActivityLog>, Iterable<SensorActivityLog>> getAllLogsFromDevice(DeviceID deviceId,
                                                                                                 ZonedDateTime start,
                                                                                                 ZonedDateTime end)
    {
        if (!_deviceRepository.existsById(deviceId))
        {
            throw new IllegalArgumentException("Device not found or Device don't exist in room");
        }

        List<SensorActivityLog> sensorLogsFromDevice = getAllSensorLogsFromDevice(deviceId.id, start, end);
        List<ActuatorActivityLog> actuatorLogsFromDevice = getAllActuatorLogsFromDevice(deviceId.id, start, end);

        return Pair.of(actuatorLogsFromDevice, sensorLogsFromDevice);
    }

    private List<SensorActivityLog> getAllSensorLogsFromDevice(long deviceId, ZonedDateTime startDate,
                                                               ZonedDateTime endDate)
    {
        List<SensorActivityLog> sensorData = new ArrayList<>();
        Iterable<Sensor> sensors = _sensorRepository.findAllByDeviceId(deviceId);

        for (Sensor sensor : sensors)
        {
            Iterable<SensorActivityLog> readings = _sensorActivityLogRepository
                    .findAllBySensorIdAndTimestampBetween(sensor.identity().id, startDate, endDate);

            for (SensorActivityLog reading : readings)
            {
                sensorData.add(reading);
            }
        }

        return sensorData;
    }

    private List<ActuatorActivityLog> getAllActuatorLogsFromDevice(long deviceId, ZonedDateTime startDate,
                                                                   ZonedDateTime endDate)
    {
        Iterable<Actuator> actuators = _actuatorRepository.findAllByDeviceId(deviceId);
        List<ActuatorActivityLog> actuatorData = new ArrayList<>();

        for (Actuator actuator : actuators)
        {
            Iterable<ActuatorActivityLog> readings = _actuatorActivityLogRepository
                    .findAllByActuatorIdAndTimestampBetween(actuator.identity().id, startDate, endDate);

            for (ActuatorActivityLog reading : readings)
            {
                actuatorData.add(reading);
            }
        }

        return actuatorData;
    }
}
