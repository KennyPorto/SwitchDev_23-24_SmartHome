package SmartHome.domain.valueObjects;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Measurement
{

    public String measurement;

    public Measurement(String measurement)
    {
        if ( measurement == null || measurement.isEmpty() ) {
            throw new IllegalArgumentException("Measurement cannot be null or empty.");
        }
        this.measurement = measurement;
    }

    @Override
    public final boolean equals(Object o)
    {
        if ( this == o ) return true;
        if ( !(o instanceof Measurement that) ) return false;

        return Objects.equals(measurement, that.measurement);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(measurement);
    }
}
