package SmartHome.domain.valueObjects;

import java.time.ZonedDateTime;
import java.util.Objects;

public class TimeStamp
{
    public final ZonedDateTime value;

    public TimeStamp(ZonedDateTime value)
    {
        if (value == null) throw new IllegalArgumentException("TimeStamp cannot be null.");
        this.value = value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeStamp timeStamp = (TimeStamp) o;
        return value == timeStamp.value;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(value);
    }
}
