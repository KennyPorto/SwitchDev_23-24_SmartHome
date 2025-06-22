package SmartHome.persistence.springdata;

import SmartHome.persistence.jpa.datamodel.SensorDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepoSpringDataInterface extends JpaRepository<SensorDataModel, Long>
{
    Iterable<SensorDataModel> findAllByDeviceIdAndSensorTypeId(long deviceId, String sensorTypeId);
    Iterable<SensorDataModel> findAllByDeviceId(Long sensorId);
}
