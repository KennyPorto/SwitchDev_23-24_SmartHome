package SmartHome.service;

import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.actuatortype.ActuatorTypeFactory;
import SmartHome.domain.repository.ActuatorTypeRepository;
import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.mapper.ActuatorTypeDTO;
import SmartHome.mapper.ActuatorTypeMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActuatorTypeServiceTest {

    @Test
    void shouldCreateActuatorTypeService() {
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        //Assert
        assertTrue(true);
    }


    @Test
    void shouldAddActuatorType() {
        // Arrange
        String name = "at1";
        String measurementUnit = "Binary";
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);

        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        when(actuatorTypeFactoryDouble.createActuatorTypeName(name)).thenReturn(Optional.ofNullable(actuatorTypeIdDouble));
        when(actuatorTypeFactoryDouble.createActuatorType(actuatorTypeIdDouble, MeasurementUnit.valueOf(measurementUnit))).thenReturn(actuatorTypeDouble);
        when(actuatorTypeRepositoryDouble.existsById(actuatorTypeIdDouble)).thenReturn(false);
        when(actuatorTypeRepositoryDouble.save(actuatorTypeDouble)).thenReturn(actuatorTypeDouble);

        ActuatorTypeService service = new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        // Act
        ActuatorType result = service.add(actuatorTypeIdDouble, MeasurementUnit.valueOf(measurementUnit));

        // Assert
        assertEquals(actuatorTypeDouble, result);
    }

    @Test
    void shouldNotAddActuatorType_ActuatorExistsById() {
        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        MeasurementUnit measurementUnit = MeasurementUnit.Binary;
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);

        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        when(actuatorTypeFactoryDouble.createActuatorType(actuatorTypeIdDouble, measurementUnit)).thenReturn(actuatorTypeDouble);
        when(actuatorTypeRepositoryDouble.existsById(actuatorTypeIdDouble)).thenReturn(true);

        ActuatorTypeService service = new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.add(actuatorTypeIdDouble, measurementUnit));
        String expectedMessage = "Actuator type id already exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotAddActuatorType() {
        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        MeasurementUnit measurementUnit = MeasurementUnit.Binary;

        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        when(actuatorTypeFactoryDouble.createActuatorType(actuatorTypeIdDouble, measurementUnit)).thenReturn(null);
        when(actuatorTypeRepositoryDouble.existsById(actuatorTypeIdDouble)).thenReturn(false);

        ActuatorTypeService service = new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.add(actuatorTypeIdDouble, measurementUnit));
        String expectedMessage = "Invalid actuator type attributes";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldFindAllActuatorTypes() {

        // Arrange
        String measurementUnit = "Binary";
        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorTypeDTO actuatorTypeDTODouble = mock(ActuatorTypeDTO.class);
        when(actuatorTypeRepositoryDouble.findAll()).thenReturn(List.of(actuatorTypeDouble));
        when(actuatorTypeDouble.identity()).thenReturn(actuatorTypeIdDouble);
        when(actuatorTypeDouble.getMeasurementUnit()).thenReturn(MeasurementUnit.valueOf(measurementUnit));
        ActuatorTypeService service = new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        // Mocking static method
        try (MockedStatic<ActuatorTypeMapper> stMapper = Mockito.mockStatic(ActuatorTypeMapper.class)) {
            stMapper.when(() -> ActuatorTypeMapper.actuatorTypesToDTO(List.of(actuatorTypeDouble)))
                    .thenReturn(List.of(actuatorTypeDTODouble));

            // Act
            Iterable<ActuatorType> result = service.findAll();
            Iterable<ActuatorType> expected = List.of(actuatorTypeDouble);

            // Assert
            assertEquals(expected.toString(), result.toString());
        }
    }

    @Test
    void shouldFindActuatorTypeById() {
        // Arrange
        String name = "at1";
        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        when(actuatorTypeFactoryDouble.createActuatorTypeName(name)).thenReturn(Optional.of(actuatorTypeIdDouble));
        when(actuatorTypeRepositoryDouble.findById(actuatorTypeIdDouble)).thenReturn(Optional.of(actuatorTypeDouble));

        ActuatorTypeService service = new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        // Act
        ActuatorType result = service.findById(name);

        // Assert
        assertEquals(actuatorTypeDouble, result);
    }

    @Test
    void shouldFindActuatorTypeById_notFound() {
        // Arrange
        String name = "at1";
        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        ActuatorType actuatorTypeDouble = mock(ActuatorType.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        when(actuatorTypeFactoryDouble.createActuatorTypeName(name)).thenReturn(Optional.of(actuatorTypeIdDouble));
        when(actuatorTypeRepositoryDouble.findById(actuatorTypeIdDouble)).thenReturn(Optional.empty());

        ActuatorTypeService service = new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> service.findById(name));

        assertEquals("Not found by id.", e.getMessage());
    }

    @Test
    void shouldNotFindActuatorTypeWhenNull() {
        // Arrange
        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        when(actuatorTypeFactoryDouble.createActuatorTypeName(null)).thenReturn(Optional.empty());

        ActuatorTypeService service = new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.findById(null));
        String expectedMessage = "Bad id input";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldConfirmActuatorTypeExistsById() {

        // Arrange
        String name = "at1";
        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        when(actuatorTypeIdDouble.toString()).thenReturn(name);
        when(actuatorTypeFactoryDouble.createActuatorTypeName(name)).thenReturn(Optional.of(actuatorTypeIdDouble));
        when(actuatorTypeRepositoryDouble.existsById(actuatorTypeIdDouble)).thenReturn(true);
        ActuatorTypeService service = new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        // Act
        boolean result = service.existsById(name).getLeft();

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldNotConfirmActuatorTypeExistsByIdInvalidName() {

        // Arrange
        String name = " ";
        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        when(actuatorTypeFactoryDouble.createActuatorTypeName(name)).thenReturn(Optional.empty());
        when(actuatorTypeRepositoryDouble.existsById(actuatorTypeIdDouble))
                .thenReturn(false);
        ActuatorTypeService service = new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.existsById(name));
        String expectedMessage = "Bad id input";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotConfirmActuatorTypeExistsById() {

        // Arrange
        String name = "at1";
        ActuatorTypeFactory actuatorTypeFactoryDouble = mock(ActuatorTypeFactory.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        when(actuatorTypeFactoryDouble.createActuatorTypeName(name)).thenReturn(Optional.of(actuatorTypeIdDouble));
        when(actuatorTypeRepositoryDouble.existsById(actuatorTypeIdDouble)).thenReturn(false);
        ActuatorTypeService service = new ActuatorTypeService(actuatorTypeRepositoryDouble, actuatorTypeFactoryDouble);

        // Act
        boolean result = service.existsById(name).getLeft();

        // Assert
        assertFalse(result);
    }
}
