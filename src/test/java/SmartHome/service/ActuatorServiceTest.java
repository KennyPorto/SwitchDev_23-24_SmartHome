package SmartHome.service;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.actuators.implementation.BlindRollerActuator;
import SmartHome.domain.actuators.implementation.RangeActuatorDecimal;
import SmartHome.domain.actuators.implementation.RangeActuatorInt;
import SmartHome.domain.actuators.implementation.SwitchOnOffActuator;
import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.Value;
import SmartHome.domain.valueObjects.values.implementation.PercentageValue;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorServiceTest {
    private ActuatorRepository mockActuatorRepository;
    private ActuatorFactory mockActuatorFactory;
    private DeviceRepository mockDeviceRepository;
    private DeviceFactory mockDeviceFactory;


    @BeforeEach
    void setup() {
        mockActuatorRepository = mock(ActuatorRepository.class);
        mockActuatorFactory = mock(ActuatorFactory.class);
        mockDeviceRepository = mock(DeviceRepository.class);
        mockDeviceFactory = mock(DeviceFactory.class);
    }

    @Test
    void addActuatorToADeviceWithInvalidDeviceId() {
        // Arrange
        ActuatorName name = mock(ActuatorName.class);
        ActuatorModel model = mock(ActuatorModel.class);
        ActuatorId id = mock(ActuatorId.class);
        DeviceID deviceId = mock(DeviceID.class);
        ActuatorTypeId actuatorTypeId = mock(ActuatorTypeId.class);
        LimitInt limitInt = mock(LimitInt.class);
        Value value = mock(Value.class);
        LimitFractional limitFractional = mock(LimitFractional.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(false);
        ActuatorService actuatorService = new ActuatorService(
                mockActuatorRepository, mockActuatorFactory,
                mockDeviceRepository, mockDeviceFactory);

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> actuatorService.add(deviceId, model, name, id,
                        actuatorTypeId, limitInt, limitFractional, value)
        );

        String expectedMessage = "Device id doesn't exist";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }


    @Test
    void saveActuatorWithValidArguments() {
        // Arrange
        ActuatorName name = mock(ActuatorName.class);
        ActuatorModel model = mock(ActuatorModel.class);
        ActuatorId id = mock(ActuatorId.class);
        Actuator actuator = mock(Actuator.class);
        DeviceID deviceId = mock(DeviceID.class);
        ActuatorTypeId actuatorTypeId = mock(ActuatorTypeId.class);
        LimitInt limitInt = mock(LimitInt.class);
        Value value = mock(Value.class);
        LimitFractional limitFractional = mock(LimitFractional.class);

        when(mockActuatorRepository.existsById(id)).thenReturn(false);
        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorFactory.createActuator(deviceId, model, name, id, actuatorTypeId)).thenReturn(actuator);
        when(mockActuatorRepository.save(actuator)).thenReturn(actuator);

        ActuatorService actuatorService = mock(ActuatorService.class);
        when(actuatorService.add(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(actuator);

        // Act
        Actuator result = actuatorService.add(deviceId, model, name, id, actuatorTypeId, limitInt, limitFractional, value);

        // Assert
        assertEquals(result, actuator);
    }

    @Test
    void saveActuatorWithValidArguments_BlindRoller() {
        // Arrange
        ActuatorName name = new ActuatorName("blindRoller");
        ActuatorModel model = new ActuatorModel("BlindRollerActuator");
        ActuatorId id = new ActuatorId(1);
        DeviceID deviceId = new DeviceID(1);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("blind");
        BlindRollerActuator actuator = new BlindRollerActuator(deviceId, name, id, actuatorTypeId, model);
        ActuatorFactory actuatorFactory = new ActuatorFactory();
        DeviceFactory deviceFactory = new DeviceFactory();

        Value value = new PercentageValue(100);
        LimitInt limitInt = mock(LimitInt.class);
        LimitFractional limitFractional = mock(LimitFractional.class);

        when(mockActuatorRepository.existsById(id)).thenReturn(false);
        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.save(any())).thenReturn(actuator);

        ActuatorService actuatorService = new ActuatorService(mockActuatorRepository, actuatorFactory,
                mockDeviceRepository, deviceFactory);

        // Act
        BlindRollerActuator result = (BlindRollerActuator) actuatorService
                .add(deviceId, model, name, id, actuatorTypeId, limitInt, limitFractional, value);


        // Assert
        assertEquals(actuator.getCurrentPercentage().value, result.getCurrentPercentage().value);
    }

    @Test
    void saveActuatorWithValidArguments_RangeINT() {
        // Arrange
        ActuatorName name = new ActuatorName("RangeActuatorInt");
        ActuatorModel model = new ActuatorModel("RangeActuatorInt");
        ActuatorId id = new ActuatorId(1);
        DeviceID deviceId = new DeviceID(1);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("range");
        LimitInt limitInt = mock(LimitInt.class);
        RangeActuatorInt actuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, model, limitInt);
        ActuatorFactory actuatorFactory = new ActuatorFactory();
        DeviceFactory deviceFactory = new DeviceFactory();

        Value value = new PercentageValue(100);
        LimitFractional limitFractional = mock(LimitFractional.class);

        when(mockActuatorRepository.existsById(id)).thenReturn(false);
        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.save(any())).thenReturn(actuator);

        ActuatorService actuatorService = new ActuatorService(mockActuatorRepository, actuatorFactory,
                mockDeviceRepository, deviceFactory);

        // Act
        Actuator result = actuatorService.add(deviceId, model, name, id, actuatorTypeId, limitInt, limitFractional, value);

        // Assert
        assertEquals(result, actuator);
    }

    @Test
    void saveActuatorWithValidArguments_RangeDECIMAL() {
        // Arrange
        ActuatorName name = new ActuatorName("RangeActuatorDecimal");
        ActuatorModel model = new ActuatorModel("RangeActuatorDecimal");
        ActuatorId id = new ActuatorId(1);
        DeviceID deviceId = new DeviceID(1);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("range");
        LimitInt limitInt = mock(LimitInt.class);
        LimitFractional limitFractional = mock(LimitFractional.class);
        RangeActuatorDecimal actuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId, limitFractional,
                model);
        ActuatorFactory actuatorFactory = new ActuatorFactory();
        DeviceFactory deviceFactory = new DeviceFactory();

        Value value = new PercentageValue(100);

        when(mockActuatorRepository.existsById(id)).thenReturn(false);
        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.save(any())).thenReturn(actuator);

        ActuatorService actuatorService = new ActuatorService(mockActuatorRepository, actuatorFactory,
                mockDeviceRepository, deviceFactory);

        // Act
        Actuator result = actuatorService.add(deviceId, model, name, id, actuatorTypeId, limitInt, limitFractional, value);

        // Assert
        assertEquals(result, actuator);
    }

    @Test
    void saveActuatorWithValidArguments_switch() {
        // Arrange
        ActuatorName name = new ActuatorName("SwitchOnOffActuator");
        ActuatorModel model = new ActuatorModel("SwitchOnOffActuator");
        ActuatorId id = new ActuatorId(1);
        DeviceID deviceId = new DeviceID(1);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("range");
        LimitInt limitInt = mock(LimitInt.class);
        LimitFractional limitFractional = mock(LimitFractional.class);
        SwitchOnOffActuator actuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, model);
        ActuatorFactory actuatorFactory = new ActuatorFactory();
        DeviceFactory deviceFactory = new DeviceFactory();

        Value value = new PercentageValue(100);

        when(mockActuatorRepository.existsById(id)).thenReturn(false);
        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.save(any())).thenReturn(actuator);

        ActuatorService actuatorService = new ActuatorService(mockActuatorRepository, actuatorFactory,
                mockDeviceRepository, deviceFactory);

        // Act
        Actuator result = actuatorService.add(deviceId, model, name, id, actuatorTypeId, limitInt, limitFractional, value);

        // Assert
        assertEquals(result, actuator);
    }

    @Test
    void findAll() {
        // Arrange
        Actuator actuator = mock(Actuator.class);
        ActuatorId actuatorId = mock(ActuatorId.class);

        ActuatorService actuatorService = new ActuatorService(mockActuatorRepository, mockActuatorFactory, mockDeviceRepository, mockDeviceFactory);

        when(mockActuatorRepository.findAll()).thenReturn(List.of(actuator));
        when(actuator.identity()).thenReturn(actuatorId);

        // Act
        Iterable<Actuator> result = actuatorService.findAll();
        Iterable<Actuator> expected = List.of(actuator);

        // Assert
        assertEquals(expected.iterator().next().identity().id,
                result.iterator().next().identity().id);
    }

    @Test
    void findById() {
        // Arrange
        long id = 1L;
        Actuator actuator = mock(Actuator.class);
        ActuatorService actuatorService = new ActuatorService(mockActuatorRepository, mockActuatorFactory, mockDeviceRepository, mockDeviceFactory);
        ActuatorId actuatorId = mock(ActuatorId.class);

        when(mockActuatorFactory.createActuatorId(id)).thenReturn(Optional.of(actuatorId));
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.of(actuator));

        Actuator result = actuatorService.findById(id);
        assertEquals(result, actuator);
    }

    @Test
    void findById_badIdInput() {
        // Arrange
        long id = 1L;
        ActuatorService actuatorService = new ActuatorService(mockActuatorRepository, mockActuatorFactory, mockDeviceRepository, mockDeviceFactory);

        when(mockActuatorFactory.createActuatorId(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> actuatorService.findById(id)
        );

        String expectedMessage = "Bad id input";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void findById_notFound() {
        // Arrange
        long id = 1L;
        ActuatorService actuatorService = new ActuatorService(mockActuatorRepository, mockActuatorFactory, mockDeviceRepository, mockDeviceFactory);
        ActuatorId actuatorId = mock(ActuatorId.class);

        when(mockActuatorFactory.createActuatorId(id)).thenReturn(Optional.of(actuatorId));
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> actuatorService.findById(id)
        );

        String expectedMessage = "Not found by id.";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void findAllByDeviceId() {
        // Arrange
        long id = 1L;
        Actuator actuator = mock(Actuator.class);
        ActuatorService actuatorService = new ActuatorService(mockActuatorRepository, mockActuatorFactory, mockDeviceRepository, mockDeviceFactory);
        DeviceID deviceID = mock(DeviceID.class);

        when(mockDeviceFactory.createDeviceId(id)).thenReturn(Optional.of(deviceID));
        when(mockActuatorRepository.findAllByDeviceId(id)).thenReturn(List.of(actuator));

        Iterable<Actuator> result = actuatorService.findAllByDeviceId(id);
        Iterable<Actuator> expected = List.of(actuator);
        assertEquals(expected, result);
    }

    @Test
    void findAllByDeviceId_badIdInput() {
        // Arrange
        long id = 1L;
        ActuatorService actuatorService = new ActuatorService(mockActuatorRepository, mockActuatorFactory, mockDeviceRepository, mockDeviceFactory);

        when(mockDeviceFactory.createDeviceId(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> actuatorService.findAllByDeviceId(id)
        );

        String expectedMessage = "Bad id input";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
