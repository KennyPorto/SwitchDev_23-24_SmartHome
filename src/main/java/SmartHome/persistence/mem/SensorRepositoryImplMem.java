package SmartHome.persistence.mem;

import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.SensorId;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static SmartHome.utils.Constants.SENSOR_CONFIG;

public class SensorRepositoryImplMem implements SensorRepository
{
    private final List<String> _availableModels;
    private final Map<SensorId, Sensor> _sensorData = new HashMap<>();

    public SensorRepositoryImplMem()
    {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(new File(SENSOR_CONFIG));
            String[] sensors = config.getStringArray("sensor");
            this._availableModels = List.of(sensors);
        } catch (Exception exception) {
            String errorMessage = "Error occurred while reading the configuration file '" + SENSOR_CONFIG + "': " + exception.getMessage();
            throw new IllegalArgumentException(errorMessage);
        }
    }


    @Override
    public Sensor save(Sensor sensor) {
        if (sensor == null) return null;
        if ( existsById(sensor.identity()) ) return null;
        _sensorData.put(sensor.identity(), sensor);
        return _sensorData.get(sensor.identity());
    }

    @Override
    public List<Sensor> findAll() {
        return new ArrayList<>(_sensorData.values());
    }

    @Override
    public Optional<Sensor> findById(SensorId id) {
        return existsById(id) ? Optional.of(this._sensorData.get(id)) : Optional.empty();
    }

    @Override
    public boolean existsById(SensorId id) {
        SensorId sensorID = this._sensorData.keySet().stream()
              .filter(key -> key.id == id.id)
              .findFirst()
              .orElse(null);
        return sensorID != null;
    }

    @Override
    public Iterable<Sensor> findAllByDeviceId(Long deviceId) {
        return _sensorData.values().stream().filter(
              sensor -> sensor.getDeviceID().id == deviceId).collect(Collectors.toList());
    }

    @Override
    public Iterable<Sensor> findAllByDeviceIdAndSensorTypeId(long deviceId, String sensorTypeId) {
        return _sensorData.values().stream()
                .filter(sensor -> sensor.getDeviceID().id == deviceId && sensor.getSensorTypeId().id.equals(sensorTypeId))
                .collect(Collectors.toList());
    }
}
