package SmartHome.domain.valueObjects;

public class Precision
{
    public double precision;

    public Precision(double precision)
    {
        if (!validateArgument(precision)) throw new IllegalArgumentException();
        this.precision = precision;
    }

    private boolean validateArgument(double precision)
    {
        return precision > 0.0;
    }
}
