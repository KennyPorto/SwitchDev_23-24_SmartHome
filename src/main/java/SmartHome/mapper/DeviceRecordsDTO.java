package SmartHome.mapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceRecordsDTO {
    public final Iterable<ActuatorActivityLogDTO> actuatorLogs;
    public final Iterable<SensorActivityLogDTO> sensorLogs;
}
