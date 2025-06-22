package SmartHome.domain.repository;

import SmartHome.ddd.Repository;
import SmartHome.domain.room.Room;
import SmartHome.domain.valueObjects.OutdoorIndoor;
import SmartHome.domain.valueObjects.RoomId;

public interface RoomRepository extends Repository<RoomId, Room> {
    Iterable<Room> findAllByHouseId(Long houseId);
    Iterable<Room> findAllByHouseIdAndOutdoorIndoor(long id, OutdoorIndoor outdoorIndoor);
}

