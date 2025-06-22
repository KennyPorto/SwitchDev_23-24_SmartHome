package SmartHome.persistence.mem;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.implementation.BlindRollerActuator;
import SmartHome.domain.actuators.implementation.RangeActuatorDecimal;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.valueObjects.ActuatorId;
import SmartHome.domain.valueObjects.DeviceID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorRepositoryImplMemTest
{
    private ActuatorRepository actuatorRepository;
    private BlindRollerActuator actuatorMock;
    private ActuatorId idMock;

    @BeforeEach
    void setUp() {
        actuatorRepository = new ActuatorRepositoryImplMem();
        idMock = mock(ActuatorId.class);
        actuatorMock = mock(BlindRollerActuator.class);
    }

    @Test
    void validRepositoryCreationFromPreConfiguredCatalogue() {
        // Assert
        assertNotNull(actuatorRepository);
    }


    @Test
    void validSaveShouldStoreActuator() {
        // Act
        Actuator savedActuator = actuatorRepository.save(actuatorMock);
        Actuator repositoryActuator = actuatorRepository.findAll().iterator().next();

        //Assert
        assertEquals(savedActuator, repositoryActuator);
    }

    @Test
    void nullActuatorShouldNotBeSaved() {
        //Assert
        assertNull(actuatorRepository.save(null));
    }

    @Test
    void foundValidActuator() {
        //Arrange
        when(actuatorMock.identity()).thenReturn(idMock);
        Actuator savedActuator = actuatorRepository.save(actuatorMock);

        // Act
        Optional<Actuator> foundActuator = actuatorRepository.findById(idMock);

        // Assert
        assertTrue(foundActuator.isPresent());
        assertEquals(savedActuator, foundActuator.orElse(null));
    }

    @Test
    void validActuatorExists() {
        //Arrange
        when(actuatorMock.identity()).thenReturn(idMock);
        actuatorRepository.save(actuatorMock);

        // Act
        boolean foundActuator = actuatorRepository.existsById(idMock);

        // Assert
        assertTrue(foundActuator);
    }

    @Test
    void notFoundActuator() {
        // Act
        Optional<Actuator> foundActuator = actuatorRepository.findById(idMock);

        // Assert
        assertFalse(foundActuator.isPresent());
    }

    @Test
    void invalidActuatorDoesNotExist() {
        // Act
        boolean foundActuator = actuatorRepository.existsById(idMock);

        // Assert
        assertFalse(foundActuator);
    }

    @Test
    void validNoSensorsSaved() {
        // Arrange
        RangeActuatorDecimal rangeActuatorMock = mock(RangeActuatorDecimal.class);
        ActuatorId rangeIdMock = mock(ActuatorId.class);

        when(rangeActuatorMock.identity()).thenReturn(rangeIdMock);
        when(actuatorMock.identity()).thenReturn(idMock);

        // Act
        List<Actuator> savedActuators = (List<Actuator>) actuatorRepository.findAll();

        // Assert
        assertTrue(savedActuators.isEmpty());
    }

    @Test
    void validFindAllIsNotNull() {
        //Arrange
        actuatorRepository.save(actuatorMock);

        //Act
        Iterable<Actuator> allActuators = actuatorRepository.findAll();

        //Assert
        assertNotNull(allActuators);
    }

    @Test
    void findOneActuatorByDeviceId() {
        // Arrange
        DeviceID deviceIDmock = new DeviceID(1L);
        when(actuatorMock.getDeviceID()).thenReturn(deviceIDmock);
        actuatorRepository.save(actuatorMock);

        // Act
        Iterable<Actuator> result = actuatorRepository.findAllByDeviceId(1L);
        Iterable<Actuator> expected = Collections.singletonList(actuatorMock);

        // Assert
        assertIterableEquals(expected, result);
    }

    @Test
    void findNoActuatorByDeviceId() {
        // Act
        Iterable<Actuator> result = actuatorRepository.findAllByDeviceId(1L);
        Iterable<Actuator> expected = Collections.emptyList();

        // Assert
        assertIterableEquals(expected, result);
    }
}