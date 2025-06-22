package SmartHome.domain.room;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RoomTest
{
    private RoomName roomName;
    private RoomId roomId;
    private HouseId houseId;
    private Floor floor;
    private Dimensions dimensions;
    private RoomName roomNameDouble;
    private RoomId roomIdDouble;
    private HouseId houseIdDouble;
    private Floor floorDouble;
    private Dimensions dimensionsDouble;
    private OutdoorIndoor outdoorIndoor;

    @BeforeEach
    void setup()
    {
        roomName = new RoomName("RoomName");
        roomId = new RoomId(1);
        houseId = new HouseId(1);
        floor = new Floor("Floor1");
        dimensions = new Dimensions(2, 3, 4);
        outdoorIndoor = OutdoorIndoor.OUTDOOR;
    }

    @Test
    void constructor_With_Valid_Arguments_With_Isolation()
    {
        // Arrange
        RoomName roomNameDouble = mock(RoomName.class);
        RoomId roomIdDouble = mock(RoomId.class);
        HouseId houseIdDouble = mock(HouseId.class);
        Floor floorDouble = mock(Floor.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);

        // Act
        Room roomDouble = new Room(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);

        // Assert
        assertNotNull(roomDouble);
    }

    @Test
    void getNameWithIsolation()
    {

        // Arrange
        RoomName roomNameDouble = mock(RoomName.class);
        RoomId roomIdDouble = mock(RoomId.class);
        HouseId houseIdDouble = mock(HouseId.class);
        Floor floorDouble = mock(Floor.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        RoomName expected = roomNameDouble;
        Room roomDouble = new Room(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);

        // Act
        RoomName result = roomDouble.getRoomName();
        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getHouseIdWithIsolation()
    {
        // Arrange
        RoomName roomNameDouble = mock(RoomName.class);
        RoomId roomIdDouble = mock(RoomId.class);
        HouseId houseIdDouble = mock(HouseId.class);
        Floor floorDouble = mock(Floor.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        HouseId expected = houseIdDouble;
        Room roomDouble = new Room(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);

        // Act
        HouseId result = roomDouble.getHouseId();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getHouseFloorWithIsolation()
    {
        // Arrange
        RoomName roomNameDouble = mock(RoomName.class);
        RoomId roomIdDouble = mock(RoomId.class);
        HouseId houseIdDouble = mock(HouseId.class);
        Floor floorDouble = mock(Floor.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        Floor expected = floorDouble;
        Room roomDouble = new Room(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);

        // Act
        Floor result = roomDouble.getHouseFloor();
        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getDimensionsWithIsolation()
    {
        // Arrange
        RoomName roomNameDouble = mock(RoomName.class);
        RoomId roomIdDouble = mock(RoomId.class);
        HouseId houseIdDouble = mock(HouseId.class);
        Floor floorDouble = mock(Floor.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        Dimensions expected = dimensionsDouble;
        Room roomDouble = new Room(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);

        // Act
        Dimensions result = roomDouble.getDimensions();
        // Assert
        assertEquals(expected, result);
    }

    @Test
    void sameObjectEqualsTrueWithIsolation()
    {
        // Arrange
        roomNameDouble = mock(RoomName.class);
        roomIdDouble = mock(RoomId.class);
        houseIdDouble = mock(HouseId.class);
        floorDouble = mock(Floor.class);
        dimensionsDouble = mock(Dimensions.class);
        Room roomDouble = new Room(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);

        // Act
        boolean actual = roomDouble.sameAs(roomDouble);
        // Assert
        assertTrue(actual);
    }

    @Test
    void differentObjectEqualsFalse()
    {
        // Arrange
        RoomName roomNameDouble = mock(RoomName.class);
        RoomId roomIdDouble = mock(RoomId.class);
        HouseId houseIdDouble = mock(HouseId.class);
        Floor floorDouble = mock(Floor.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        Room roomDouble = new Room(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);
        int differentObject = 1;

        // Act and Assert
        assertFalse(roomDouble.sameAs(differentObject));
    }

    @Test
    void objectWithTheSameAttributesEqualsTrueWithIsolation()
    {
        // Arrange
        roomNameDouble = mock(RoomName.class);
        roomIdDouble = mock(RoomId.class);
        houseIdDouble = mock(HouseId.class);
        floorDouble = mock(Floor.class);
        dimensionsDouble = mock(Dimensions.class);
        Room roomDouble = new Room(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);
        Room roomDouble2 = new Room(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);

        // Act
        boolean actual = roomDouble.sameAs(roomDouble2);
        // Assert
        assertTrue(actual);
    }

    @Test
    void sameTypeObjectWithDifferentAttributeEqualsFalseWithIsolation()
    {
        // Arrange
        roomNameDouble = mock(RoomName.class);
        RoomName roomNameDouble2 = mock(RoomName.class);
        roomIdDouble = mock(RoomId.class);
        houseIdDouble = mock(HouseId.class);
        floorDouble = mock(Floor.class);
        dimensionsDouble = mock(Dimensions.class);
        Room roomDouble = new Room(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);
        Room roomDouble2 = new Room(roomNameDouble2, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);

        // Act
        boolean actual = roomDouble.sameAs(roomDouble2);
        // Assert
        assertFalse(actual);
    }

    @Test
    void constructorWithValidArguments()
    {
        // Arrange
        RoomName roomName = new RoomName("RoomName");
        RoomId roomId = new RoomId(1);
        HouseId houseId = new HouseId(1);
        Floor floor = new Floor("Floor1");
        Dimensions dimensions = new Dimensions(2, 3, 4);

        // Act
        Room room = new Room(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);

        // Assert
        assertNotNull(room);
    }

    @Test
    void getName()
    {

        // Arrange
        RoomName roomName = new RoomName("RoomName");
        RoomId roomId = new RoomId(1);
        HouseId houseId = new HouseId(1);
        Floor floor = new Floor("Floor1");
        Dimensions dimensions = new Dimensions(2, 3, 4);
        Room myRoom = new Room(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);
        RoomName expected = roomName;

        // Act
        RoomName result = myRoom.getRoomName();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getHouseId()
    {
        // Arrange
        RoomName roomName = new RoomName("RoomName");
        RoomId roomId = new RoomId(1);
        HouseId houseId = new HouseId(1);
        Floor floor = new Floor("Floor1");
        Dimensions dimensions = new Dimensions(2, 3, 4);
        Room myRoom = new Room(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);
        HouseId expected = houseId;

        // Act
        HouseId result = myRoom.getHouseId();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getHouseFloor()
    {
        // Arrange
        RoomName roomName = new RoomName("RoomName");
        RoomId roomId = new RoomId(1);
        HouseId houseId = new HouseId(1);
        Floor floor = new Floor("Floor1");
        Dimensions dimensions = new Dimensions(2, 3, 4);
        Room myRoom = new Room(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);
        Floor expected = floor;

        // Act
        Floor result = myRoom.getHouseFloor();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getDimensions()
    {
        // Arrange
        RoomName roomName = new RoomName("RoomName");
        RoomId roomId = new RoomId(1);
        HouseId houseId = new HouseId(1);
        Floor floor = new Floor("Floor1");
        Dimensions dimensions = new Dimensions(2, 3, 4);
        Room myRoom = new Room(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);
        Dimensions expected = dimensions;

        // Act
        Dimensions result = myRoom.getDimensions();

        // Assert
        assertEquals(expected, result);
    }


    @Test
    void sameObjectEqualsTrue()
    {
        // Arrange
        Room room = new Room(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);

        // Act
        boolean actual = room.sameAs(room);

        // Assert
        assertTrue(actual);
    }

    @Test
    void objectWithTheSameAttributesEqualsTrue()
    {
        // Arrange
        Room room = new Room(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);
        Room room2 = new Room(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);

        // Act
        boolean actual = room.sameAs(room2);

        // Assert
        assertTrue(actual);
    }
}