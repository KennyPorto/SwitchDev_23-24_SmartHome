package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.actuators.Actuator;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("blind_roller")
public class BlindRollerDataModel extends ActuatorDataModel
{
    private int currentValue;

    public BlindRollerDataModel(Actuator actuator, int currentValue)
    {
        super(actuator);
        this.currentValue = currentValue;
    }
}
