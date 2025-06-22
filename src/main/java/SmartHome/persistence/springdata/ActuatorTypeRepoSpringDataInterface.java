package SmartHome.persistence.springdata;

import SmartHome.persistence.jpa.datamodel.ActuatorTypeDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActuatorTypeRepoSpringDataInterface extends JpaRepository<ActuatorTypeDataModel, String>
{

}
