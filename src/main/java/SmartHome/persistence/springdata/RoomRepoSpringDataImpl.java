package SmartHome.persistence.springdata;

import SmartHome.domain.repository.RoomRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.valueObjects.OutdoorIndoor;
import SmartHome.domain.valueObjects.RoomId;
import SmartHome.mapper.RoomMapper;
import SmartHome.persistence.jpa.datamodel.RoomDataModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static SmartHome.mapper.RoomMapper.roomsDataModelToDomain;

@Repository
public class RoomRepoSpringDataImpl implements RoomRepository
{
    private final RoomRepoSpringDataInterface _roomRepoSpringDataInterface;

    public RoomRepoSpringDataImpl(RoomRepoSpringDataInterface roomRepoSpringDataInterface)
    {
        this._roomRepoSpringDataInterface = roomRepoSpringDataInterface;
    }

    @Override
    public Iterable<Room> findAllByHouseId(Long houseId)
    {
        Iterable<RoomDataModel> roomDataModels = _roomRepoSpringDataInterface.findAllByHouseId(houseId);
        return RoomMapper.roomsDataModelToDomain(roomDataModels);
    }

    @Override
    public Iterable<Room> findAllByHouseIdAndOutdoorIndoor(long id, OutdoorIndoor outdoorIndoor) {
        Iterable<RoomDataModel> roomDataModels = _roomRepoSpringDataInterface.findAllByHouseIdAndOutdoorIndoor(id, outdoorIndoor);
        return RoomMapper.roomsDataModelToDomain(roomDataModels);
    }

    @Override
    public Room save(Room entity)
    {
        RoomDataModel roomDataModel = new RoomDataModel(entity);

        RoomDataModel result = _roomRepoSpringDataInterface.save(roomDataModel);

        return RoomMapper.roomDataModelToDomain(result);
    }

    @Override
    public Iterable<Room> findAll()
    {
        Iterable<RoomDataModel> roomDataModels = _roomRepoSpringDataInterface.findAll();

        return roomsDataModelToDomain(roomDataModels);
    }

    @Override
    public Optional<Room> findById(RoomId id)
    {
        Optional<RoomDataModel> roomDataModel = _roomRepoSpringDataInterface.findById(id.id);

        if (roomDataModel.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(RoomMapper.roomDataModelToDomain(roomDataModel.get()));
    }

    @Override
    public boolean existsById(RoomId id)
    {
        return _roomRepoSpringDataInterface.existsById(id.id);
    }
}
