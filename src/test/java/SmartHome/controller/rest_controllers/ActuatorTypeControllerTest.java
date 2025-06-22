package SmartHome.controller.rest_controllers;

import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.actuatortype.ActuatorTypeFactory;
import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.service.ActuatorTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActuatorTypeController.class)
@AutoConfigureMockMvc
class ActuatorTypeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ActuatorTypeService actuatorTypeService;

    @MockBean
    ActuatorTypeFactory actuatorTypeFactory;

    @MockBean
    ActuatorType actuatorType;

    @BeforeEach
    void setUp() {
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Act1");
        MeasurementUnit measurementUnit = MeasurementUnit.valueOf("Binary");

        when(actuatorTypeFactory.createActuatorType(actuatorTypeId, measurementUnit))
                .thenReturn(actuatorType);
        when(actuatorType.identity()).thenReturn(actuatorTypeId);
        when(actuatorType.getMeasurementUnit()).thenReturn(measurementUnit);
    }


    @Test
    void getActuatorTypes() throws Exception {
        // Arrange

        String jsonResponse = """
                 [
                    {
                        "actuatorTypeId": "Act1",
                        "measurementUnit": "Binary",
                        "links": [
                                    {
                                        "rel": "self",
                                        "href": "http://localhost/api/v1/actuator-types/Act1"
                                    }
                        ]
                    }
                ]
                """;

        when(actuatorTypeService.findAll()).thenReturn(List.of(actuatorType));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuator-types"))
                .andExpect(status().isOk());

        //Assert
        resultActions.andExpect(content().json(jsonResponse));
    }

    @Test
    void getActuatorTypes_emptyList() throws Exception {
        //Arrange
        String jsonResponse = """
                []
                """;

        when(actuatorTypeService.findAll()).thenReturn(List.of());

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuator-types"))
                .andExpect(status().isOk());

        //Assert
        resultActions.andExpect(content().json(jsonResponse));
    }

    @Test
    void getActuatorTypeById() throws Exception {
        //Arrange
        String jsonResponse = """
                       {
                           "actuatorTypeId": "Act1",
                           "measurementUnit": "Binary",
                           "_links": {
                                 "self": {
                                         "href": "http://localhost/api/v1/actuator-types"
                                     }
                             }
                       }
                """;

        when(actuatorTypeService.findById("Act1")).thenReturn(actuatorType);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuator-types/Act1"))
                .andExpect(status().isOk());

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
                .getContentAsString(), false);
    }

    @Test
    void getActuatorTypeWithInvalidId() throws Exception {
        //Arrange
        String jsonResponse = """
                    {
                        "error": "Bad id input",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuator-types"
                            }
                        }
                    }
                """;

        when(actuatorTypeService.findById("abc")).thenThrow(new IllegalArgumentException("Bad id input"));

        //Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuator-types/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        //Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
                .getContentAsString(), false);
    }

    @Test
    void addActuatorType() throws Exception {
        //Arrange
        String jsonRequest = """
                {
                    "actuatorTypeId": "Act1",
                    "measurementUnit": "Binary"
                }
                """;

        String jsonResponse = """
                {
                    "actuatorTypeId": "Act1",
                    "measurementUnit": "Binary",
                    "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuator-types/Act1"
                                }
                            }
                }
                """;

        when(actuatorTypeService.add(any(), any())).thenReturn(actuatorType);

        //Act
        ResultActions resultActions = mockMvc.perform(post("/api/v1/actuator-types")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));

        //Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);

    }

    @Test
    void addActuatorType_failActuatorTypeIdAlreadyExists() throws Exception {

        //Arrange
        when(actuatorTypeService.add(any(), any()))
                .thenThrow(new EntityNotFoundException("Actuator type id already exist"));

        String jsonRequest = """
                {
                    "actuatorTypeId": "Act1",
                    "measurementUnit": "Binary"
                }
                """;

        String jsonResponse = """
                {
                        "error": "Actuator type id already exist",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuator-types"
                            }
                        }
                    }
                """;

        //Act
        ResultActions resultActions = mockMvc.perform(post("/api/v1/actuator-types")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonResponse));

        //Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

}
