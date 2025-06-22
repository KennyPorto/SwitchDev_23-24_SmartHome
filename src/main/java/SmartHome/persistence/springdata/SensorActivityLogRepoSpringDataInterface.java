package SmartHome.persistence.springdata;

import SmartHome.persistence.jpa.datamodel.SensorActivityLogDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;

public interface SensorActivityLogRepoSpringDataInterface extends JpaRepository<SensorActivityLogDataModel, Long>
{
    Iterable<SensorActivityLogDataModel> findAllBySensorIdAndTimeStampBetween(long sensorId, ZonedDateTime start, ZonedDateTime end);
    Iterable<SensorActivityLogDataModel> findAllBySensorId(long sensorId);

}
