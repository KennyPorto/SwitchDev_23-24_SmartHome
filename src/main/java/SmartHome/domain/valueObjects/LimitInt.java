package SmartHome.domain.valueObjects;

public class LimitInt {
    public final int lowerLimit;
    public final int upperLimit;

    public LimitInt(int lowerLimit, int upperLimit) {
        if (!validateArguments(lowerLimit, upperLimit)) throw new IllegalArgumentException();
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    private boolean validateArguments(int lowerLimit, int upperLimit) {
        return lowerLimit < upperLimit;
    }
}
