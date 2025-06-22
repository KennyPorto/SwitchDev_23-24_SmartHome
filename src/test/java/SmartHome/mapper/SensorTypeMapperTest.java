package SmartHome.mapper;

import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.sensortype.SensorTypeFactory;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;
import SmartHome.persistence.jpa.datamodel.SensorTypeDataModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorTypeMapperTest {
    @Test
    void sensorTypeToDto() {
        // Arrange
        String id = "st1";
        MeasurementUnit measurementUnit = MeasurementUnit.Percentage;
        SensorTypeId sensorTypeId = new SensorTypeId(id);

        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();
        SensorType sensorType = sensorTypeFactory.createSensorType(sensorTypeId, measurementUnit);

        // Act
        SensorTypeDTO expected = SensorTypeMapper.sensorTypeToDto(sensorType);
        SensorTypeDTO result = new SensorTypeDTO(id, measurementUnit.toString());

        // Assert
        assertEquals(expected.id, result.id);
        assertEquals(expected.measurementUnit, result.measurementUnit);
    }

    @Test
    void listSensorTypeToDto() {
        // Arrange
        String id = "st1";
        MeasurementUnit measurementUnit = MeasurementUnit.Percentage;
        SensorTypeId sensorTypeId = new SensorTypeId(id);

        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();
        SensorType sensorType = sensorTypeFactory.createSensorType(sensorTypeId, measurementUnit);

        Iterable<SensorType> sensorTypes = List.of(sensorType);

        // Act
        Iterable<SensorTypeDTO> expected = SensorTypeMapper.sensorTypesToDto(sensorTypes);
        Iterable<SensorTypeDTO> result = List.of(new SensorTypeDTO(id, measurementUnit.toString()));

        // Assert
        assertEquals(expected.iterator().next().id, result.iterator().next().id);
        assertEquals(expected.iterator().next().measurementUnit, result.iterator().next().measurementUnit);
    }


    @Test
    void sensorTypeDataModelToDomain()
    {
        // Arrange
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();
        SensorType sensorType = sensorTypeFactory.createSensorType(new SensorTypeId("st1"), MeasurementUnit.Percentage);

        SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(sensorType);

        // Act
        SensorType result = SensorTypeMapper.sensorTypeDataModelToDomain(sensorTypeDataModel);

        // Assert
        assertEquals("st1", result.identity().toString());
        assertEquals(MeasurementUnit.Percentage.toString(), result.getMeasurementUnit().toString());
    }

    @Test
    void sensorTypesDataModelToDomain()
    {
        // Arrange
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();
        SensorType sensorType = sensorTypeFactory.createSensorType(new SensorTypeId("st1"), MeasurementUnit.Percentage);

        SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(sensorType);
        Iterable<SensorTypeDataModel> sensorTypeDataModels = List.of(sensorTypeDataModel);

        // Act
        Iterable<SensorType> result = SensorTypeMapper.sensorTypesDataModelToDomain(sensorTypeDataModels);

        // Assert
        assertEquals("st1", result.iterator().next().identity().toString());
        assertEquals(MeasurementUnit.Percentage.toString(), result.iterator().next().getMeasurementUnit().toString());
    }
}