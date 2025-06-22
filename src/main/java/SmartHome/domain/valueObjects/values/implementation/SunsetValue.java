package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;
import org.shredzone.commons.suncalc.SunTimes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class SunsetValue implements Value
{
    public final LocalTime _sunset;

    public SunsetValue(LocalDate localDate, double latitude, double longitude)
    {
        _sunset = calculateSunset(localDate, latitude, longitude);
    }

    public static LocalTime calculateSunset(LocalDate localDate, double latitude, double longitude)
    {
        SunTimes sunTimes = SunTimes.compute()
                .on(localDate)
                .at(latitude, longitude)
                .execute();

        ZonedDateTime sunsetDate = sunTimes.getSet();

        return sunsetDate.toLocalTime();
    }
}
