package SmartHome.domain.repository;

import SmartHome.ddd.Repository;
import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.valueObjects.ActuatorTypeId;

public interface ActuatorTypeRepository extends Repository<ActuatorTypeId, ActuatorType> {
}
