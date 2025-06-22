package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.house.House;
import jakarta.persistence.*;

@Entity
@Table(name = "house")
public class HouseDataModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long _houseId;
    @Column(name = "street")
    private String _street;
    @Column(name = "door_number")
    private String _doorNumber;
    @Column(name = "zip_code")
    private String _zipCode;
    @Column(name = "city")
    private String _city;
    @Column(name = "country")
    private String _country;
    @Column(name = "latitude")
    private double _latitude;
    @Column(name = "longitude")
    private double _longitude;
    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private int _version;

    public HouseDataModel()
    {
    }

    public HouseDataModel(House house)
    {
        this._street = house.getAddress().street;
        this._doorNumber = house.getAddress().doorNumber;
        this._zipCode = house.getAddress().zipCode;
        this._city = house.getAddress().city;
        this._country = house.getAddress().country;
        this._latitude = house.getGPS().latitude;
        this._longitude = house.getGPS().longitude;
    }

    public long getHouseId()
    {
        return _houseId;
    }

    public String getStreet()
    {
        return _street;
    }

    public String getDoorNumber()
    {
        return _doorNumber;
    }

    public String getZipCode()
    {
        return _zipCode;
    }

    public String getCity()
    {
        return _city;
    }

    public String getCountry()
    {
        return _country;
    }

    public double getLatitude()
    {
        return _latitude;
    }

    public double getLongitude()
    {
        return _longitude;
    }

    public int getVersion() { return _version; }

    public void configureLocation(String street, String doorNumber, String zipCode, String city, String country,
                                  double latitude, double longitude) {
        this._street = street;
        this._doorNumber = doorNumber;
        this._zipCode = zipCode;
        this._city = city;
        this._country = country;
        this._latitude = latitude;
        this._longitude = longitude;
    }
}
