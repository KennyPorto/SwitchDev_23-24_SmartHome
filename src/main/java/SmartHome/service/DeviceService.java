package SmartHome.service;

import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.repository.RoomRepository;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService
{
    private final DeviceFactory _deviceFactory;
    private final DeviceRepository _deviceRepo;
    private final RoomFactory _roomFactory;
    private final RoomRepository _roomRepository;

    public DeviceService( DeviceFactory deviceFactory,
                          @Qualifier ("deviceRepoSpringDataImpl") DeviceRepository deviceRepo,
                          RoomFactory roomFactory,
                          @Qualifier("roomRepoSpringDataImpl") RoomRepository roomRepository)
    {
        if (deviceFactory == null || deviceRepo == null || roomFactory == null || roomRepository == null)
            throw new IllegalArgumentException();
        this._deviceFactory = deviceFactory;
        this._deviceRepo = deviceRepo;
        this._roomFactory = roomFactory;
        this._roomRepository = roomRepository;
    }

    public Device add(DeviceName deviceName, DeviceID deviceId, DeviceModel deviceModel, RoomId roomId)
    {
        if (!this._roomRepository.existsById(roomId))
        {
            throw new IllegalArgumentException("Room id doesn't exist");
        }

        Device deviceToSave = _deviceFactory.createDevice(roomId, deviceId, deviceName, deviceModel, new DeviceStatus(true));
        if (deviceToSave == null)
        {
            throw new IllegalArgumentException("Invalid device attributes");
        }

        return this._deviceRepo.save(deviceToSave);
    }

    public Iterable<Device> findAll()
    {
        return this._deviceRepo.findAll();
    }


    public Device findById(Long id)
    {
        Optional<DeviceID> deviceId = _deviceFactory.createDeviceId(id);

        if (deviceId.isPresent())
        {
            Optional<Device> device = _deviceRepo.findById(deviceId.get());
            return device.orElse(null);
        }

        return null;
    }


    public Pair<Boolean, DeviceID> existsById(Long id)
    {
        Optional<DeviceID> deviceId = _deviceFactory.createDeviceId(id);
        return deviceId.map(value ->
                    Pair.of(_deviceRepo.existsById(value), value))
            .orElseGet(() -> Pair.of(false, null));
    }

    public Iterable<Device> findAllByRoomId(long id)
    {
        Optional<RoomId> roomId = _roomFactory.createRoomId(id);
        return roomId.map(value -> this._deviceRepo.findAllByRoomId(value.id))
                .orElseGet(List::of);
    }

    public Device deactivateDevice(long id)
    {
        Optional<DeviceID> deviceId = _deviceFactory.createDeviceId(id);

        if (deviceId.isPresent()) {
            Optional<Device> device = _deviceRepo.findById(deviceId.get());
            if (device.isPresent()) {
                Device deviceToDeactivate = device.get();
                if (!deviceToDeactivate.getDeviceStatus().activated) {
                    throw new IllegalArgumentException("Device is not activated");
                }
                deviceToDeactivate.deactivateDevice();

                return _deviceRepo.save(deviceToDeactivate);
            } else {
                throw new IllegalArgumentException("Device not found");
            }
        } else {
            throw new IllegalArgumentException("Invalid device ID");
        }
    }
}
