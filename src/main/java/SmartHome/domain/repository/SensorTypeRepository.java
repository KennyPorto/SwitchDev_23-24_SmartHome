package SmartHome.domain.repository;

import SmartHome.ddd.Repository;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.valueObjects.SensorTypeId;

public interface SensorTypeRepository extends Repository<SensorTypeId, SensorType> {
}
