package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.WValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WValueTest
{
    @Test
    void testValidWValue()
    {
        WValue wValue = new WValue(0.0);
        assertEquals(0.0, wValue.currentValue);
    }

    @Test
    void testInvalidWValue()
    {
        assertThrows(IllegalArgumentException.class, () -> new WValue(-1.0));
    }
}