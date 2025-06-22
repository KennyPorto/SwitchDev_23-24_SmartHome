package SmartHome.mapper;

import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.actuatortype.ActuatorTypeFactory;
import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.persistence.jpa.datamodel.ActuatorTypeDataModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActuatorTypeMapperTest {

    @Test
    void actuatorTypeToDTO() {
        // Arrange
        String name = "actuator1";
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);
        MeasurementUnit measurementUnit = MeasurementUnit.Binary;
        ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactory();
        ActuatorType actuatorType = actuatorTypeFactory.createActuatorType(actuatorTypeId, measurementUnit);

        // Act
        ActuatorTypeDTO expected = ActuatorTypeMapper.actuatorTypeToDTO(actuatorType);
        ActuatorTypeDTO result = new ActuatorTypeDTO(name, measurementUnit.toString());

        // Assert
        assertEquals(expected.name, result.name);
        assertEquals(expected.measurementUnit, result.measurementUnit);
    }

    @Test
    void actuatorTypesToDTO() {
        // Arrange
        String name = "actuator1";
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);
        MeasurementUnit measurementUnit = MeasurementUnit.Binary;
        ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactory();
        ActuatorType actuatorType = actuatorTypeFactory.createActuatorType(actuatorTypeId, measurementUnit);
        Iterable<ActuatorType> actuatorTypes = List.of(actuatorType);

        // Act
        Iterable<ActuatorTypeDTO> expected = ActuatorTypeMapper.actuatorTypesToDTO(actuatorTypes);
        Iterable<ActuatorTypeDTO> result = List.of(new ActuatorTypeDTO(name, measurementUnit.toString()));

        // Assert
        assertEquals(expected.iterator().next().name, result.iterator().next().name);
        assertEquals(expected.iterator().next().measurementUnit, result.iterator().next().measurementUnit);
        assertTrue(expected.iterator().hasNext());
    }

    @Test
    void toDomain()
    {
        // Arrange
        String name = "blindRoller";
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);
        MeasurementUnit measurementUnit = MeasurementUnit.Percentage;
        ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactory();
        ActuatorType actuatorType = actuatorTypeFactory.createActuatorType(actuatorTypeId, measurementUnit);
        ActuatorTypeDataModel dataModel = new ActuatorTypeDataModel(actuatorType);

        // Act
        ActuatorType expected = ActuatorTypeMapper.toDomain(dataModel);

        // Assert
        assertEquals(expected.identity().name, actuatorType.identity().name);
        assertEquals(expected.getMeasurementUnit(), actuatorType.getMeasurementUnit());
    }

    @Test
    void toDomainList()
    {
        // Arrange
        String name = "blindRoller";
        MeasurementUnit measurementUnit = MeasurementUnit.Percentage;
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);
        ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactory();
        ActuatorType actuatorType = actuatorTypeFactory.createActuatorType(actuatorTypeId, measurementUnit);
        ActuatorTypeDataModel dataModel = new ActuatorTypeDataModel(actuatorType);

        ActuatorTypeDataModel dataModel2 = new ActuatorTypeDataModel(actuatorType);

        Iterable<ActuatorTypeDataModel> dataModels = List.of(dataModel, dataModel2);

        // Act
        Iterable<ActuatorType> expected = ActuatorTypeMapper.toDomain(dataModels);

        // Assert
        assertEquals(expected.iterator().next().identity().name, actuatorType.identity().name);
        assertEquals(expected.iterator().next().getMeasurementUnit(), actuatorType.getMeasurementUnit());
        assertTrue(expected.iterator().hasNext());
    }

    @Test
    void getDomainActuatorTypeListFromActuatorTypeModelList() {
        // Arrange
        ActuatorTypeDataModel actuatorTypeDataModel = mock(ActuatorTypeDataModel.class);
        when(actuatorTypeDataModel.getId()).thenReturn("ActuatorTypeId");
        when(actuatorTypeDataModel.getMeasurementUnit()).thenReturn(MeasurementUnit.Celsius);

        List<ActuatorTypeDataModel> actuatorTypeDataModels = new ArrayList<>();
        actuatorTypeDataModels.add(actuatorTypeDataModel);

        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(actuatorTypeDataModel.getId());
        MeasurementUnit unit = MeasurementUnit.Celsius;
        ActuatorType expected = new ActuatorTypeFactory().createActuatorType(actuatorTypeId, unit);

        // Act
        Iterable<ActuatorType> domainTypes = ActuatorTypeMapper.toDomain(actuatorTypeDataModels);
        ActuatorType current = domainTypes.iterator().next();

        // Assert
        assertTrue(current.sameAs(expected));
    }

}
