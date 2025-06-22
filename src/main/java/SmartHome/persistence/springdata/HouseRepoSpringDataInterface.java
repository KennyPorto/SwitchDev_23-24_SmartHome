package SmartHome.persistence.springdata;

import SmartHome.persistence.jpa.datamodel.HouseDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepoSpringDataInterface extends JpaRepository<HouseDataModel, Long> {
}
