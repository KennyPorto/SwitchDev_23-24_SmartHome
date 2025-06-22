package SmartHome.persistence.springdata;

import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.SensorId;
import SmartHome.mapper.SensorMapper;
import SmartHome.persistence.jpa.datamodel.SensorDataModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SensorRepoSpringDataImpl implements SensorRepository
{
    private final SensorRepoSpringDataInterface sensorRepoSpringDataInterface;

    public SensorRepoSpringDataImpl(SensorRepoSpringDataInterface sensorRepoSpringDataInterface)
    {
        this.sensorRepoSpringDataInterface = sensorRepoSpringDataInterface;
    }

    @Override
    public Iterable<Sensor> findAllByDeviceId(Long deviceId)
    {
        Iterable<SensorDataModel> sensorDataModels = sensorRepoSpringDataInterface.findAllByDeviceId(deviceId);
        return SensorMapper.dataModelListToDomain(sensorDataModels);
    }

    @Override
    public Iterable<Sensor> findAllByDeviceIdAndSensorTypeId(long deviceId, String sensorTypeId) {
        Iterable<SensorDataModel> sensorDataModels = sensorRepoSpringDataInterface.findAllByDeviceIdAndSensorTypeId(deviceId, sensorTypeId);
        return SensorMapper.dataModelListToDomain(sensorDataModels);
    }

    @Override
    public Sensor save(Sensor entity)
    {
        SensorDataModel sensorDataModel = new SensorDataModel(entity);
        SensorDataModel result = sensorRepoSpringDataInterface.save(sensorDataModel);

        return SensorMapper.dataModelToDomain(result);
    }

    @Override
    public Iterable<Sensor> findAll()
    {
        Iterable<SensorDataModel> sensors = sensorRepoSpringDataInterface.findAll();
        return SensorMapper.dataModelListToDomain(sensors);
    }

    @Override
    public Optional<Sensor> findById(SensorId id)
    {
        Optional<SensorDataModel> sensorDataModel = sensorRepoSpringDataInterface.findById(id.id);
        if (sensorDataModel.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(SensorMapper.dataModelToDomain(sensorDataModel.get()));
    }

    @Override
    public boolean existsById(SensorId id)
    {
        return sensorRepoSpringDataInterface.existsById(id.id);
    }
}
