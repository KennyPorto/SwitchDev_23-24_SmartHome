package SmartHome.persistence.mem;

import SmartHome.domain.repository.RoomRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.valueObjects.OutdoorIndoor;
import SmartHome.domain.valueObjects.RoomId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomRepositoryImplMem implements RoomRepository
{
    private final Map<RoomId, Room> _rooms = new HashMap<>();

    @Override
    public Room save(Room room) {
        if (room == null) return null;
        if (existsById(room.identity())) return null;
        _rooms.put(room.identity(), room);
        return room;
    }

    @Override
    public Iterable<Room> findAll() {
        return _rooms.values();
    }

    @Override
    public Optional<Room> findById(RoomId id) {
        if (!existsById(id))
            return Optional.empty();
        else
            return Optional.of(_rooms.get(id));
    }

    @Override
    public boolean existsById(RoomId id) {
        return _rooms.keySet().stream().filter(roomId -> roomId.equals(id)).findFirst().orElse(null) != null;
    }

    @Override
    public Iterable<Room> findAllByHouseId(Long houseId) {
        return _rooms.values().stream().filter(room -> room.getHouseId().id == houseId).collect(Collectors.toList());
    }

    @Override
    public Iterable<Room> findAllByHouseIdAndOutdoorIndoor(long id, OutdoorIndoor outdoorIndoor) {
        return _rooms.values().stream()
                .filter(room -> room.getHouseId().id == id && room.getOutdoorIndoor() == outdoorIndoor)
                .collect(Collectors.toList());
    }
}