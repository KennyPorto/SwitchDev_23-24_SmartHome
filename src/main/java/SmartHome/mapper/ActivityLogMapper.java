package SmartHome.mapper;

import SmartHome.domain.activitylog.ActivityLogFactory;
import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.jpa.datamodel.ActuatorActivityLogDataModel;
import SmartHome.persistence.jpa.datamodel.SensorActivityLogDataModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityLogMapper
{
    public static ActuatorActivityLogDTO toDTO(ActuatorActivityLog actuatorActivityLog)
    {
        return new ActuatorActivityLogDTO(
                actuatorActivityLog.identity().id,
                actuatorActivityLog.getActuatorId().id,
                actuatorActivityLog.getLogTime().value.toString(),
                actuatorActivityLog.getMeasurement().measurement
        );
    }

    public static Iterable<ActuatorActivityLogDTO> toDTOs(Iterable<ActuatorActivityLog> actuatorActivityLogs)
    {
        List<ActuatorActivityLogDTO> actuatorActivityLogsDTOS = new ArrayList<>();
        actuatorActivityLogs.forEach(
                actuatorActivityLog -> actuatorActivityLogsDTOS.add(
                        toDTO(actuatorActivityLog)
                ));
        return actuatorActivityLogsDTOS;
    }

    public static ActuatorActivityLog toDomain(ActuatorActivityLogDataModel model)
    {
        ActuatorId actuatorId = new ActuatorId(model.getActuatorId());
        ActivityLogId activityLogId = new ActivityLogId(model.getActivityLogId());
        TimeStamp timeStamp = new TimeStamp(model.getTimeStamp());
        Measurement measurement = new Measurement(model.getMeasurement());
        return new ActivityLogFactory().createActuatorActivityLog(activityLogId, actuatorId, timeStamp, measurement).orElse(null);
    }

    public static Iterable<ActuatorActivityLog> toDomain(Iterable<ActuatorActivityLogDataModel> dataModels)
    {
        List<ActuatorActivityLog> activityLogs = new ArrayList<>();
        dataModels.forEach(
                dataModel -> activityLogs.add(toDomain(dataModel))
        );
        return activityLogs;
    }

    public static SensorActivityLog sensorDataModelToDomain(SensorActivityLogDataModel model)
    {
        SensorId sensorId = new SensorId(model.getSensorId());
        ActivityLogId activityLogId = new ActivityLogId(model.getSensorActivityLogId());
        TimeStamp timeStamp = new TimeStamp(model.getTimeStamp());
        Measurement measurement = new Measurement(model.getMeasurement());
        return new ActivityLogFactory().createSensorActivityLog(activityLogId, sensorId, timeStamp, measurement).orElse(null);
    }

    public static Iterable<SensorActivityLog> sensorsDataModelToDomain(Iterable<SensorActivityLogDataModel> dataModels)
    {
        List<SensorActivityLog> activityLogs = new ArrayList<>();
        dataModels.forEach(
                dataModel -> activityLogs.add(sensorDataModelToDomain(dataModel))
        );
        return activityLogs;
    }

    public static SensorActivityLogDTO sensorToDTO(SensorActivityLog sensorActivityLog)
    {
        return new SensorActivityLogDTO(
                sensorActivityLog.identity().id,
                sensorActivityLog.getSensorId().id,
                sensorActivityLog.getLogTime().value.toString(),
                sensorActivityLog.getMeasurement().measurement
        );
    }


    public static Iterable<SensorActivityLogDTO> sensorsToDTO(Iterable<SensorActivityLog> sensorActivityLogs)
    {
        List<SensorActivityLogDTO> sensorActivityLogsDTO = new ArrayList<>();
        sensorActivityLogs.forEach(
                sensorActivityLog -> sensorActivityLogsDTO.add(
                        sensorToDTO(sensorActivityLog)
                ));
        return sensorActivityLogsDTO;
    }

    public static List<SensorActivityLogDTO> sensorLogAndMeasurementToDto(Iterable<SensorActivityLog> data)
    {
        List<SensorActivityLogDTO> response = new ArrayList<>();
        for (SensorActivityLog reading : data) {
            response.add(new SensorActivityLogDTO(reading.identity().id, reading.getSensorId().id,
                    reading.getLogTime().value.toString(), reading.getMeasurement().measurement));
        }
        return response;
    }

    public static List<ActuatorActivityLogDTO> actuatorLogAndMeasurementToDto(Iterable<ActuatorActivityLog> data)
    {
        List<ActuatorActivityLogDTO> response = new ArrayList<>();
        for (ActuatorActivityLog reading : data) {
            response.add(new ActuatorActivityLogDTO(reading.identity().id, reading.getActuatorId().id,
                    reading.getLogTime().value.toString(), reading.getMeasurement().measurement));
        }
        return response;
    }
}
