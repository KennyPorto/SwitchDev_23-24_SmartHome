package SmartHome.service;

import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.SensorFactory;
import SmartHome.domain.valueObjects.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class SensorService {
    private final SensorRepository _sensorRepository;
    private final SensorFactory _sensorFactory;
    private final DeviceRepository _deviceRepository;
    private final DeviceFactory _deviceFactory;

    public SensorService(@Qualifier("sensorRepoSpringDataImpl") SensorRepository sensorRepository, SensorFactory sensorFactory, @Qualifier("deviceRepoSpringDataImpl") DeviceRepository deviceRepository, DeviceFactory deviceFactory)

    {
        this._sensorRepository = sensorRepository;
        this._sensorFactory = sensorFactory;
        this._deviceRepository = deviceRepository;
        this._deviceFactory = deviceFactory;
    }

    public Sensor add(SensorId sensorId, SensorModel model, DeviceID deviceID, SensorTypeId typeName, SensorName name) {
        if (!_deviceRepository.existsById(deviceID)) {
            throw new EntityNotFoundException("Device with this deviceId doesnt exist");
        }

        Sensor sensorToSave = _sensorFactory.createSensor(deviceID, model, name, sensorId, typeName);

        return _sensorRepository.save(sensorToSave);
    }

    public Iterable<Sensor> findAll() {
        return _sensorRepository.findAll();
    }

    public Sensor findById(Long id) {
        Optional<SensorId> sensorId = _sensorFactory.createSensorId(id);

        if (sensorId.isEmpty()) {
            throw new IllegalArgumentException("Bad id input");
        }

        Optional<Sensor> sensor = _sensorRepository.findById(sensorId.get());

        if (sensor.isEmpty()) {
            throw new EntityNotFoundException("Not found by id");
        }

        return sensor.get();
    }
    public Iterable<Sensor> findAllByDeviceId(Long id) {
        Optional<DeviceID> deviceID = _deviceFactory.createDeviceId(id);

        if (deviceID.isEmpty()) {
            throw new IllegalArgumentException("Bad id input");
        }

        return _sensorRepository.findAllByDeviceId(id);
    }

}