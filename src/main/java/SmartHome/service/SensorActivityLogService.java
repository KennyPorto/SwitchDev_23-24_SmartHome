package SmartHome.service;

import SmartHome.domain.activitylog.ActivityLogFactory;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.repository.SensorActivityLogRepository;
import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.SensorFactory;
import SmartHome.domain.valueObjects.*;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorActivityLogService {
    private final SensorActivityLogRepository _activityLogRepository;
    private final ActivityLogFactory _activityLogFactory;
    private final SensorRepository _sensorRepository;
    private final SensorFactory _sensorFactory;

    public SensorActivityLogService(
            @Qualifier("sensorActivityLogRepoSpringDataImpl") SensorActivityLogRepository activityLogRepository,
            ActivityLogFactory activityLogFactory,
            @Qualifier("sensorRepoSpringDataImpl") SensorRepository sensorRepository,
            SensorFactory sensorFactory) {
        this._activityLogRepository = activityLogRepository;
        this._activityLogFactory = activityLogFactory;
        this._sensorRepository = sensorRepository;
        this._sensorFactory = sensorFactory;
    }

    public SensorActivityLog add(SensorId sensorId, TimeStamp timeStamp, ActivityLogId activityLogId,
                                 Measurement measurement) {
        if (!_sensorRepository.existsById(sensorId)) {
            throw new EntityNotFoundException("SensorId don't exist");
        }

        Optional<SensorActivityLog> sensorActivityLogToSave = _activityLogFactory
                .createSensorActivityLog(activityLogId, sensorId, timeStamp, measurement);

        if (sensorActivityLogToSave.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return _activityLogRepository.save(sensorActivityLogToSave.get());
    }

    public Iterable<SensorActivityLog> findAll() {
        return _activityLogRepository.findAll();
    }

    public SensorActivityLog findById(Long sensorActivityLogId) {
        Optional<ActivityLogId> activityLogId = _activityLogFactory.createActivityLogId(sensorActivityLogId);

        if (activityLogId.isEmpty()) {
            throw new IllegalArgumentException("Bad id input");
        }

        Optional<SensorActivityLog> sensorActivityLog = _activityLogRepository.findById(activityLogId.get());

        if (sensorActivityLog.isEmpty()) {
            throw new EntityNotFoundException("Not found by id.");
        }

        return sensorActivityLog.get();
    }

    public Iterable<SensorActivityLog> findAllBySensorId(long id)
    {
        Optional<SensorId> sensorId = _sensorFactory.createSensorId(id);

        if (sensorId.isEmpty()) {
            throw new IllegalArgumentException("Bad id input");
        }

        Optional<Sensor> sensor = _sensorRepository.findById(sensorId.get());

        if (sensor.isEmpty()) {
            throw new EntityNotFoundException("Not found by id.");
        }
        return _activityLogRepository.findAllBySensorId(id);
    }

    public Pair<Boolean, ActivityLogId> existsById(Long sensorActivityLogId) {
        Optional<ActivityLogId> activityLogId = _activityLogFactory.createActivityLogId(sensorActivityLogId);

        if (activityLogId.isEmpty()) {
            throw new IllegalArgumentException("Bad id input");
        }

        return Pair.of(_activityLogRepository.existsById(activityLogId.get()), activityLogId.get());
    }
}
