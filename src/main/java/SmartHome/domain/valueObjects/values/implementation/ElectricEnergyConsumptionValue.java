package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;

import java.time.LocalTime;
import java.util.Map;

public class ElectricEnergyConsumptionValue implements Value
{
    public final Map<LocalTime, WhValue> data;
    public final LocalTime start;
    public final LocalTime end;
    public final double consumption;


    public ElectricEnergyConsumptionValue(LocalTime start, LocalTime end, Map<LocalTime, WhValue> data)
    {
        if (start == null || end == null || start.isAfter(end))
            throw new IllegalArgumentException("Invalid time period");

        this.start = start;
        this.end = end;
        this.data = data;
        this.consumption = getEnergyConsumptionOverAPeriod(start, end);


    }

    private double getReadingInAGivenTime(LocalTime time)
    {
        double readingValue = 0;
        for (Map.Entry<LocalTime, WhValue> entry : data.entrySet())
            if (entry.getKey().equals(time)) {
                WhValue value = entry.getValue();
                readingValue = value.currentValue;
            }

        return readingValue;
    }

    private double getEnergyConsumptionOverAPeriod(LocalTime startTime, LocalTime endTime)
    {
        double startReadingValue = getReadingInAGivenTime(startTime);
        double endReadingValue = getReadingInAGivenTime(endTime);

        double totalConsumption = endReadingValue - startReadingValue;

        if (!isValidEnergyConsumption(totalConsumption)) {
            throw new IllegalArgumentException("Invalid energy consumption value.");
        }

        return totalConsumption;
    }


    private boolean isValidEnergyConsumption(double value)
    {
        return value >= 0;
    }
}
