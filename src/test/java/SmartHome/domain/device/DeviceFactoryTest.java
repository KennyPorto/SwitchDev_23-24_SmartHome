package SmartHome.domain.device;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class DeviceFactoryTest {

    @Test
    void validCreateDevice() {
        // Arrange
        DeviceFactory deviceFactory = new DeviceFactory();
        RoomId roomId = mock(RoomId.class);
        DeviceID deviceID = mock(DeviceID.class);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);

        // Act
        Device device = deviceFactory.createDevice(roomId, deviceID, deviceName, deviceModel,
                new DeviceStatus(true));

        // Assert
        assertEquals(deviceID, device.identity());
        assertEquals(deviceName, device.getName());
        assertEquals(deviceModel, device.getModel());
    }

    @Test
    void createDeviceId() {
        // Arrange
        DeviceFactory deviceFactory = new DeviceFactory();
        long id = 1;

        // Act
        Optional<DeviceID> optionalDeviceID = deviceFactory.createDeviceId(id);

        // Assert
        assertTrue(optionalDeviceID.isPresent());
    }

    @Test
    void createDeviceId_fail() {
        // Arrange
        DeviceFactory deviceFactory = new DeviceFactory();
        long id = -1;

        // Act
        Optional<DeviceID> optionalDeviceID = deviceFactory.createDeviceId(id);

        // Assert
        assertTrue(optionalDeviceID.isEmpty());
    }

    @Test
    void createDeviceName() {
        // Arrange
        DeviceFactory deviceFactory = new DeviceFactory();
        String name = "Heater";

        // Act
        Optional<DeviceName> optionalDeviceName = deviceFactory.createDeviceName(name);

        // Assert
        assertTrue(optionalDeviceName.isPresent());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void createDeviceName_fail(String name) {
        // Arrange
        DeviceFactory deviceFactory = new DeviceFactory();

        // Act
        Optional<DeviceName> optionalDeviceName = deviceFactory.createDeviceName(name);

        // Assert
        assertTrue(optionalDeviceName.isEmpty());
    }

    @Test
    void createDeviceModel() {
        // Arrange
        DeviceFactory deviceFactory = new DeviceFactory();
        String model = "Xiaomi";

        // Act
        Optional<DeviceModel> optionalDeviceModel = deviceFactory.createDeviceModel(model);

        // Assert
        assertTrue(optionalDeviceModel.isPresent());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void createDeviceModel_fail(String model) {
        // Arrange
        DeviceFactory deviceFactory = new DeviceFactory();

        // Act
        Optional<DeviceModel> optionalDeviceModel = deviceFactory.createDeviceModel(model);

        // Assert
        assertTrue(optionalDeviceModel.isEmpty());
    }

    @Test
    void createDeviceStatus(){
        // Arrange
        DeviceFactory deviceFactory = new DeviceFactory();
        boolean activated = true;

        // Act
        DeviceStatus deviceStatus = deviceFactory.createDeviceStatus(activated);

        // Assert
        assertTrue(deviceStatus.activated);
    }
}
