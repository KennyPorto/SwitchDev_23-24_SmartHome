package SmartHome.persistence.springdata;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.implementation.BlindRollerActuator;
import SmartHome.domain.actuators.implementation.RangeActuatorDecimal;
import SmartHome.domain.actuators.implementation.RangeActuatorInt;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.valueObjects.ActuatorId;
import SmartHome.mapper.ActuatorMapper;
import SmartHome.persistence.jpa.datamodel.ActuatorDataModel;
import SmartHome.persistence.jpa.datamodel.ActuatorRangeDecimalDataModel;
import SmartHome.persistence.jpa.datamodel.ActuatorRangeIntDataModel;
import SmartHome.persistence.jpa.datamodel.BlindRollerDataModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static SmartHome.utils.Constants.*;

@Repository
public class ActuatorRepoSpringDataImpl implements ActuatorRepository {
    private final ActuatorRepoSpringDataInterface springDataInterface;

    public ActuatorRepoSpringDataImpl(ActuatorRepoSpringDataInterface actuatorRepoSpringDataInterface) {
        this.springDataInterface = actuatorRepoSpringDataInterface;
    }

    @Override
    public Iterable<Actuator> findAllByDeviceId(Long deviceId) {
        Iterable<ActuatorDataModel> actuatorDataModels = springDataInterface.findAllByDeviceId(deviceId);
        return ActuatorMapper.dataModelListToDomain(actuatorDataModels);
    }

    @Override
    public Actuator save(Actuator actuator)
    {
        ActuatorDataModel actuatorDataModel = switch ( actuator.getActuatorModel().model ) {
            case RANGE_INT -> {
                RangeActuatorInt rangeInt = (RangeActuatorInt) actuator;
                yield new ActuatorRangeIntDataModel(actuator, rangeInt.getLowerLimit(), rangeInt.getUpperLimit());
            }
            case RANGE_DECIMAL -> {
                RangeActuatorDecimal rangeDecimal = (RangeActuatorDecimal) actuator;
                yield new ActuatorRangeDecimalDataModel(actuator, rangeDecimal.getLowerLimit(), rangeDecimal.getUpperLimit());
            }
            case BLIND_ROLLER -> {
                BlindRollerActuator blindRoller = (BlindRollerActuator) actuator;
                yield new BlindRollerDataModel(actuator, blindRoller.getCurrentPercentage().value);
            }
            default -> new ActuatorDataModel(actuator);
        };
        ActuatorDataModel result = springDataInterface.save(actuatorDataModel);
        return ActuatorMapper.dataModelToDomain(result);
    }

    @Override
    public Iterable<Actuator> findAll() {
        Iterable<ActuatorDataModel> actuators = springDataInterface.findAll();
        return ActuatorMapper.dataModelListToDomain(actuators);
    }

    @Override
    public Optional<Actuator> findById(ActuatorId id) {
        Optional<ActuatorDataModel> actuatorDataModel = springDataInterface.findById(id.id);

        return actuatorDataModel.map(ActuatorMapper::dataModelToDomain);

    }

    @Override
    public boolean existsById(ActuatorId id) {
        return springDataInterface.existsById(id.id);
    }
}
