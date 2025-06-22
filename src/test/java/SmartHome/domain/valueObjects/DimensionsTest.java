package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DimensionsTest {
    Dimensions dimensionsForVolume;

    @BeforeEach
    public void setUp() {
        dimensionsForVolume = new Dimensions(2.8, 2, 3.6);
    }

    @Test
    void validHeightConstructor() {
        // Arrange
        double expectedResult = 2.8;

        // Act
        double actualResult = dimensionsForVolume.height;

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void validWidthConstructor() {
        // Arrange
        double expectedResult = 2;

        // Act
        double actualResult = dimensionsForVolume.width;

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void validLengthConstructor() {
        // Arrange
        double expectedResult = 3.6;

        // Act
        double actualResult = dimensionsForVolume.length;

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void invalidHeightThrowsException() {
        // Arrange
        double invalidHeight = 0;
        String expectedResult = "Invalid arguments passed to constructor.";

        // Act
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> new Dimensions(invalidHeight, 2, 3.6));
        String actualResult = exception.getMessage();

        // Assert
        assertEquals(expectedResult, actualResult);
    }


    @Test
    void invalidWidthThrowsException() {
        // Arrange
        double invalidWidth = 0;
        String expectedResult = "Invalid arguments passed to constructor.";

        // Act
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> new Dimensions(2.8, invalidWidth, 3.6));
        String actualResult = exception.getMessage();

        // Assert
        assertEquals(expectedResult, actualResult);
    }


    @Test
    void invalidLengthThrowsException() {
        // Arrange
        double invalidLength = 0;
        String expectedResult = "Invalid arguments passed to constructor.";

        // Act
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> new Dimensions(2.8, 2, invalidLength));
        String actualResult = exception.getMessage();

        // Assert
        assertEquals(expectedResult, actualResult);
    }


    @Test
    void validBoundaryHeight(){
        // Arrange
        double validBoundaryHeight = 1;

        // Act
        assertDoesNotThrow(() -> new Dimensions(validBoundaryHeight, 2, 3.6));
    }


    @Test
    void validBoundaryWidth(){
        // Arrange
        double validBoundaryWidth = 1;

        // Act + Assert
        assertDoesNotThrow(() -> new Dimensions(2.8, validBoundaryWidth, 3.6));
    }


    @Test
    void validBoundaryLength()
    {
        // Arrange
        double validBoundaryLength = 1;

        // Act + Assert
        assertDoesNotThrow(() -> new Dimensions(2.8, 2, validBoundaryLength));
    }

    @Test
    void testEqualsSameObjectTrue() {
        // Arrange
        double height = 2.8;
        double width = 2;
        double length = 3.6;
        Dimensions dimensions = new Dimensions(height, width, length);

        // Act
        boolean result = dimensions.equals(dimensions);

        // Assert
        assertTrue(result);
    }

    @Test
    void differentObjectEqualsFalse() {
        // Arrange
        double height = 2.8;
        double width = 2;
        double length = 3.6;
        Dimensions dimensions = new Dimensions(height, width, length);
        int differentObject = 1;

        // Act and Assert
        assertNotEquals(dimensions, differentObject);
    }

    @Test
    void objectWithTheSameDimensionsEqualsTrue() {
        // Arrange
        double height = 2.8;
        double width = 2;
        double length = 3.6;
        Dimensions dimensions = new Dimensions(height, width, length);
        Dimensions dimensions2 = new Dimensions(height, width, length);

        // Act
        boolean result = dimensions.equals(dimensions2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameObjectHashCode() {
        // Arrange
        double height = 2.8;
        double width = 2;
        double length = 3.6;
        Dimensions dimensions = new Dimensions(height, width, length);
        Dimensions dimensions2 = new Dimensions(height, width, length);

        // Act
        int result = dimensions.hashCode();
        int result2 = dimensions2.hashCode();

        // Assert
        assertEquals(result, result2);
    }

}
