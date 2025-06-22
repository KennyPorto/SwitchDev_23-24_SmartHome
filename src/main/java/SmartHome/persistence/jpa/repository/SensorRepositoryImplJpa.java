package SmartHome.persistence.jpa.repository;

import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.SensorId;
import SmartHome.mapper.SensorMapper;
import SmartHome.persistence.jpa.datamodel.SensorDataModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public class SensorRepositoryImplJpa implements SensorRepository
{
    @PersistenceContext
    private final EntityManager entityManager;


    public SensorRepositoryImplJpa(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public Sensor save(Sensor sensor)
    {
        SensorDataModel dataModel = new SensorDataModel(sensor);

        SensorDataModel result = entityManager.merge(dataModel);

        return SensorMapper.dataModelToDomain(result);
    }

    @Override
    public Iterable<Sensor> findAll()
    {
        TypedQuery<SensorDataModel> query = entityManager.createQuery(
              "SELECT a FROM SensorDataModel a", SensorDataModel.class
        );

        return SensorMapper.dataModelListToDomain(query.getResultList());
    }

    @Override
    public Optional<Sensor> findById(SensorId id)
    {
        SensorDataModel sensor = entityManager.find(SensorDataModel.class, id.id);
        if ( sensor == null )
            return Optional.empty();
        return Optional.of(SensorMapper.dataModelToDomain(sensor));
    }

    @Override
    public boolean existsById(SensorId id)
    {
        Optional<Sensor> sensor = findById(id);
        return sensor.isPresent();
    }

    @Override
    public Iterable<Sensor> findAllByDeviceId(Long deviceId)
    {
        TypedQuery<SensorDataModel> query = entityManager.createQuery(
                "SELECT s FROM SensorDataModel s WHERE s.deviceId = :deviceId", SensorDataModel.class
        );
        query.setParameter("deviceId", deviceId);
        return SensorMapper.dataModelListToDomain(query.getResultList());
    }

    @Override
    public Iterable<Sensor> findAllByDeviceIdAndSensorTypeId(long deviceId, String sensorTypeId) {
        TypedQuery<SensorDataModel> query = entityManager.createQuery(
                "SELECT s FROM SensorDataModel s WHERE s.deviceId = :deviceId AND s.sensorTypeId = :sensorTypeId",
                SensorDataModel.class);
        query.setParameter("deviceId", deviceId);
        query.setParameter("sensorTypeId", sensorTypeId);

        List<SensorDataModel> listDataModel = query.getResultList();

        return SensorMapper.dataModelListToDomain(listDataModel);
    }
}
