package SmartHome.service;

import SmartHome.domain.activitylog.ActivityLogFactory;
import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.implementation.BlindRollerActuator;
import SmartHome.domain.repository.ActuatorActivityLogRepository;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.implementation.PercentageValue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class ActuatorActService
{
    private final ActuatorActivityLogRepository _activityLogRepository;
    private final DeviceRepository _deviceRepository;
    private final ActivityLogFactory _activityLogFactory;
    private final ActuatorRepository _actuatorRepository;
    private final SensorRepository _sensorRepository;

    public ActuatorActService(
            @Qualifier("actuatorActivityLogRepoSpringDataImpl") ActuatorActivityLogRepository activityLogRepository,
            ActivityLogFactory activityLogFactory,
            @Qualifier("actuatorRepoSpringDataImpl") ActuatorRepository actuatorRepository,
            @Qualifier("deviceRepoSpringDataImpl") DeviceRepository deviceRepository,
            @Qualifier("sensorRepoSpringDataImpl") SensorRepository sensorRepository)
    {
        this._activityLogRepository = activityLogRepository;
        this._activityLogFactory = activityLogFactory;
        this._actuatorRepository = actuatorRepository;
        this._deviceRepository = deviceRepository;
        this._sensorRepository = sensorRepository;

    }

    public ActuatorActivityLog closeBlindRoller(DeviceID deviceID, Measurement measurement, TimeStamp timeStamp, ActuatorId actuatorId)
    {
        validateDevice(deviceID);
        if (!hasScaleSensor(deviceID))
            throw new IllegalArgumentException("Device doesn't have a scale sensor");

        BlindRollerActuator blindRollerActuator = getBlindRollerActuator(actuatorId);
        if (blindRollerActuator.getDeviceID().getId() != deviceID.getId())
            throw new IllegalArgumentException("This blindRoller doesn't belong to this device");

        if (!isNumeric(measurement.getMeasurement()))
            throw new NumberFormatException("Measurement should be numeric");

        Actuator blindRollerActuatorUpdated = updateBlindRoller(measurement, blindRollerActuator);
        return saveAction(blindRollerActuatorUpdated.identity(), timeStamp, measurement);
    }

    private Actuator updateBlindRoller(Measurement measurement, BlindRollerActuator blindRollerActuator)
    {
        PercentageValue percentageValue = new PercentageValue(parseInt(measurement.getMeasurement()));
        BlindRollerActuator result = blindRollerActuator.setCurrentPercentage(percentageValue);
        return this._actuatorRepository.save(result);
    }


    public ActuatorActivityLog saveAction(ActuatorId actuatorId, TimeStamp timeStamp, Measurement measurement)
    {
        ActivityLogId id = new ActivityLogId(1L);
        Optional<ActuatorActivityLog> log = _activityLogFactory.createActuatorActivityLog(
                id, actuatorId, timeStamp, measurement);

        if (log.isEmpty()) {
            throw new IllegalArgumentException("Log couldn't be saved");
        }

        return _activityLogRepository.save(log.get());
    }

    private void validateDevice(DeviceID deviceID)
    {
        boolean exists = _deviceRepository.existsById(deviceID);
        if (!exists)
            throw new IllegalArgumentException("A device with this device Id doesn't exist");
    }

    private BlindRollerActuator getBlindRollerActuator(ActuatorId actuatorId)
    {
        Optional<Actuator> actuator = _actuatorRepository.findById(actuatorId);
        if (actuator.isEmpty())
            throw new IllegalArgumentException("An actuator with this actuator Id doesn't exist");
        Actuator blindRollerActuator = actuator.get();
        if (!isBlindRoller(blindRollerActuator))
            throw new IllegalArgumentException("The actuator is not a blind roller");

        return (BlindRollerActuator) blindRollerActuator;
    }

    private boolean isBlindRoller(Actuator actuator)
    {
        return actuator.getActuatorModel().getModel().equals("BlindRollerActuator");
    }

    private boolean isNumeric(String measurement)
    {
        return measurement.chars().allMatch(Character::isDigit);
    }

    private boolean hasScaleSensor(DeviceID deviceID)
    {
        Iterable<Sensor> sensors = _sensorRepository.findAllByDeviceId(deviceID.id);
        for (Sensor sensor : sensors) {
            if (sensor.getSensorModel().getModel().equals("ScaleSensor")) {
                return true;
            }
        }
        return false;
    }
}
