package SmartHome.controller.cli_controllers;

import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.valueObjects.DeviceID;
import SmartHome.mapper.*;
import SmartHome.service.DeviceLogService;
import org.apache.commons.lang3.tuple.Pair;

import java.time.ZonedDateTime;

public class Ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod
{
    private final DeviceLogService _deviceLogService;

    public Ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod(DeviceLogService deviceLogService)
    {
        this._deviceLogService = deviceLogService;
    }

    public DeviceRecordsDTO getAllLogs(long deviceId, TimePeriodDTO dto)
    {
        ZonedDateTime start = ZonedDateTime.parse(dto.startDate);
        ZonedDateTime end = ZonedDateTime.parse(dto.endDate);
        DeviceID deviceIdObj = new DeviceID(deviceId);
        Pair<Iterable<ActuatorActivityLog>, Iterable<SensorActivityLog>> result =
                _deviceLogService.getAllLogsFromDevice(deviceIdObj, start, end);

        Iterable<ActuatorActivityLogDTO> activityLogDtoList = ActivityLogMapper.toDTOs(result.getLeft());
        Iterable<SensorActivityLogDTO> sensorActivityLogDtoList = ActivityLogMapper.sensorLogAndMeasurementToDto(result.getRight());

        return new DeviceRecordsDTO(activityLogDtoList, sensorActivityLogDtoList);
    }

}
