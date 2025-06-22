package SmartHome.persistence.mem;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.valueObjects.ActuatorId;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static SmartHome.utils.Constants.ACTUATOR_CONFIG;

public class ActuatorRepositoryImplMem implements ActuatorRepository
{
    private final List<String> _availableModels;
    private final Map<ActuatorId, Actuator> _actuatorData = new HashMap<>();

    public ActuatorRepositoryImplMem()
    {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(new File(ACTUATOR_CONFIG));
            String[] actuators = config.getStringArray("actuator");
            this._availableModels = List.of(actuators);
        } catch (Exception exception) {
            String errorMessage = "Error occurred while reading the configuration file '" + ACTUATOR_CONFIG + "': " + exception.getMessage();
            throw new IllegalArgumentException(errorMessage);
        }
    }

    @Override
    public Actuator save(Actuator actuator) {
        if ( actuator == null ) return null;
        if ( existsById(actuator.identity()) ) return null;

        this._actuatorData.put(actuator.identity(), actuator);
        return this._actuatorData.get(actuator.identity());
    }

    @Override
    public List<Actuator> findAll() {
        return new ArrayList<>(_actuatorData.values());
    }

    @Override
    public Optional<Actuator> findById(ActuatorId id) {
        return existsById(id) ? Optional.of(this._actuatorData.get(id)) : Optional.empty();
    }

    @Override
    public boolean existsById(ActuatorId id) {
        ActuatorId actuatorID = this._actuatorData.keySet().stream()
              .filter(key -> key.id == id.id)
              .findFirst()
              .orElse(null);
        return actuatorID != null;
    }

    @Override
    public Iterable<Actuator> findAllByDeviceId(Long deviceId) {
        return _actuatorData.values().stream().filter(actuator -> actuator.getDeviceID().id == deviceId).collect(Collectors.toList());
    }
}
