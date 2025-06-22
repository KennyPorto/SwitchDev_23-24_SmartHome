package SmartHome.persistence.mem;

import SmartHome.domain.device.Device;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.valueObjects.DeviceID;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeviceRepositoryImplMem implements DeviceRepository
{

    private final Map<DeviceID, Device> _data = new HashMap<>();

    @Override
    public Device save(Device entity) {
        _data.put(entity.identity(), entity);
        return entity;
    }

    @Override
    public Iterable<Device> findAll() {
        return _data.values();
    }

    @Override
    public Optional<Device> findById(DeviceID id) {
        Optional<DeviceID> deviceID = _data.keySet().stream().filter(deviceId -> deviceId.id == id.id).findFirst();
        return deviceID.map(_data::get);
    }

    @Override
    public boolean existsById(DeviceID id) {
        DeviceID deviceId = _data.keySet().stream().filter(devId -> devId.equals(id)).findFirst().orElse(null);
        return deviceId != null;
    }

    @Override
    public Iterable<Device> findAllByRoomId(Long roomId) {
        return _data.values().stream().filter(device -> device.getRoomId().id == roomId).collect(Collectors.toList());
    }

    @Override
    public boolean isActiveById(DeviceID deviceID) {
        if ( ! existsById(deviceID) ) return false;
        Optional<Device> device = findById(deviceID);
        return device.map(value -> value.getDeviceStatus().activated).orElse(false);
    }

}
