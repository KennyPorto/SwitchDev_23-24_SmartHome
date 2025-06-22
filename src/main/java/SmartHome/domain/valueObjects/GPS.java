package SmartHome.domain.valueObjects;

import SmartHome.ddd.ValueObject;

public class GPS extends ValueObject {

    public final double latitude;
    public final double longitude;


    public GPS(double latitude, double longitude) {
        if (! validateArguments(latitude, longitude))
            throw new IllegalArgumentException("Latitude must be between -90 and 90, and Longitude must be between -180 and 180.");
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private boolean validateArguments(double latitude, double longitude) {
        if (latitude < - 90 || latitude > 90) return false;
        if (longitude < - 180 || longitude > 180) return false;
        return !Double.isNaN(longitude) && !Double.isNaN(latitude);
    }
}
