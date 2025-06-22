package SmartHome.domain.room;

import SmartHome.ddd.AggregateRoot;
import SmartHome.domain.valueObjects.*;

public class Room implements AggregateRoot<RoomId>
{
    private final RoomName _roomName;
    private final RoomId _roomId;
    private final HouseId _houseId;
    private final Floor _houseFloor;
    private final Dimensions _dimensions;
    private final OutdoorIndoor _outdoorIndoor;

    protected Room(RoomName roomName, RoomId roomId, HouseId houseId, Floor houseFloor, Dimensions dimensions, OutdoorIndoor outdoorIndoor)
    {
        this._roomName = roomName;
        this._roomId = roomId;
        this._houseId = houseId;
        this._houseFloor = houseFloor;
        this._dimensions = dimensions;
        this._outdoorIndoor = outdoorIndoor;
    }

    @Override
    public RoomId identity()
    {
        return _roomId;
    }

    @Override
    public boolean sameAs(Object object)
    {
        if (this == object)
            return true;

        if (object instanceof Room newRoom) {

            return (this._roomName.equals(newRoom.getRoomName())) &&
                    (this._houseId.equals(newRoom.getHouseId())) &&
                    (this._houseFloor.equals(newRoom.getHouseFloor())) &&
                    (this._outdoorIndoor.toString().equals(newRoom.getOutdoorIndoor().toString())) &&
                    (this._dimensions.equals(newRoom.getDimensions()));
        }
        return false;
    }

    public RoomName getRoomName()
    {
        return _roomName;
    }

    public HouseId getHouseId()
    {
        return _houseId;
    }

    public Floor getHouseFloor()
    {
        return _houseFloor;
    }

    public Dimensions getDimensions()
    {
        return _dimensions;
    }
    public OutdoorIndoor getOutdoorIndoor()
    {
        return _outdoorIndoor;
    }

}