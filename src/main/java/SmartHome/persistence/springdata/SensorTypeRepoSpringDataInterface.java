package SmartHome.persistence.springdata;


import SmartHome.persistence.jpa.datamodel.SensorTypeDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

interface SensorTypeRepoSpringDataInterface extends JpaRepository<SensorTypeDataModel, String>
{
}
