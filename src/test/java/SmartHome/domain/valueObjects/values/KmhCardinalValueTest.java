package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.KmhCardinalValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


class KmhCardinalValueTest {

    @Test
    void testConstructor()
    {
        KmhCardinalValue value = new KmhCardinalValue(10.0, Math.PI);
        assertEquals(10.0, value.speed);
        assertEquals("West", value.radian);
    }

    @Test
    void testConstructorThrowsExceptionForInvalidRadian()
    {
        assertThrows(IllegalArgumentException.class, () -> new KmhCardinalValue(10.0, -1.0));
        assertThrows(IllegalArgumentException.class, () -> new KmhCardinalValue(10.0, 2 * Math.PI));
    }

    @Test
    void testConstructorThrowsExceptionForInvalidSpeed()
    {
        assertThrows(IllegalArgumentException.class, () -> new KmhCardinalValue(-0.01, Math.PI));
    }

    @ParameterizedTest
    @CsvSource({
            "0, East",
            "0.785398163, North East",
            "1.570796327, North",
            "2.35619449, North West",
            "3.141592654, West",
            "3.926990817, South West",
            "4.71238898, South",
            "5.497787144, South East"
    })
    void validGetWindDirection(double radian, String expectedDirection)
    {
        KmhCardinalValue value = new KmhCardinalValue(10.0, radian);
        assertEquals(expectedDirection, value.radian);
    }

    @Test
    void invalidGetWindSpeed() {
        KmhCardinalValue kmhCardinalValue = new KmhCardinalValue(0.0, Math.PI);
        assertEquals(0.0, kmhCardinalValue.speed, 0.01);
    }

    @Test
    void invalidEastBoundary()
    {
        double invalidRadian = Math.PI / 8;
        KmhCardinalValue value = new KmhCardinalValue(10.0, invalidRadian);
        assertNotEquals("East", value.radian);
    }

    @Test
    void validEastUpperBoundary()
    {
        double radian = 15 * Math.PI / 8;
        KmhCardinalValue value = new KmhCardinalValue(10.0, radian);
        assertEquals("East", value.radian);
    }

    @Test
    void invalidNorthEastBoundary()
    {
        double invalidRadian = 3 * Math.PI / 8;
        KmhCardinalValue value = new KmhCardinalValue(10.0, invalidRadian);
        assertNotEquals("North East", value.radian);
    }

    @Test
    void invalidNorthBoundary()
    {
        double invalidRadian = 5 * Math.PI / 8;
        KmhCardinalValue value = new KmhCardinalValue(10.0, invalidRadian);
        assertNotEquals("North", value.radian);
    }

    @Test
    void invalidNorthWestBoundary()
    {
        double invalidRadian = 7 * Math.PI / 8;
        KmhCardinalValue value = new KmhCardinalValue(10.0, invalidRadian);
        assertNotEquals("North West", value.radian);
    }

    @Test
    void invalidWestBoundary()
    {
        double invalidRadian = 9 * Math.PI / 8;
        KmhCardinalValue value = new KmhCardinalValue(10.0, invalidRadian);
        assertNotEquals("West", value.radian);
    }

    @Test
    void invalidSouthWestBoundary()
    {
        double invalidRadian = 11 * Math.PI / 8;
        KmhCardinalValue value = new KmhCardinalValue(10.0, invalidRadian);
        assertNotEquals("South West", value.radian);
    }

    @Test
    void invalidSouthBoundary()
    {
        double invalidRadian = 13 * Math.PI / 8;
        KmhCardinalValue value = new KmhCardinalValue(10.0, invalidRadian);
        assertNotEquals("South", value.radian);
    }

}
