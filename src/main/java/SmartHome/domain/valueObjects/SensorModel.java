package SmartHome.domain.valueObjects;
import SmartHome.ddd.ValueObject;
import lombok.Getter;

@Getter
public class SensorModel extends ValueObject {
    public String model;

    public SensorModel(String model){
        if(model != null && !model.isBlank() && !model.isEmpty()){
            this.model = model;
        }else{
            throw new IllegalArgumentException("Sensor model cannot be null or empty");
        }
    }
}
