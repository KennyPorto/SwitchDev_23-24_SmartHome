package SmartHome.persistence.mem;

import SmartHome.domain.repository.SensorTypeRepository;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.valueObjects.SensorTypeId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SensorTypeRepositoryImplMem implements SensorTypeRepository
{
    private final Map<SensorTypeId, SensorType> _data = new HashMap<>();
    @Override
    public SensorType save(SensorType entity) {
        if (existsById(entity.identity())) return null;
        _data.put(entity.identity(), entity);
        return entity;
    }

    @Override
    public Iterable<SensorType> findAll() {
        return _data.values();
    }

    @Override
    public Optional<SensorType> findById(SensorTypeId id) {
        return _data.containsKey(id) ? Optional.of(_data.get(id)) : Optional.empty();
    }

    @Override
    public boolean existsById(SensorTypeId id) {
        Set<SensorTypeId> keys = _data.keySet();
        for (SensorTypeId key : keys) {
            if (key.toString().equals(id.toString())) {
                return true;
            }
        }

        return false;
    }
}
