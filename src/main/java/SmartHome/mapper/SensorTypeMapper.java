package SmartHome.mapper;


import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.sensortype.SensorTypeFactory;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;
import SmartHome.persistence.jpa.datamodel.SensorTypeDataModel;

import java.util.ArrayList;
import java.util.List;

public class SensorTypeMapper {
    public static SensorTypeDTO sensorTypeToDto(SensorType sensorType) {
        return new SensorTypeDTO(sensorType.identity().id, sensorType.getMeasurementUnit().toString());
    }

    public static Iterable<SensorTypeDTO> sensorTypesToDto(Iterable<SensorType> sensorTypes) {
        List<SensorTypeDTO> sensorTypesDto = new ArrayList<>();
        for (SensorType st : sensorTypes) {
            sensorTypesDto.add(sensorTypeToDto(st));
        }

        return sensorTypesDto;
    }
    public static Iterable<SensorType> sensorTypesDataModelToDomain(Iterable<SensorTypeDataModel> sensorTypesDataModels) {
        List<SensorType> sensorTypes = new ArrayList<>();
        sensorTypesDataModels.forEach(
                sensorTypeDataModel -> sensorTypes.add(sensorTypeDataModelToDomain(sensorTypeDataModel))
        );
        return sensorTypes;
    }

    public static SensorType sensorTypeDataModelToDomain(SensorTypeDataModel sensorTypeDataModel) {
        SensorTypeId sensorTypeId = new SensorTypeId(sensorTypeDataModel.getId());
        MeasurementUnit measurementUnit = sensorTypeDataModel.getMeasurementUnit();
        return new SensorTypeFactory().createSensorType(sensorTypeId, measurementUnit);
    }
}
