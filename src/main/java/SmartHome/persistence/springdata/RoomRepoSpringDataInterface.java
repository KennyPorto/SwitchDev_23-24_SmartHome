package SmartHome.persistence.springdata;

import SmartHome.domain.valueObjects.OutdoorIndoor;
import SmartHome.persistence.jpa.datamodel.RoomDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

interface RoomRepoSpringDataInterface extends JpaRepository<RoomDataModel, Long>
{
    Iterable<RoomDataModel> findAllByHouseId(Long houseId);
    Iterable<RoomDataModel> findAllByHouseIdAndOutdoorIndoor(Long houseId, OutdoorIndoor outdoorIndoor);
}
