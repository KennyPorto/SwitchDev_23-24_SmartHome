package SmartHome.controller.rest_controllers.resources;

import SmartHome.mapper.ErrorResponse;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class ErrorResourceHandler {
    public static ErrorResponse manageError(String eMessage, Class<?> className) {
        ErrorResponse response = new ErrorResponse(eMessage);
        Link link = WebMvcLinkBuilder.linkTo(className).withSelfRel();
        response.add(link);
        return response;
    }
}
