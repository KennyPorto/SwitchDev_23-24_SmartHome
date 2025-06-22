package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.actuators.Actuator;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("range_decimal")
public class ActuatorRangeDecimalDataModel extends ActuatorDataModel {
    @Column(name = "lower_limit_dec")
    private double lowerLimit;
    @Column(name = "upper_limit_dec")
    private double upperLimit;

    public ActuatorRangeDecimalDataModel() {

    }

    public ActuatorRangeDecimalDataModel(Actuator actuator, double lowerLimit, double upperLimit) {
        super(actuator);
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }
}
