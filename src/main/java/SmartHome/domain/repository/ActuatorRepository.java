package SmartHome.domain.repository;

import SmartHome.ddd.Repository;
import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.valueObjects.ActuatorId;

public interface ActuatorRepository extends Repository<ActuatorId, Actuator> {
    Iterable<Actuator> findAllByDeviceId(Long deviceId);
}
