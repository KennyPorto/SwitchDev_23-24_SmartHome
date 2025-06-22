package SmartHome.mapper;

import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.actuatortype.ActuatorTypeFactory;
import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.persistence.jpa.datamodel.ActuatorTypeDataModel;

import java.util.ArrayList;
import java.util.List;

public class ActuatorTypeMapper {
    public static ActuatorTypeDTO actuatorTypeToDTO(ActuatorType actuatorType) {
        return new ActuatorTypeDTO(actuatorType.identity().name, actuatorType.getMeasurementUnit().toString());
    }

    public static Iterable<ActuatorTypeDTO> actuatorTypesToDTO(Iterable<ActuatorType> actuatorTypes) {
        List<ActuatorTypeDTO> actuatorTypesDTO = new ArrayList<>();
        for (ActuatorType actuatorType : actuatorTypes) {
            actuatorTypesDTO.add(actuatorTypeToDTO(actuatorType));
        }
        return actuatorTypesDTO;
    }

    static public ActuatorType toDomain(ActuatorTypeDataModel dataModel)
    {
        ActuatorTypeId id = new ActuatorTypeId(dataModel.getId());
        MeasurementUnit unit = dataModel.getMeasurementUnit();
        return new ActuatorTypeFactory().createActuatorType(id, unit);
    }

    static public Iterable<ActuatorType> toDomain(Iterable<ActuatorTypeDataModel> dataModels)
    {
        List<ActuatorType> actuators = new ArrayList<>();
        dataModels.forEach(
              dataModel -> actuators.add(toDomain(dataModel))
        );
        return actuators;
    }
}
