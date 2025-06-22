package SmartHome.persistence.springdata;

import SmartHome.domain.device.Device;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.valueObjects.DeviceID;
import SmartHome.mapper.DeviceMapper;
import SmartHome.persistence.jpa.datamodel.DeviceDataModel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DeviceRepoSpringDataImpl implements DeviceRepository
{
    private final DeviceRepoSpringDataInterface _deviceInterface;

    public DeviceRepoSpringDataImpl(DeviceRepoSpringDataInterface deviceRepoSpringDataInterface)
    {
        this._deviceInterface = deviceRepoSpringDataInterface;
    }

    @Override
    public Device save(Device device)
    {
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);

        DeviceDataModel result = _deviceInterface.save(deviceDataModel);

        return DeviceMapper.deviceDataModelToDomain(result);
    }

    @Override
    public Iterable<Device> findAllByRoomId(Long roomId)
    {
        Iterable<DeviceDataModel> models = this._deviceInterface.findAllByRoomId(roomId);

        return DeviceMapper.devicesDataModelToDomain(models);
    }

    @Override
    public Iterable<Device> findAll()
    {
        Iterable<DeviceDataModel> models = this._deviceInterface.findAll();
        return DeviceMapper.devicesDataModelToDomain(models);
    }

    @Override
    public Optional<Device> findById(DeviceID id)
    {
        Optional<DeviceDataModel> model = this._deviceInterface.findById(id.id);

        if (model.isPresent()) {
            return Optional.of(DeviceMapper.deviceDataModelToDomain(model.get()));
        }

        return Optional.empty();
    }

    @Override
    public boolean isActiveById(DeviceID deviceID)
    {
        Optional<Device> device = findById(deviceID);

        if (device.isEmpty()) {
            return false;
        }

        return device.get().getDeviceStatus().activated;
    }

    @Override
    public boolean existsById(DeviceID id)
    {
        return this._deviceInterface.existsById(id.id);
    }
}
