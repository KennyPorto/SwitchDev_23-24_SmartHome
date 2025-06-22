package SmartHome.persistence.springdata;

import SmartHome.persistence.jpa.datamodel.ActuatorDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActuatorRepoSpringDataInterface extends JpaRepository<ActuatorDataModel, Long> {
    Iterable<ActuatorDataModel> findAllByDeviceId(Long actuatorId);
}
