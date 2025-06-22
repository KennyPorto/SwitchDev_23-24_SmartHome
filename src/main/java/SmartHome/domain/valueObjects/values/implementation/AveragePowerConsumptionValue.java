package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AveragePowerConsumptionValue implements Value
{
    public final Map<LocalTime, WValue> data;
    public final LocalTime start;
    public final LocalTime end;
    public final double averageConsumption;

    public AveragePowerConsumptionValue(LocalTime start, LocalTime end, Map<LocalTime, WValue> data)
    {
        if (start == null || end == null || start.isAfter(end))
            throw new IllegalArgumentException("Invalid time period");

        this.start = start;
        this.end = end;
        this.data = data;
        this.averageConsumption = getReading(start, end);
    }

    public double getReading(LocalTime start, LocalTime end)
    {
        List<WValue> readingsInAGivenPeriod = getReadingsInAPeriodOfTime(start, end);
        if (readingsInAGivenPeriod.isEmpty()) throw new IllegalArgumentException ("No readings to show");
        return getAveragePowerConsumption(readingsInAGivenPeriod);
    }

    private double getAveragePowerConsumption(List<WValue> readings)
    {
        double totalPower = 0;
        for (WValue reading : readings) {
            double numericValue = reading.currentValue;
            totalPower += numericValue;
        }

        return totalPower / readings.size();
    }

    private List<WValue> getReadingsInAPeriodOfTime(LocalTime start, LocalTime end)
    {
        List<WValue> readingsInAGivenPeriod = new ArrayList<>();
        for (Map.Entry<LocalTime, WValue> set : data.entrySet())
            if (set.getKey().isAfter(start) && set.getKey().isBefore(end))
                addReadingToList(set.getValue(), readingsInAGivenPeriod);

        return readingsInAGivenPeriod;
    }

    private void addReadingToList(WValue instantValue, List<WValue> readings)
    {
        if (instantValue != null)
            readings.add(instantValue);
    }
    }
