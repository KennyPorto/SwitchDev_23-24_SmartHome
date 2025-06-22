package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RoomDTOTest {

    @Test
    void createValidRoomDTO() {
        // Arrange
        String expectedRoomName = "name";
        long expectedRoomId = 2;
        long expectedHouseId = 6;
        String expectedFloor = "floor";
        double expectedHeight = 3.0;
        double expectedWidth = 4.0;
        double expectedLength = 5.0;
        String expectedOutdoorIndoor = "OUTDOOR";

        // Act
        String roomName = "name";
        long roomId = 2;
        long houseId = 6;
        String floor = "floor";
        double height = 3;
        double width = 4;
        double length = 5;
        String outdoorIndoor = "OUTDOOR";
        RoomDTO roomDTO = new RoomDTO(roomName, roomId, houseId, floor, height, width, length, outdoorIndoor);

        // Assert
        assertEquals(expectedRoomName, roomDTO.roomName);
        assertEquals(expectedRoomId, roomDTO.roomId);
        assertEquals(expectedHouseId, roomDTO.houseId);
        assertEquals(expectedFloor, roomDTO.houseFloor);
        assertEquals(expectedHeight, roomDTO.height);
        assertEquals(expectedWidth, roomDTO.width);
        assertEquals(expectedLength, roomDTO.length);
        assertEquals(expectedOutdoorIndoor, roomDTO.outdoorIndoor);
    }

    @Test
    void createValidRoomDTO_AutomaticIdGeneration() {
        // Arrange
        String expectedRoomName = "name";
        long expectedHouseId = 6;
        String expectedFloor = "floor";
        double expectedHeight = 3.0;
        double expectedWidth = 4.0;
        double expectedLength = 5.0;
        String expectedOutdoorIndoor = "OUTDOOR";

        // Act
        String roomName = "name";
        long houseId = 6;
        String floor = "floor";
        double height = 3;
        double width = 4;
        double length = 5;
        String outdoorIndoor = "OUTDOOR";
        RoomDTO roomDTO = new RoomDTO(roomName, houseId, floor, height, width, length, outdoorIndoor);

        // Assert
        assertEquals(expectedRoomName, roomDTO.roomName);
        assertEquals(expectedHouseId, roomDTO.houseId);
        assertEquals(expectedFloor, roomDTO.houseFloor);
        assertEquals(expectedHeight, roomDTO.height);
        assertEquals(expectedWidth, roomDTO.width);
        assertEquals(expectedLength, roomDTO.length);
        assertEquals(expectedOutdoorIndoor, roomDTO.outdoorIndoor);
    }
}

