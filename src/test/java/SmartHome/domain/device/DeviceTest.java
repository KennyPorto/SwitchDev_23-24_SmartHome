package SmartHome.domain.device;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeviceTest {

    @Test
    void getRoomId() {
        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device myDevice = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        RoomId expected = roomIdDouble;

        // Act
        RoomId result = myDevice.getRoomId();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getIdentity() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device myDevice = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        DeviceID expected = deviceIdDouble;

        // Act
        DeviceID result = myDevice.identity();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getName() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device myDevice = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        DeviceName expected = deviceNameDouble;

        // Act
        DeviceName result = myDevice.getName();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getModel() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device myDevice = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        DeviceModel expected = deviceModelDouble;

                // Act
        DeviceModel result = myDevice.getModel();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void objectNotNull() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device myDevice = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);

        // Act
        boolean result = myDevice == null;

        // Assert
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueEqualsWithSameObject()
    {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device myDevice = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);

        // Act
        boolean isEquals = myDevice.equals(myDevice);

        // Assert
        assertTrue(isEquals);
    }

    @Test
    void shouldReturnFalseEqualsWithDifferentObject()
    {   // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device myDevice = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        int differentObject = 1;

        // Act and Assert
        assertNotEquals(myDevice, differentObject);
    }

    @Test
    void shouldReturnTrueEqualsDevicesWithSameParameters()
    {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device device1 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        Device device2 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);

        // Act
        boolean isEquals = device1.equals(device2);

        // Assert
        assertTrue(isEquals);
    }

    @Test
    void shouldReturnFalseEqualsDevicesWithDifferentParameters()
    {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device myDevice = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        RoomId roomIdDouble2 = mock(RoomId.class);
        DeviceID deviceIDDouble2 = mock(DeviceID.class);
        DeviceName deviceNameDouble2 = mock(DeviceName.class);
        DeviceModel deviceModelDouble2 = mock(DeviceModel.class);
        Device device1 = new Device(roomIdDouble2, deviceIDDouble2, deviceNameDouble2, deviceModelDouble2, deviceFactoryDouble);

        // Act
        boolean isEquals = myDevice.equals(device1);

        // Assert
        assertFalse(isEquals);
    }

    @Test
    void shouldReturnTrueSameAsDevicesWithSameParameters() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device device1 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        Device device2 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);

        // Act
        boolean isSame = device1.sameAs(device2);

        // Assert
        assertTrue(isSame);
    }

    @Test
    void shouldReturnFalseSameAsDevicesWithDiffName() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceName deviceNameDouble1 = mock(DeviceName.class);
        DeviceName deviceNameDouble2 = mock(DeviceName.class);
        Device device1 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble1, deviceModelDouble, deviceFactoryDouble);
        Device device2 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble2, deviceModelDouble, deviceFactoryDouble);

        // Act
        boolean isSame = device1.sameAs(device2);

        // Assert
        assertFalse(isSame);
    }

    @Test
    void shouldReturnFalseSameAsDevicesWithDiffDeviceModel() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceModel deviceModelDouble1 = mock(DeviceModel.class);
        DeviceModel deviceModelDouble2 = mock(DeviceModel.class);
        Device device1 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble1, deviceFactoryDouble);
        Device device2 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble2, deviceFactoryDouble);

        // Act
        boolean isSame = device1.sameAs(device2);

        // Assert
        assertFalse(isSame);
    }

    @Test
    void shouldReturnFalseSameAsDevicesWithDiffTypes() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device device1 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        int device2 = 1;

        // Act and Assert
        assertFalse(device1.sameAs(device2));
    }

    @Test
    void getDeviceStatus() {
        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceStatus deviceStatusDouble = mock(DeviceStatus.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        when(deviceFactoryDouble.createDeviceStatus(true)).thenReturn(deviceStatusDouble);
        Device device = new Device(roomIdDouble, deviceIDDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);

        // Assert
        assertEquals(deviceStatusDouble, device.getDeviceStatus());
    }

    @Test
    void deactivateDevice() {
        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device myDevice = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        DeviceStatus deviceStatusDouble = mock(DeviceStatus.class);
        DeviceStatus deviceStatusDeactivatedDouble = mock(DeviceStatus.class);
        when(deviceFactoryDouble.createDeviceStatus(false)).thenReturn(deviceStatusDeactivatedDouble);
        when(deviceFactoryDouble.createDeviceStatus(true)).thenReturn(deviceStatusDouble);

        //Act
        boolean result = myDevice.deactivateDevice();

        // Assert
        assertTrue(result);
        assertEquals(deviceStatusDeactivatedDouble, myDevice.getDeviceStatus());
    }

    @Test
    void shouldBeTheSameHashCode() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        Device device1 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        Device device2 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);

        // Act
        int hashCode1 = device1.hashCode();
        int hashCode2 = device2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void shouldBeDifferentHashCode() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        RoomId roomIdDouble2 = mock(RoomId.class);

        Device device1 = new Device(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);
        Device device2 = new Device(roomIdDouble2, deviceIdDouble, deviceNameDouble, deviceModelDouble, deviceFactoryDouble);

        // Act
        int hashCode1 = device1.hashCode();
        int hashCode2 = device2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }

    // Aggregate tests

    @Test
    void getAggregateRoomId() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);
        RoomId expected = roomId;

        // Act
        RoomId result = myDevice.getRoomId();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getAggregateIdentity() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);
        DeviceID expected = deviceId;

        // Act
        DeviceID result = myDevice.identity();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getAggregateName() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);
        DeviceName expected = deviceName;

        // Act
        DeviceName result = myDevice.getName();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getAggregateModel() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);
        DeviceModel expected = deviceModel;

        // Act
        DeviceModel result = myDevice.getModel();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void shouldAggregateReturnFalseEqualsWithNull() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);

        // Act
        boolean isEquals = myDevice == null;

        // Assert
        assertFalse(isEquals);
    }

    @Test
    void shouldAggregateReturnTrueEqualsWithSameObject() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);

        // Act
        boolean isEquals = myDevice.equals(myDevice);

        // Assert
        assertTrue(isEquals);
    }

    @Test
    void shouldAggregateReturnTrueEqualsDevicesWithSameParameters() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);
        Device device1 = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);

        // Act
        boolean isEquals = myDevice.equals(device1);

        // Assert
        assertTrue(isEquals);
    }

    @Test
    void shouldAggregateReturnFalseEqualsDevicesWithDifferentParameters() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);
        RoomId roomId2 = new RoomId(2L);
        DeviceID deviceId2 = new DeviceID(2L);
        DeviceName deviceName2 = new DeviceName("AirConditioner");
        DeviceModel deviceModel2 = new DeviceModel("LG");
        DeviceFactory deviceFactory2 = new DeviceFactory();
        Device device1 = new Device(roomId2, deviceId2, deviceName2, deviceModel2, deviceFactory2);

        // Act
        boolean isEquals = myDevice.equals(device1);

        // Assert
        assertFalse(isEquals);
    }

    @Test
    void shouldAggregateReturnTrueSameAsDevicesWithSameParameters() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);
        Device device1 = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);

        // Act
        boolean isSame = myDevice.sameAs(device1);

        // Assert
        assertTrue(isSame);
    }

    @Test
    void shouldAggregateBeTheSameHashCode() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);
        Device device1 = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);

        // Act
        int hashCode1 = myDevice.hashCode();
        int hashCode2 = device1.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void shouldAggregateBeDifferentHashCode() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device myDevice = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);
        RoomId roomId2 = new RoomId(2L);
        Device device1 = new Device(roomId2, deviceId, deviceName, deviceModel, deviceFactory);

        // Act
        int hashCode1 = myDevice.hashCode();
        int hashCode2 = device1.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    void getAggregateDeviceStatus() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        boolean expected = true;
        Device device = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);

        // Act
        boolean result = device.getDeviceStatus().activated;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void deactivateAggregateDevice() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        Device device = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);

        //Act
        boolean result = device.getDeviceStatus().activated;

        // Assert
        assertTrue(result);
    }

    @Test
    void deactivateAggregateDeviceGetDeviceStatus() {

        // Arrange
        RoomId roomId = new RoomId(1L);
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        DeviceFactory deviceFactory = new DeviceFactory();
        boolean expected = false;
        Device device = new Device(roomId, deviceId, deviceName, deviceModel, deviceFactory);
        device.deactivateDevice();

        //Act
        boolean result = device.getDeviceStatus().activated;

        // Assert
        assertEquals(expected, result);
    }

}