package SmartHome.domain.sensortype;

import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SensorTypeTest {
    @Test
    void createSensorType() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);

        // Act
        SensorType sensorType = new SensorType(sensorTypeIdDouble, MeasurementUnit.Percentage);

        // Assert
        assertNotNull(sensorType);
    }

    @Test
    void createSensorType_identity() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorType = new SensorType(sensorTypeIdDouble, MeasurementUnit.Percentage);

        // Act
        SensorTypeId result = sensorType.identity();

        // Assert
        assertEquals(sensorTypeIdDouble, result);
    }

    @Test
    void createSensorType_getMeasurementUnit() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorType = new SensorType(sensorTypeIdDouble, MeasurementUnit.Percentage);

        // Act
        MeasurementUnit result = sensorType.getMeasurementUnit();

        // Assert
        assertEquals(MeasurementUnit.Percentage, result);
    }


    @Test
    void createSensorType_sameAs_True() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorType = new SensorType(sensorTypeIdDouble, MeasurementUnit.Percentage);
        SensorType sensorType1 = new SensorType(sensorTypeIdDouble, MeasurementUnit.Percentage);

        // Assert
        assertTrue(sensorType.sameAs(sensorType1));
    }

    @Test
    void createSensorType_sameAs_False() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorTypeId sensorTypeIdDouble2 = mock(SensorTypeId.class);
        SensorType sensorType = new SensorType(sensorTypeIdDouble, MeasurementUnit.Percentage);
        SensorType sensorType1 = new SensorType(sensorTypeIdDouble2, MeasurementUnit.Celsius);

        // Assert
        assertFalse(sensorType.sameAs(sensorType1));
    }

    @Test
    void createSensorType_sameAs_DifferentTypeObject() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorType = new SensorType(sensorTypeIdDouble, MeasurementUnit.Percentage);
        int differentTypeObject = 2;

        // Assert
        assertFalse(sensorType.sameAs(differentTypeObject));
    }

    /** integration tests **/
    @Test
    void createSensorType_integration() {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("st1");

        // Act
        SensorType sensorType = new SensorType(sensorTypeId, MeasurementUnit.Percentage);

        // Assert
        assertNotNull(sensorType);
    }

    @Test
    void createSensorType_identity_integration() {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("st1");
        SensorType sensorType = new SensorType(sensorTypeId, MeasurementUnit.Percentage);

        // Act
        SensorTypeId result = sensorType.identity();

        // Assert
        assertEquals(sensorTypeId, result);
    }

    @Test
    void createSensorType_getMeasurementUnit_integration() {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("st1");
        SensorType sensorType = new SensorType(sensorTypeId, MeasurementUnit.Percentage);

        // Act
        MeasurementUnit result = sensorType.getMeasurementUnit();

        // Assert
        assertEquals(MeasurementUnit.Percentage, result);
    }


    @Test
    void createSensorType_sameAs_True_integration() {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("st1");
        SensorType sensorType = new SensorType(sensorTypeId, MeasurementUnit.Percentage);
        SensorType sensorType1 = new SensorType(sensorTypeId, MeasurementUnit.Percentage);

        // Assert
        assertTrue(sensorType.sameAs(sensorType1));
    }

    @Test
    void createSensorType_sameAs_False_integration() {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("st1");
        SensorTypeId sensorTypeId2 = new SensorTypeId("st");
        SensorType sensorType = new SensorType(sensorTypeId, MeasurementUnit.Percentage);
        SensorType sensorType1 = new SensorType(sensorTypeId2, MeasurementUnit.Celsius);

        // Assert
        assertFalse(sensorType.sameAs(sensorType1));
    }
}