package SmartHome.mapper;

import SmartHome.domain.device.Device;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.jpa.datamodel.DeviceDataModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeviceMapperTest
{
    @Test
    void getDeviceDTOFromDomain2DTO()
    {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        Device device = mock(Device.class);
        when(device.getRoomId()).thenReturn(roomIdDouble);
        when(device.identity()).thenReturn(deviceIDDouble);
        when(device.getName()).thenReturn(deviceNameDouble);
        when(device.getModel()).thenReturn(deviceModelDouble);

        // Act
        DeviceDTO result = new DeviceDTO(roomIdDouble.id, deviceIDDouble.id, deviceNameDouble.name, deviceModelDouble.model);

        // Assert
        assertEquals(device.getRoomId().id, result.roomId);
        assertEquals(device.identity().id, result.deviceID);
        assertEquals(device.getName().name, result.deviceName);
        assertEquals(device.getModel().model, result.deviceModel);
    }

    @Test
    void getListDevicesDomainToDto()
    {
        // Arrange
        List<Device> devices = new ArrayList<>();
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceStatus deviceStatusDouble = mock(DeviceStatus.class);
        Device device = mock(Device.class);
        when(device.getRoomId()).thenReturn(roomIdDouble);
        when(device.identity()).thenReturn(deviceIDDouble);
        when(device.getName()).thenReturn(deviceNameDouble);
        when(device.getModel()).thenReturn(deviceModelDouble);
        when(device.getDeviceStatus()).thenReturn(deviceStatusDouble);
        devices.add(device);
        DeviceDTO deviceDTO = new DeviceDTO(1L, 0L, "d1", "dm1", true);

        // Act
        Iterable<DeviceDTO> result = DeviceMapper.deviceListToDto(devices);
        Iterable<DeviceDTO> expected = List.of(deviceDTO);

        // Assert
        assertEquals(expected.iterator().next().deviceID, result.iterator().next().deviceID);
    }

    @Test
    void deviceDataModelToDomain()
    {
        // Arrange
        DeviceDataModel deviceDataModel = mock(DeviceDataModel.class);
        when(deviceDataModel.getId()).thenReturn(1L);
        when(deviceDataModel.getDeviceName()).thenReturn("d1");
        when(deviceDataModel.getDeviceModel()).thenReturn("dm1");
        when(deviceDataModel.getRoomId()).thenReturn(1L);

        // Act
        Device result = DeviceMapper.deviceDataModelToDomain(deviceDataModel);

        // Assert
        assertEquals(1L, result.identity().id);
        assertEquals("d1", result.getName().name);
        assertEquals("dm1", result.getModel().model);
        assertEquals(1L, result.getRoomId().id);
    }

    @Test
    void devicesDataModelToDomain()
    {
        // Arrange
        List<DeviceDataModel> deviceDataModels = new ArrayList<>();
        DeviceDataModel deviceDataModel = mock(DeviceDataModel.class);
        deviceDataModels.add(deviceDataModel);
        when(deviceDataModel.getId()).thenReturn(1L);
        when(deviceDataModel.getDeviceName()).thenReturn("d1");
        when(deviceDataModel.getDeviceModel()).thenReturn("dm1");
        when(deviceDataModel.getRoomId()).thenReturn(1L);


        // Act
        Iterable<Device> result = DeviceMapper.devicesDataModelToDomain(deviceDataModels);

        // Assert
        assertTrue(result.iterator().hasNext());
    }
}


