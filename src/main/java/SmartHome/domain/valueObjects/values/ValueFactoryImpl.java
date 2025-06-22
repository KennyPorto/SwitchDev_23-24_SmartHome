package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class ValueFactoryImpl implements ValueFactory {

    @Override
    public RangeActuatorIntValue createRangeActuatorInt(int lowerLimit, int upperLimit) {
        return new RangeActuatorIntValue(lowerLimit, upperLimit);
    }

    @Override
    public RangeActuatorFractionalValue createRangeActuatorDecimal(double lowerLimit, double upperLimit) {
        return new RangeActuatorFractionalValue(lowerLimit, upperLimit);
    }

    @Override
    public Value createWValue(double value)
    {
        try {
            return new WValue(value);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Value createPercentage(int value)
    {
        try {
            return new PercentageValue(value);
        } catch ( Exception e ) {
            return null;
        }
    }

    @Override
    public Value createCelsiusTemperature(double value)
    {
        return new CelsiusValue(value);
    }

    @Override
    public Value createWhValue(double value)
    {
        return new WhValue(value);
    }

    @Override
    public KmhCardinalValue createKmhCardinalValue(double speed, double radian)
    {
        try {
            return new KmhCardinalValue(speed, radian);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Value createWm2Value(double value)
    {
        return new Wm2Value(value);
    }

    @Override
    public Value createSunriseValue(LocalDate localDate, double latitude, double longitude)
    {
        return new SunriseValue(localDate, latitude, longitude);
    }

    @Override
    public Value createSunsetValue(LocalDate localDate, double latitude, double longitude)
    {
        return new SunsetValue(localDate, latitude, longitude);
    }

    @Override
    public Value createAveragePowerConsumptionValue(LocalTime start, LocalTime end, Map<LocalTime, WValue> data)
    {
        try{
            return new AveragePowerConsumptionValue(start, end, data);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Value createElectricEnergyConsumptionValue(LocalTime start, LocalTime end, Map<LocalTime, WhValue> data)
    {
        try{
            return new ElectricEnergyConsumptionValue(start, end, data);
        } catch (Exception e) {
            return null;
        }
    }

}
