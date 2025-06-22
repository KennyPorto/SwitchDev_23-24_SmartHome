package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;

public class KmhCardinalValue implements Value
{
    public double speed;
    public String radian;

    public KmhCardinalValue(double speed, double radian)
    {
        if (radian < 0 || radian >= 2 * Math.PI) {
            throw new IllegalArgumentException("Radian value must be between 0 and 2π");
        }
        if (speed < 0) {
            throw new IllegalArgumentException("Speed value must be greater than or equal to 0");
        }
        this.radian = getWindDirection(radian);
        this.speed = speed;
    }


    public String getWindDirection(double radian)
    {
        if (radian < Math.PI / 8 || radian >= 15 * Math.PI / 8) {
            return "East";
        }
        if (radian < 3 * Math.PI / 8) {
            return "North East";
        }
        if (radian < 5 * Math.PI / 8) {
            return "North";
        }
        if (radian < 7 * Math.PI / 8) {
            return "North West";
        }
        if (radian < 9 * Math.PI / 8) {
            return "West";
        }
        if (radian < 11 * Math.PI / 8) {
            return "South West";
        }
        if (radian < 13 * Math.PI / 8) {
            return "South";
        }
        return "South East";
    }

}