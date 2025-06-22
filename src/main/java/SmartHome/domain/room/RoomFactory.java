package SmartHome.domain.room;

import SmartHome.domain.valueObjects.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomFactory {
    public Room createRoom(RoomName roomName, RoomId roomId, HouseId houseId, Floor houseFloor, Dimensions dimensions, OutdoorIndoor outdoorIndoor){
        return new Room(roomName, roomId, houseId, houseFloor, dimensions, outdoorIndoor);
    }

    public Optional<RoomName> createRoomName(String name) {
        try {
            return Optional.of(new RoomName(name));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<RoomId> createRoomId(Long id) {
        try {
            return Optional.of(new RoomId(id));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<Floor> createFloor(String floor) {
        try {
            return Optional.of(new Floor(floor));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<Dimensions> createDimensions(double height, double width, double length) {
        try {
            return Optional.of(new Dimensions(height, width, length));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
