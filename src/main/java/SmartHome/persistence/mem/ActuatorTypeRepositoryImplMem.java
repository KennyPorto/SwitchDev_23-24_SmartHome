package SmartHome.persistence.mem;

import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.repository.ActuatorTypeRepository;
import SmartHome.domain.valueObjects.ActuatorTypeId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ActuatorTypeRepositoryImplMem implements ActuatorTypeRepository
{

    private final Map<ActuatorTypeId, ActuatorType> data = new HashMap<>();

    @Override
    public ActuatorType save(ActuatorType entity) {
        if (entity == null) return null;
        if (existsById(entity.identity())) return null;
        data.put(entity.identity(), entity);
        return data.get(entity.identity());
    }

    @Override
    public Iterable<ActuatorType> findAll() {
        return data.values();
    }

    @Override
    public Optional<ActuatorType> findById(ActuatorTypeId id) {
        return data.containsKey(id) ? Optional.of(data.get(id)) : Optional.empty();
    }

    @Override
    public boolean existsById(ActuatorTypeId id) {
        Set<ActuatorTypeId> keys = data.keySet();
        for (ActuatorTypeId key : keys) {
            if (key.toString().equals(id.toString())) {
                return true;
            }
        }
        return false;
    }
}
