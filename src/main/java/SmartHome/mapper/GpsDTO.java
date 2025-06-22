package SmartHome.mapper;

import org.springframework.hateoas.RepresentationModel;

public class GpsDTO extends RepresentationModel<GpsDTO>
{
    public final double latitude;
    public final double longitude;

    public GpsDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
