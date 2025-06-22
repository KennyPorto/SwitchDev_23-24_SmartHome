package SmartHome.domain.repository;

import SmartHome.ddd.Repository;
import SmartHome.domain.device.Device;
import SmartHome.domain.valueObjects.DeviceID;

public interface DeviceRepository extends Repository<DeviceID, Device> {
    Iterable<Device> findAllByRoomId(Long roomId);

    boolean isActiveById(DeviceID deviceID);
}
