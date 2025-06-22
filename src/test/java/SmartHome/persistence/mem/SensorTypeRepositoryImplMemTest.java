package SmartHome.persistence.mem;

import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.valueObjects.SensorTypeId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorTypeRepositoryImplMemTest
{
    @Test
    void save() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeIdDouble);
        SensorTypeRepositoryImplMem sensorTypeRepositoryImplMem = new SensorTypeRepositoryImplMem();

        // Act
        SensorType result = sensorTypeRepositoryImplMem.save(sensorTypeDouble);

        //Assert
        assertEquals(sensorTypeDouble, result);
    }

    @Test
    void save_existingId() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeIdDouble);
        SensorTypeRepositoryImplMem sensorTypeRepositoryImplMem = new SensorTypeRepositoryImplMem();
        sensorTypeRepositoryImplMem.save(sensorTypeDouble);

        // Act
        SensorType result = sensorTypeRepositoryImplMem.save(sensorTypeDouble);

        //Assert
        assertNull(result);
    }

    @Test
    void findAll() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeIdDouble);
        SensorTypeRepositoryImplMem sensorTypeRepositoryImplMem = new SensorTypeRepositoryImplMem();
        sensorTypeRepositoryImplMem.save(sensorTypeDouble);

        // Act
        Iterable<SensorType> result = sensorTypeRepositoryImplMem.findAll();
        Iterable<SensorType> expected = List.of(sensorTypeDouble);

        //Assert
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    void findById_existingId() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeIdDouble);
        SensorTypeRepositoryImplMem sensorTypeRepositoryImplMem = new SensorTypeRepositoryImplMem();
        sensorTypeRepositoryImplMem.save(sensorTypeDouble);

        // Act
        SensorType result = sensorTypeRepositoryImplMem.findById(sensorTypeIdDouble).orElse(null);

        //Assert
        assertEquals(sensorTypeDouble, result);
    }

    @Test
    void findById_nonExistingId() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorTypeRepositoryImplMem sensorTypeRepositoryImplMem = new SensorTypeRepositoryImplMem();

        // Act
        SensorType result = sensorTypeRepositoryImplMem.findById(sensorTypeIdDouble).orElse(null);

        //Assert
        assertNull(result);
    }

    @Test
    void existsById_existingId() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeIdDouble);
        SensorTypeRepositoryImplMem sensorTypeRepositoryImplMem = new SensorTypeRepositoryImplMem();
        sensorTypeRepositoryImplMem.save(sensorTypeDouble);

        // Act
        boolean result = sensorTypeRepositoryImplMem.existsById(sensorTypeIdDouble);

        //Assert
        assertTrue(result);
    }

    @Test
    void existsById_nonExistingId() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorTypeId otherSensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeIdDouble);
        SensorTypeRepositoryImplMem sensorTypeRepositoryImplMem = new SensorTypeRepositoryImplMem();
        sensorTypeRepositoryImplMem.save(sensorTypeDouble);

        // Act
        boolean result = sensorTypeRepositoryImplMem.existsById(otherSensorTypeIdDouble);

        //Assert
        assertFalse(result);
    }
}