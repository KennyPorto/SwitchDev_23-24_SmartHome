package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;
import org.shredzone.commons.suncalc.SunTimes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class SunriseValue implements Value
{
    public final LocalTime _sunrise;

    public SunriseValue(LocalDate localDate, double latitude, double longitude)
    {
        _sunrise = calculateSunrise(localDate, latitude, longitude);
    }

    public static LocalTime calculateSunrise(LocalDate localDate, double latitude, double longitude)
    {
        SunTimes sunTimes = SunTimes.compute()
                .on(localDate)
                .at(latitude, longitude)
                .execute();

        ZonedDateTime sunriseDate = sunTimes.getRise();

        return sunriseDate.toLocalTime();
    }

}
