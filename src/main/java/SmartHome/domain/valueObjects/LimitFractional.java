package SmartHome.domain.valueObjects;

public class LimitFractional {
    public final double lowerLimit;
    public final double upperLimit;

    public LimitFractional(double lowerLimit, double upperLimit) {
        if (!validateArguments(lowerLimit, upperLimit)) throw new IllegalArgumentException();
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    private boolean validateArguments(double lowerLimit, double upperLimit) {
        return lowerLimit < upperLimit;
    }
}
