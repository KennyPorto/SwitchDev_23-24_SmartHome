package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.actuators.Actuator;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("range_int")
public class ActuatorRangeIntDataModel extends ActuatorDataModel {
    @Column(name = "lower_limit_int")
    private int lowerLimit;
    @Column(name = "upper_limit_int")
    private int upperLimit;

    public ActuatorRangeIntDataModel() {

    }

    public ActuatorRangeIntDataModel(Actuator actuator, int lowerLimit, int upperLimit) {
        super(actuator);
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }
}
