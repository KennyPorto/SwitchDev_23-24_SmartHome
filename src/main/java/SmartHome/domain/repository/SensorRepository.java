package SmartHome.domain.repository;

import SmartHome.ddd.Repository;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.SensorId;

public interface SensorRepository extends Repository<SensorId, Sensor> {
    Iterable<Sensor> findAllByDeviceId(Long deviceId);

    Iterable<Sensor> findAllByDeviceIdAndSensorTypeId(long deviceId, String sensorTypeId);
}
