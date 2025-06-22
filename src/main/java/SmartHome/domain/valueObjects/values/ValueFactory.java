package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.RangeActuatorFractionalValue;
import SmartHome.domain.valueObjects.values.implementation.RangeActuatorIntValue;
import SmartHome.domain.valueObjects.values.implementation.WValue;
import SmartHome.domain.valueObjects.values.implementation.WhValue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public interface ValueFactory
{
    RangeActuatorIntValue createRangeActuatorInt(int lowerLimit, int upperLimit);

    RangeActuatorFractionalValue createRangeActuatorDecimal(double lowerLimit, double upperLimit);

    Value createWValue(double value);

    Value createPercentage(int value);

    Value createCelsiusTemperature(double value);

    Value createWhValue(double value);

    Value createKmhCardinalValue(double speed, double radian);

    Value createWm2Value(double value);

    Value createSunriseValue(LocalDate localDate, double latitude, double longitude);

    Value createSunsetValue(LocalDate localDate, double latitude, double longitude);

    Value createAveragePowerConsumptionValue(LocalTime start, LocalTime end, Map<LocalTime, WValue> data);

    Value createElectricEnergyConsumptionValue(LocalTime start, LocalTime end, Map<LocalTime, WhValue> data);
}