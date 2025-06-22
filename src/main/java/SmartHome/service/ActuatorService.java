package SmartHome.service;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.actuators.implementation.BlindRollerActuator;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.Value;
import SmartHome.domain.valueObjects.values.implementation.PercentageValue;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static SmartHome.utils.Constants.*;

@Service
public class ActuatorService
{
    private final ActuatorRepository _actuatorRepository;
    private final ActuatorFactory _actuatorFactory;
    private final DeviceRepository _deviceRepository;
    private final DeviceFactory _deviceFactory;

    public ActuatorService(@Qualifier("actuatorRepoSpringDataImpl") ActuatorRepository actuatorRepository, ActuatorFactory actuatorFactory,
                           @Qualifier("deviceRepoSpringDataImpl") DeviceRepository deviceRepository, DeviceFactory deviceFactory)
    {
        this._actuatorRepository = actuatorRepository;
        this._actuatorFactory = actuatorFactory;
        this._deviceRepository = deviceRepository;
        this._deviceFactory = deviceFactory;
    }

    public Actuator add(DeviceID deviceID, ActuatorModel actuatorModel, ActuatorName name, ActuatorId id,
                        ActuatorTypeId actuatorTypeId, LimitInt limitInt, LimitFractional limitFractional, Value value)
    {
        if ( !this._deviceRepository.existsById(deviceID) ) {
            throw new EntityNotFoundException("Device id doesn't exist");
        }

        Actuator actuator = _actuatorFactory.createActuator(deviceID, actuatorModel, name, id, actuatorTypeId);

        if ( actuatorModel.model.equals(RANGE_INT) ) {
            actuator = _actuatorFactory.createActuator(deviceID, actuatorModel, name, id, actuatorTypeId, limitInt);
        }

        if ( actuatorModel.model.equals(RANGE_DECIMAL) ) {
            actuator = _actuatorFactory.createActuator(deviceID, actuatorModel, name, id, actuatorTypeId, limitFractional);
        }

        if ( actuator.getActuatorModel().getModel().equals(BLIND_ROLLER) ) {
            BlindRollerActuator blindRollerActuator = (BlindRollerActuator) actuator;
            actuator = blindRollerActuator.setCurrentPercentage((PercentageValue) value);
        }

        if ( actuator == null ) {
            throw new IllegalArgumentException("Invalid actuator attributes");
        }

        return _actuatorRepository.save(actuator);
    }

    public Iterable<Actuator> findAll()
    {
        return _actuatorRepository.findAll();

    }

    public Actuator findById(Long id)
    {
        Optional<ActuatorId> actuatorId = _actuatorFactory.createActuatorId(id);

        if ( actuatorId.isEmpty() ) {
            throw new IllegalArgumentException("Bad id input");
        }

        Optional<Actuator> actuator = _actuatorRepository.findById(actuatorId.get());

        if ( actuator.isEmpty() ) {
            throw new EntityNotFoundException("Not found by id.");
        }

        return actuator.get();
    }

    public Iterable<Actuator> findAllByDeviceId(Long id) {
        Optional<DeviceID> deviceID = _deviceFactory.createDeviceId(id);

        if (deviceID.isEmpty()) {
            throw new IllegalArgumentException("Bad id input");
        }

        return _actuatorRepository.findAllByDeviceId(id);
    }
}
