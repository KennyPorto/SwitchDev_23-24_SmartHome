package SmartHome.persistence.springdata;

import SmartHome.persistence.jpa.datamodel.ActuatorActivityLogDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;

public interface ActuatorActivityLogRepoSpringDataInterface extends JpaRepository<ActuatorActivityLogDataModel, Long>
{
    ActuatorActivityLogDataModel findTopByActuatorIdOrderByTimeStampDesc(long actuatorId);
    Iterable<ActuatorActivityLogDataModel> findAllByActuatorIdAndTimeStampBetween(long actuatorId, ZonedDateTime start, ZonedDateTime end);

    Iterable<ActuatorActivityLogDataModel> findAllByActuatorId(long actuatorId);
}
