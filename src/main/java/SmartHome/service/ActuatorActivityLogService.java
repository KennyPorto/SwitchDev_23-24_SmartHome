package SmartHome.service;

import SmartHome.domain.activitylog.ActivityLogFactory;
import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.repository.ActuatorActivityLogRepository;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.ActuatorId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.TimeStamp;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActuatorActivityLogService
{
    private final ActuatorActivityLogRepository _activityLogRepository;
    private final ActivityLogFactory _activityLogFactory;
    private final ActuatorRepository _actuatorRepository;
    private final ActuatorFactory _actuatorFactory;

    public ActuatorActivityLogService(
            @Qualifier("actuatorActivityLogRepoSpringDataImpl") ActuatorActivityLogRepository activityLogRepository,
            ActivityLogFactory activityLogFactory,
            @Qualifier("actuatorRepoSpringDataImpl") ActuatorRepository actuatorRepository,
            ActuatorFactory actuatorFactory
    )

    {
        this._activityLogRepository = activityLogRepository;
        this._activityLogFactory = activityLogFactory;
        this._actuatorRepository = actuatorRepository;
        this._actuatorFactory = actuatorFactory;
    }

    public ActuatorActivityLog add(ActuatorId actuatorId, TimeStamp timeStamp, ActivityLogId activityLogId,
                                   Measurement measurement)
    {
        if (!_actuatorRepository.existsById(actuatorId))
        {
            throw new EntityNotFoundException("ActuatorId doesn't exist");
        }

        Optional<ActuatorActivityLog> actuatorActivityLogToSave = _activityLogFactory
                .createActuatorActivityLog(activityLogId, actuatorId, timeStamp, measurement);

        if (actuatorActivityLogToSave.isEmpty())
        {
            throw new IllegalArgumentException();
        }

        return _activityLogRepository.save(actuatorActivityLogToSave.get());
    }

    public Iterable<ActuatorActivityLog> findAll()
    {
        return _activityLogRepository.findAll();
    }

    public ActuatorActivityLog findById(Long actuatorActivityLogId)
    {
        Optional<ActivityLogId> activityLogId = _activityLogFactory.createActivityLogId(actuatorActivityLogId);

        if (activityLogId.isEmpty())
        {
            throw new IllegalArgumentException("Bad id input");
        }

        Optional<ActuatorActivityLog> actuatorActivityLog = _activityLogRepository.findById(activityLogId.get());

        if (actuatorActivityLog.isEmpty())
        {
            throw new EntityNotFoundException("Not found by id.");
        }

        return actuatorActivityLog.get();
    }

    public Pair<Boolean, ActivityLogId> existsById(Long actuatorActivityLogId)
    {
        Optional<ActivityLogId> activityLogId = _activityLogFactory.createActivityLogId(actuatorActivityLogId);

        if (activityLogId.isEmpty())
        {
            throw new IllegalArgumentException("Bad id input");
        }

        return Pair.of(this._activityLogRepository.existsById(activityLogId.get()), activityLogId.get());
    }

    public Iterable<ActuatorActivityLog> findAllByActuatorId(long id)
    {
        Optional<ActuatorId> actuatorId = _actuatorFactory.createActuatorId(id);

        if (actuatorId.isEmpty()) {
            throw new IllegalArgumentException("Bad id input");
        }

        Optional<Actuator> actuator = _actuatorRepository.findById(actuatorId.get());

        if (actuator.isEmpty()) {
            throw new EntityNotFoundException("Not found by id.");
        }
        return _activityLogRepository.findAllByActuatorId(id);
    }
}
