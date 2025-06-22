package SmartHome.controller.cli_controllers;

import SmartHome.domain.sensortype.SensorTypeFactory;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;
import SmartHome.mapper.SensorTypeDTO;
import SmartHome.persistence.mem.SensorTypeRepositoryImplMem;
import SmartHome.service.SensorTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Ctrl04AddSensorTypeTest
{
    private Ctrl04AddSensorType ctrl04;

    @BeforeEach
    void setup() {
        SensorTypeRepositoryImplMem sensorTypeRepositoryImplMem = new SensorTypeRepositoryImplMem();
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();
        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryImplMem, sensorTypeFactory);
        ctrl04 = new Ctrl04AddSensorType(service);
    }

    @Test
    void addSensorType_success() {
        // Arrange
        String id = "st1";
        String measurementUnit = "Percentage";
        SensorTypeId sensorTypeId = new SensorTypeId(id);
        MeasurementUnit measurementUnitObj = MeasurementUnit.Percentage;
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(id, measurementUnit);

        // Arrange
        String result = ctrl04.addSensorType(sensorTypeId, measurementUnitObj).id;
        String expected = sensorTypeDTO.id;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void addSensorType_failForDuplication1() {
        // Arrange
        String id = "st1";
        SensorTypeId sensorTypeId = new SensorTypeId(id);
        MeasurementUnit measurementUnit = MeasurementUnit.Percentage;
        ctrl04.addSensorType(sensorTypeId, measurementUnit);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> ctrl04.addSensorType(sensorTypeId, measurementUnit));
    }


    @Test
    void getAllSensorTypes_emptyList() {
        // Act
        Iterable<SensorTypeDTO> result = ctrl04.getAllSensorTypes();
        Iterable<SensorTypeDTO> expected = List.of();

        // Assert
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    void getAllSensorTypes_notEmptyList() {
        // Arrange
        String id = "st1";
        String measurementUnit = "Percentage";
        SensorTypeId sensorTypeId = new SensorTypeId(id);
        MeasurementUnit measurementUnitObj = MeasurementUnit.Percentage;
        ctrl04.addSensorType(sensorTypeId, measurementUnitObj);

        // Act
        Iterable<SensorTypeDTO> result = ctrl04.getAllSensorTypes();
        Iterable<SensorTypeDTO> expected = List.of(new SensorTypeDTO(id, measurementUnit));

        // Assert
        assertEquals(expected.iterator().next().measurementUnit, result.iterator().next().measurementUnit);
    }
}