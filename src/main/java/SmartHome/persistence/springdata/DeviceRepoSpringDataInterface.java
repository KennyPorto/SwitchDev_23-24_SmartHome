package SmartHome.persistence.springdata;

import SmartHome.persistence.jpa.datamodel.DeviceDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepoSpringDataInterface extends JpaRepository<DeviceDataModel, Long>
{
    Iterable<DeviceDataModel> findAllByRoomId(long roomId);
}

