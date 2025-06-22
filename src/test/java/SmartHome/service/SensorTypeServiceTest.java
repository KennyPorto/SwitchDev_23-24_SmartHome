package SmartHome.service;

import SmartHome.domain.repository.SensorTypeRepository;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.sensortype.SensorTypeFactory;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;
import SmartHome.mapper.SensorTypeDTO;
import SmartHome.mapper.SensorTypeMapper;
import SmartHome.persistence.mem.SensorTypeRepositoryImplMem;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorTypeServiceTest {
    @Test
    void SensorTypeService() {
        SensorTypeRepositoryImplMem sensorTypeRepositoryImplMemDouble = mock(SensorTypeRepositoryImplMem.class);
        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryImplMemDouble, sensorTypeFactoryDouble);

        //Assert
        assertNotNull(service);
    }

    @Test
    void shouldAddActuatorType() {
        // Arrange
        String name = "st1";
        String measurementUnit = "Percentage";
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorType sensorTypeDouble = mock(SensorType.class);

        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        when(sensorTypeFactoryDouble.createSensorTypeName(name)).thenReturn(Optional.ofNullable(sensorTypeIdDouble));
        when(sensorTypeFactoryDouble.createSensorType(sensorTypeIdDouble, MeasurementUnit.valueOf(measurementUnit))).thenReturn(sensorTypeDouble);
        when(sensorTypeRepositoryDouble.existsById(sensorTypeIdDouble)).thenReturn(false);
        when(sensorTypeRepositoryDouble.save(sensorTypeDouble)).thenReturn(sensorTypeDouble);

        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryDouble, sensorTypeFactoryDouble);

        // Act
        SensorType result = service.add(sensorTypeIdDouble, MeasurementUnit.valueOf(measurementUnit));

        // Assert
        assertEquals(sensorTypeDouble, result);
    }

    @Test
    void shouldNotAddSensorType_SensorExistsById() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        MeasurementUnit measurementUnit = MeasurementUnit.Binary;
        SensorType sensorTypeDouble = mock(SensorType.class);

        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        when(sensorTypeFactoryDouble.createSensorType(sensorTypeIdDouble, measurementUnit)).thenReturn(sensorTypeDouble);
        when(sensorTypeRepositoryDouble.existsById(sensorTypeIdDouble)).thenReturn(true);

        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryDouble, sensorTypeFactoryDouble);

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.add(sensorTypeIdDouble, measurementUnit));
        String expectedMessage = "Sensor type id already exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotAddSensorType() {
        // Arrange
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        MeasurementUnit measurementUnit = MeasurementUnit.Binary;

        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        when(sensorTypeFactoryDouble.createSensorType(sensorTypeIdDouble, measurementUnit)).thenReturn(null);
        when(sensorTypeRepositoryDouble.existsById(sensorTypeIdDouble)).thenReturn(false);

        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryDouble, sensorTypeFactoryDouble);

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.add(sensorTypeIdDouble, measurementUnit));
        String expectedMessage = "Invalid sensor type attributes";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void findAllSensorTypes() {
        // Arrange
        String measurementUnit = "Percentage";
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepositoryImplMem sensorTypeRepositoryImplMemDouble = mock(SensorTypeRepositoryImplMem.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        SensorTypeDTO sensorTypeDTODouble = mock(SensorTypeDTO.class);
        when(sensorTypeRepositoryImplMemDouble.findAll()).thenReturn(List.of(sensorTypeDouble));
        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryImplMemDouble, sensorTypeFactoryDouble);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeIdDouble);
        when(sensorTypeDouble.getMeasurementUnit()).thenReturn(MeasurementUnit.valueOf(measurementUnit));

        try (MockedStatic<SensorTypeMapper> stMapper = Mockito.mockStatic(SensorTypeMapper.class)) {
            stMapper.when(() -> SensorTypeMapper.sensorTypesToDto(List.of(sensorTypeDouble)))
                    .thenReturn(List.of(sensorTypeDTODouble));

            // Act
            Iterable<SensorTypeDTO> result = SensorTypeMapper.sensorTypesToDto(service.findAll());
            Iterable<SensorTypeDTO> expected = List.of(sensorTypeDTODouble);

            // Assert
            assertEquals(expected.toString(), result.toString());
        }
    }


    @Test
    void findSensorTypeById_success() {
        // Arrange
        String name = "st1";
        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepository sensorTypeRepositoryImplMemDouble = mock(SensorTypeRepositoryImplMem.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorTypeId sensorTypeId = new SensorTypeId(name);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeId);
        when(sensorTypeRepositoryImplMemDouble.findById(sensorTypeIdDouble))
                .thenReturn(Optional.of(sensorTypeDouble));
        when(sensorTypeIdDouble.toString()).thenReturn(name);
        when(sensorTypeFactoryDouble.createSensorTypeName(name)).thenReturn(Optional.of(sensorTypeIdDouble));
        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryImplMemDouble, sensorTypeFactoryDouble);

        //Act
        SensorType result = service.findById(name);

        //Assert
        assertEquals(sensorTypeDouble, result);
    }

    @Test
    void findSensorTypeById_fail() {
        // Arrange
        String id = "Temp";
        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepository sensorTypeRepositoryImplMemDouble = mock(SensorTypeRepositoryImplMem.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeFactoryDouble.createSensorTypeName(id)).thenReturn(Optional.of(sensorTypeIdDouble));
        when(sensorTypeRepositoryImplMemDouble.findById(sensorTypeIdDouble)).thenReturn(Optional.empty());
        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryImplMemDouble, sensorTypeFactoryDouble);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> service.findById(id));
    }

    @Test
    void findById_failBadIdInput() {
        // Arrange
        String id = " ";
        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        when(sensorTypeFactoryDouble.createSensorTypeName(id)).thenReturn(Optional.empty());
        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryDouble, sensorTypeFactoryDouble);

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.findById(id));
        String expectedMessage = "Bad id input";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void findById_failNotFoundById() {
        // Arrange
        String id = "Temp";
        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeFactoryDouble.createSensorTypeName(id)).thenReturn(Optional.of(sensorTypeIdDouble));
        when(sensorTypeRepositoryDouble.findById(sensorTypeIdDouble)).thenReturn(Optional.empty());
        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryDouble, sensorTypeFactoryDouble);

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.findById(id));
        String expectedMessage = "Not found by id.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void sensorTypeExistsById_success() {
        // Arrange
        String id = "Temp";
        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepository sensorTypeRepositoryImplMemDouble = mock(SensorTypeRepositoryImplMem.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorTypeId sensorTypeId = new SensorTypeId(id);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeId);
        when(sensorTypeFactoryDouble.createSensorTypeName(id)).thenReturn(Optional.of(sensorTypeIdDouble));
        when(sensorTypeRepositoryImplMemDouble.existsById(sensorTypeIdDouble)).thenReturn(true);
        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryImplMemDouble, sensorTypeFactoryDouble);

        // Act
        boolean result = service.existsById(id).getLeft();

        // Assert
        assertTrue(result);
    }

    @Test
    void sensorTypeExistsById_failInvalidId() {

        // Arrange
        String id = " ";
        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeFactoryDouble.createSensorTypeName(id)).thenReturn(Optional.empty());
        when(sensorTypeRepositoryDouble.existsById(sensorTypeIdDouble))
                .thenReturn(false);
        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryDouble, sensorTypeFactoryDouble);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.existsById(id));
        String expectedMessage = "Bad id input";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void sensorTypeExistsById_failDoesntExist() {
        // Arrange
        String id = "Temp";
        SensorTypeFactory sensorTypeFactoryDouble = mock(SensorTypeFactory.class);
        SensorTypeRepository sensorTypeRepositoryImplMemDouble = mock(SensorTypeRepository.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeFactoryDouble.createSensorTypeName(id)).thenReturn(Optional.of(sensorTypeIdDouble));
        when(sensorTypeRepositoryImplMemDouble.existsById(sensorTypeIdDouble)).thenReturn(false);
        SensorTypeService service = new SensorTypeService(sensorTypeRepositoryImplMemDouble, sensorTypeFactoryDouble);

        // Act
        boolean result = service.existsById(id).getLeft();

        // Assert
        assertFalse(result);
    }
}