package SmartHome.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeviceDTOTest {

    private DeviceDTO deviceDTO;

    @BeforeEach
    public void setup() {
        deviceDTO = new DeviceDTO(1, 1, "d1", "dm1");
    }

    @Test
    void validDeviceDTO_Constructor() {
        // Arrange
        long roomId = 1L;
        String deviceName = "Device1";
        String deviceModel = "Model1";
        boolean isActive = true;

        // Act
        DeviceDTO deviceDTO = new DeviceDTO(roomId, deviceName, deviceModel, isActive);

        // Assert
        assertEquals(roomId, deviceDTO.roomId);
        assertEquals(deviceName, deviceDTO.deviceName);
        assertEquals(deviceModel, deviceDTO.deviceModel);
        assertEquals(isActive, deviceDTO.isActive);
    }

    @Test
    void getRoomId() {

        // Arrange
        long expected = 1;

        // Act
        long result = deviceDTO.roomId;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getDeviceId() {

        // Arrange
        long expected = 1;

        // Act
        long result = deviceDTO.deviceID;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getName() {

        // Arrange
        String expected = "d1";

        // Act
        String result = deviceDTO.deviceName;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getModel() {

        // Arrange
        String expected = "dm1";

        // Act
        String result = deviceDTO.deviceModel;

        // Assert
        assertEquals(expected, result);
    }

}
