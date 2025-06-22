package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseTest {
    @Test
    void getErrorMessage() {
        ErrorResponse errorResponse = new ErrorResponse("message");

        assertEquals("message", errorResponse.error);
    }
}