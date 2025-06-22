package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrecisionTest
{
    @Test
    void createPrecision_success()
    {
        double precision = 0.01;
        assertEquals(precision, new Precision(precision).precision, 0.001);
    }

    @Test
    void createPrecision_not_successful()
    {
        double precision = -0.01;
        assertThrows(IllegalArgumentException.class, () -> new Precision(precision));
    }

    @Test
    void precisionEqualsZero()
    {
        double precision = 0.0;
        assertThrows(IllegalArgumentException.class, () -> new Precision(precision));
    }

}