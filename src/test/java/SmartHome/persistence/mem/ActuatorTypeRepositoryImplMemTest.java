package SmartHome.persistence.mem;

import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.valueObjects.ActuatorTypeId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActuatorTypeRepositoryImplMemTest
{
    @Test
    void shouldSave() {

        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);
        when(actuatorTypeDouble.identity()).thenReturn(actuatorTypeIdDouble);
        ActuatorTypeRepositoryImplMem ActuatorTypeRepository = new ActuatorTypeRepositoryImplMem();

        // Act
        ActuatorType result = ActuatorTypeRepository.save(actuatorTypeDouble);

        //Assert
        assertEquals(actuatorTypeDouble, result);
    }

    @Test
    void shouldNotSaveNull() {

        // Arrange
        ActuatorTypeRepositoryImplMem ActuatorTypeRepository = new ActuatorTypeRepositoryImplMem();

        // Act
        ActuatorType result = ActuatorTypeRepository.save(null);

        //Assert
        assertNull(result);
    }

    @Test
    void shouldNotSaveDuplicated() {

        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);
        when(actuatorTypeDouble.identity()).thenReturn(actuatorTypeIdDouble);
        ActuatorTypeRepositoryImplMem ActuatorTypeRepository = new ActuatorTypeRepositoryImplMem();
        ActuatorTypeRepository.save(actuatorTypeDouble);

        // Act
        ActuatorType result = ActuatorTypeRepository.save(actuatorTypeDouble);

        //Assert
        assertNull(result);
    }

    @Test
    void shouldFindAll() {

        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);
        when(actuatorTypeDouble.identity()).thenReturn(actuatorTypeIdDouble);
        ActuatorTypeRepositoryImplMem ActuatorTypeRepository = new ActuatorTypeRepositoryImplMem();
        ActuatorTypeRepository.save(actuatorTypeDouble);

        // Act
        Iterable<ActuatorType> result = ActuatorTypeRepository.findAll();
        Iterable<ActuatorType> expected = List.of(actuatorTypeDouble);

        //Assert
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    void shouldFindByIdExistingId() {

        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);
        when(actuatorTypeDouble.identity()).thenReturn(actuatorTypeIdDouble);
        ActuatorTypeRepositoryImplMem ActuatorTypeRepository = new ActuatorTypeRepositoryImplMem();
        ActuatorTypeRepository.save(actuatorTypeDouble);

        // Act
        ActuatorType result = ActuatorTypeRepository.findById(actuatorTypeIdDouble).orElse(null);

        //Assert
        assertEquals(actuatorTypeDouble, result);
    }

    @Test
    void shouldNotFindByIdNonExistingId() {

        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorTypeRepositoryImplMem ActuatorTypeRepository = new ActuatorTypeRepositoryImplMem();

        // Act
        ActuatorType result = ActuatorTypeRepository.findById(actuatorTypeIdDouble).orElse(null);

        //Assert
        assertNull(result);
    }

    @Test
    void shouldExistsByIdExistingId() {

        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);
        when(actuatorTypeDouble.identity()).thenReturn(actuatorTypeIdDouble);
        ActuatorTypeRepositoryImplMem ActuatorTypeRepository = new ActuatorTypeRepositoryImplMem();
        ActuatorTypeRepository.save(actuatorTypeDouble);

        // Act
        boolean result = ActuatorTypeRepository.existsById(actuatorTypeIdDouble);

        //Assert
        assertTrue(result);
    }

    @Test
    void shouldNotExistsByIdNonExistingId() {

        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorTypeId otherActuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);
        when(actuatorTypeDouble.identity()).thenReturn(actuatorTypeIdDouble);
        ActuatorTypeRepositoryImplMem ActuatorTypeRepository = new ActuatorTypeRepositoryImplMem();
        ActuatorTypeRepository.save(actuatorTypeDouble);

        // Act
        boolean result = ActuatorTypeRepository.existsById(otherActuatorTypeIdDouble);

        //Assert
        assertFalse(result);
    }
}
