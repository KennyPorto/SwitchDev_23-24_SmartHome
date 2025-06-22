package SmartHome.domain.sensortype;

import SmartHome.ddd.AggregateRoot;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;

public class SensorType implements AggregateRoot<SensorTypeId> {
    private final SensorTypeId _sensorTypeId;
    private final MeasurementUnit _measurementUnit;

    protected SensorType(SensorTypeId sensorTypeId, MeasurementUnit _measurementUnit)
    {
        this._sensorTypeId = sensorTypeId;
        this._measurementUnit = _measurementUnit;
    }

    public MeasurementUnit getMeasurementUnit() {
        return _measurementUnit;
    }

    @Override
    public SensorTypeId identity() {
        return _sensorTypeId;
    }

    @Override
    public boolean sameAs(Object object) {
        if ( object instanceof SensorType sensorType ) {

            return this._sensorTypeId.equals(sensorType._sensorTypeId)
                    && this._measurementUnit.equals(sensorType._measurementUnit);
        }
        return false;
    }
}
