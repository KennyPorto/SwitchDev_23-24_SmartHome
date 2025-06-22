package SmartHome.domain.valueObjects;
import SmartHome.ddd.ValueObject;

public class SensorName extends ValueObject{
    public String name;

    public SensorName(String name){
        if(name != null && !name.isBlank() && !name.isEmpty()){
            this.name = name;
        }else{
            throw new IllegalArgumentException("Sensor name cannot be null or empty");
        }
    }
}
