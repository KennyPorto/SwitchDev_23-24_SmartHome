package SmartHome.service;

import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.repository.RoomRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService
{
    private final RoomRepository _roomRepository;
    private final RoomFactory _roomFactory;
    private final HouseFactory _houseFactory;
    private final HouseRepository _houseRepository;

    public RoomService(
          @Qualifier("roomRepoSpringDataImpl") RoomRepository roomRepository,
          RoomFactory roomFactory,
          HouseFactory houseFactory,
          @Qualifier("houseRepoSpringDataImpl") HouseRepository houseRepository)
    {
        this._roomFactory = roomFactory;
        this._roomRepository = roomRepository;
        this._houseFactory = houseFactory;
        this._houseRepository = houseRepository;
    }

    public Room add(RoomName roomName, RoomId roomId, HouseId houseId, Floor floor, Dimensions dimensions, OutdoorIndoor outdoorIndoor)
    {
        if ( !this._houseRepository.existsById(houseId) )
            throw new IllegalArgumentException("House id doesn't exist");

        Room roomToSave = _roomFactory.createRoom(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);

        return _roomRepository.save(roomToSave);
    }

    public Iterable<Room> findAll()
    {
        return this._roomRepository.findAll();
    }

    public Room findById(Long id)
    {
        Optional<RoomId> roomId = _roomFactory.createRoomId(id);
        if ( roomId.isEmpty() ) {
            throw new IllegalArgumentException("Bad id input");
        }

        Optional<Room> room = _roomRepository.findById(roomId.get());

        return room.orElseThrow(
              () -> new IllegalArgumentException("Not found by id.")
        );
    }

    public Pair<Boolean, RoomId> existsById(Long id)
    {
        Optional<RoomId> roomId = _roomFactory.createRoomId(id);

        return roomId.map(value ->
                    Pair.of(_roomRepository.existsById(value), value))
              .orElseGet(() -> Pair.of(false, null));
    }

    public Iterable<Room> findAllByHouseId(long id)
    {
        Optional<HouseId> houseId = _houseFactory.createHouseId(id);

        return houseId.map(value ->
                    this._roomRepository.findAllByHouseId(value.id))
              .orElseGet(List::of);
    }
}
