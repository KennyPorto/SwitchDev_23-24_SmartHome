package SmartHome.controller.exceptions;

import SmartHome.controller.rest_controllers.resources.ErrorResourceHandler;
import SmartHome.mapper.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

@ControllerAdvice
public class ControllerException {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e,
                                                                        HandlerMethod handlerMethod) {
        Class<?> controller = handlerMethod.getBeanType();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResourceHandler.manageError(e.getMessage(), controller));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e,
                                                                       HandlerMethod handlerMethod) {
        Class<?> controller = handlerMethod.getBeanType();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResourceHandler.manageError(e.getMessage(), controller));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e,
                                                                               HandlerMethod handlerMethod) {
        Class<?> controller = handlerMethod.getBeanType();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResourceHandler.manageError(e.getMessage(), controller));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e,
                                                                    HandlerMethod handlerMethod) {
        Class<?> controller = handlerMethod.getBeanType();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResourceHandler.manageError(e.getMessage(), controller)
        );
    }
}
