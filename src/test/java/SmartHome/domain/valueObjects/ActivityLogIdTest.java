package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityLogIdTest
{
    @Test
    void constructorWithValidId()
    {
        //Arrange
        long validId = 1;

        //Act
        ActivityLogId result = new ActivityLogId(validId);

        //Assert
        assertDoesNotThrow(() -> new RoomId(validId));
        assertEquals(validId, result.id);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1, -10})
    void constructorWithInvalidId(long invalidId)
    {
        //Arrange
        String errorMessage = "Id cannot be smaller than 1";
        //Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new ActivityLogId(invalidId));

        //Assert
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void sameObjectEqualsTrue()
    {
        //Arrange
        long id = 20;
        ActivityLogId newActivityLogId = new ActivityLogId(id);

        //Act
        boolean actual = newActivityLogId.equals(newActivityLogId);

        //Assert
        assertTrue(actual);
    }

    @Test
    void differentIdEqualsFalse()
    {
        //Arrange
        long id = 20;
        ActivityLogId newActivityLogId = new ActivityLogId(id);
        long differentObjectId = 21;
        ActivityLogId differentActivityLogId = new ActivityLogId(differentObjectId);

        //Act
        boolean result = newActivityLogId.equals(differentActivityLogId);

        //Assert
        assertFalse(result);
    }

    @Test
    void differentObjectEqualsFalse()
    {
        //Arrange
        long id = 20;
        ActivityLogId newActivityLogId = new ActivityLogId(id);
        ActuatorId actuatorId = new ActuatorId(1);

        //Act && Assert
        assertNotEquals(newActivityLogId, actuatorId);
    }

    @Test
    void differentObjectEqualsNull()
    {
        //Arrange
        long id = 20;
        ActivityLogId newActivityLogId = new ActivityLogId(id);

        //Assert
        assertNotEquals(null, newActivityLogId);
    }

    @Test
    void objectsWithSameIdEqualsTrue(){
        //Arrange
        long id = 1;
        ActivityLogId activityLogId1 = new ActivityLogId(id);
        ActivityLogId activityLogId2 = new ActivityLogId(id);

        // Act
        boolean result = activityLogId1.equals(activityLogId2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameObjectHashCode() {
        // Arrange
        long roomId = 3;
        ActivityLogId activityLogId = new ActivityLogId(roomId);

        // Act
        int result1 = activityLogId.hashCode();
        int result2 = activityLogId.hashCode();

        // Assert
        assertEquals(result1, result2);
    }

    @Test
    void differentObjectHashCode() {
        // Arrange
        long activityLogId1 = 3;
        long activityLogId2 = 6;

        ActivityLogId newActivityLogId1 = new ActivityLogId(activityLogId1);
        ActivityLogId newActivityLogId2 = new ActivityLogId(activityLogId2);

        // Act
        int result1 = newActivityLogId1.hashCode();
        int result2 = newActivityLogId2.hashCode();

        // Assert
        assertNotEquals(result1, result2);
    }
}
