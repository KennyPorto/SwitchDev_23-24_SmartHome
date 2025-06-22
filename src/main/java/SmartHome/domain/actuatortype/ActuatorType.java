package SmartHome.domain.actuatortype;

import SmartHome.ddd.AggregateRoot;
import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.domain.valueObjects.MeasurementUnit;

public class ActuatorType implements AggregateRoot<ActuatorTypeId> {

    private final ActuatorTypeId _actuatorTypeId;

    private final MeasurementUnit _measurementUnit;

    protected ActuatorType(ActuatorTypeId actuatorTypeId, MeasurementUnit measurementUnit)
    {
        this._actuatorTypeId = actuatorTypeId;
        this._measurementUnit = measurementUnit;
    }

    public MeasurementUnit getMeasurementUnit() { return _measurementUnit; }
    @Override
    public ActuatorTypeId identity() {
        return _actuatorTypeId;
    }

    @Override
    public boolean sameAs(Object object) {
        if ( this == object ) return true;
        if ( object == null || getClass() != object.getClass() ) return false;
        ActuatorType actuatorType = (ActuatorType) object;
        return _actuatorTypeId.equals(actuatorType._actuatorTypeId)
              && _measurementUnit.equals(actuatorType._measurementUnit);
    }
}
