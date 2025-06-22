package SmartHome.persistence.jpa.repository;

import SmartHome.domain.device.Device;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.valueObjects.DeviceID;
import SmartHome.mapper.DeviceMapper;
import SmartHome.persistence.jpa.datamodel.DeviceDataModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class DeviceRepositoryImplJPA implements DeviceRepository
{

    @PersistenceContext
    private EntityManager _entityManager;

    public DeviceRepositoryImplJPA(EntityManager entityManager)
    {
        this._entityManager = entityManager;
    }

    @Override
    public Device save(Device device)
    {
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);

        DeviceDataModel result = _entityManager.merge(deviceDataModel);

        return DeviceMapper.deviceDataModelToDomain(result);
    }

    @Override
    public Iterable<Device> findAll()
    {
        TypedQuery<DeviceDataModel> query = _entityManager.createQuery(
                "select device FROM DeviceDataModel device", DeviceDataModel.class
        );

        return DeviceMapper.devicesDataModelToDomain(query.getResultList());

    }

    @Override
    public Optional<Device> findById(DeviceID id)
    {
        DeviceDataModel device = _entityManager.find(DeviceDataModel.class, id.id);
        if (device == null)
            return Optional.empty();
        return Optional.of(DeviceMapper.deviceDataModelToDomain(device));
    }

    @Override
    public boolean existsById(DeviceID id)
    {
        Optional<Device> device = findById(id);
        return device.isPresent();
    }

    @Override
    public Iterable<Device> findAllByRoomId(Long roomId)
    {
        TypedQuery<DeviceDataModel> query = _entityManager.createQuery(
                "select device FROM DeviceDataModel device " +
                        "where device.roomId = :roomId", DeviceDataModel.class
        );
        query.setParameter("roomId", roomId);

        return DeviceMapper.devicesDataModelToDomain(query.getResultList());
    }

    @Override
    public boolean isActiveById(DeviceID deviceID)
    {
        TypedQuery<DeviceDataModel> query = _entityManager.createQuery(
                "select device FROM DeviceDataModel device " +
                        "where device.deviceStatus = true " +
                        "and device.id = :deviceId", DeviceDataModel.class
        );
        query.setParameter("deviceId", deviceID.id);

        return !query.getResultList().isEmpty();
    }

}
