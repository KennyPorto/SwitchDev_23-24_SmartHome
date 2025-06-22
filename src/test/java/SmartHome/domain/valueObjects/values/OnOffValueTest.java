package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.OnOffValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OnOffValueTest
{
    @Test
    void validIsOn()
    {
        // Arrange
        boolean state = true;

        // Act
        OnOffValue status = new OnOffValue(state);

        // Assert
        assertEquals(state, status.isOn);
    }

    @Test
    void validIsOff()
    {
        // Arrange
        boolean state = false;

        // Act
        OnOffValue status = new OnOffValue(state);

        // Assert
        assertEquals(state, status.isOn);
    }
}
