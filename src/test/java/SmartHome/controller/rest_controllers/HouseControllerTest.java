package SmartHome.controller.rest_controllers;

import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.service.HouseService;
import SmartHome.service.PeakPowerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HouseController.class)
@AutoConfigureMockMvc
class HouseControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    HouseService houseService;

    @MockBean
    PeakPowerService peakPowerService;

    @MockBean
    HouseRepository houseRepository;

    @MockBean
    HouseFactory houseFactory;

    @MockBean
    House house;

    @BeforeEach
    void setup()
    {
        HouseId houseId = new HouseId(1L);
        String street = "Rua da Telha";
        String doorNUmber = "1";
        String zipCode = "4430-548";
        String city = "Porto";
        String country = "Portugal";
        Address address = new Address(street, doorNUmber, zipCode, city, country);
        GPS gps = new GPS(41.17, -8.60);

        when(houseFactory.createHouse(houseId, address, gps)).thenReturn(house);
        when(house.identity()).thenReturn(houseId);
        when(house.getAddress()).thenReturn(address);
        when(house.getGPS()).thenReturn(gps);

        when(houseService.findAll()).thenReturn(List.of(house));
    }

    @Test
    void getHouses() throws Exception
    {
        // Arrange
        String jsonResponse = """
                [
                    {
                     	"houseId": 1,
                     	"street": "Rua da Telha",
                     	"doorNumber": "1",
                     	"zipCode": "4430-548",
                     	"city": "Porto",
                    "country": "Portugal",
                     	"latitude": 41.17,
                     	"longitude": -8.60,
                      "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/houses/1"
                            }
                        ]
                      }
                  ]
                """;

        when(houseService.findAll()).thenReturn(List.of(house));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/houses"))
                .andExpect(status().isOk());

        // Assert
        resultActions.andExpect(content().json(jsonResponse));
    }

    @Test
    void getHousesEmptyList() throws Exception
    {
        // Arrange
        String jsonResponse =
                """
                        []
                        """;

        when(houseService.findAll()).thenReturn(List.of());

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/houses"))
                .andExpect(status().isOk());

        // Assert
        resultActions.andExpect(content().json(jsonResponse));
    }

    @Test
    void getHouseByHouseId() throws Exception
    {
        // Arrange
        String jsonResponse = """
                    {
                     	"houseId": 1,
                     	"street": "Rua da Telha",
                     	"doorNumber": "1",
                     	"zipCode": "4430-548",
                     	"city": "Porto",
                    "country": "Portugal",
                     	"latitude": 41.17,
                     	"longitude": -8.60,
                      "_links": {
                            "self": {
                                    "href": "http://localhost/api/v1/houses"
                            }
                      }
                  }
                """;

        when(houseService.findById(1L)).thenReturn(house);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/houses/1"))
                .andExpect(status().isOk());

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
                .getContentAsString(), false);
    }

    @Test
    void getHouseByHouseIdInvalid() throws Exception
    {
        //Arrange
        String jsonResponse = """
                    {
                        "error": "Bad id input",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/houses"
                            }
                        }
                    }
                """;

        when(houseService.findById(2L)).thenThrow(new IllegalArgumentException("Bad id input"));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/houses/2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
                .getContentAsString(), false);
    }

    @Test
    void addHouse() throws Exception
    {
        // Arrange
        String jsonRequest = """
                {
                 	"houseId": 1,
                 	"street": "Rua da Telha",
                 	"doorNumber": "1",
                 	"zipCode": "4430-548",
                 	"city": "Porto",
                    "country": "Portugal",
                 	"latitude": 41.17,
                 	"longitude": -8.60
                }
                """;

        String jsonResponse = """
                    {
                      "houseId":1,
                      "street":"Rua da Telha",
                      "doorNumber":"1",
                      "zipCode":"4430-548",
                      "city":"Porto",
                      "country":"Portugal",
                      "latitude":41.17,
                      "longitude":-8.6,
                      "_links":
                        {
                          "self":
                            {
                              "href":"http://localhost/api/v1/houses/1"
                            },
                          "peak-power":
                            {
                              "href":"http://localhost/api/v1/houses/1/peak-power?start-date={{start-date}}&end-date={{end-date}}&delta={{delta}}",
                              "type":"GET",
                              "templated":true
                            },
                          "functionalities":
                              {
                                "href":"http://localhost/api/v1/devices?house-id=1",
                                "type":"GET"
                              },
                          "rooms":
                            {
                                "href":"http://localhost/api/v1/rooms?house-id=1",
                                "type":"GET"
                            }
                        }
                    }
                """;

        when(houseService.add(any(), any(), any())).thenReturn(house);

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/v1/houses")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
                .getContentAsString(), false);
    }

    @Test
    void configureLocationHouse() throws Exception
    {
        // Arrange
        String jsonRequest = """
                {
                 	"houseId": 1,
                 	"street": "Rua do Campo",
                 	"doorNumber": "1",
                 	"zipCode": "4430-999",
                 	"city": "Porto",
                    "country": "Portugal",
                 	"latitude": 41.17,
                 	"longitude": -8.60
                }
                """;

        String jsonResponse = """
                    {
                      "houseId":1,
                      "street":"Rua do Campo",
                      "doorNumber":"1",
                      "zipCode":"4430-999",
                      "city":"Porto",
                      "country":"Portugal",
                      "latitude":41.17,
                      "longitude":-8.6,
                      "_links":
                        {
                          "self":
                            {
                              "href":"http://localhost/api/v1/houses/1"
                            },
                          "peak-power":
                            {
                              "href":"http://localhost/api/v1/houses/1/peak-power?start-date={{start-date}}&end-date={{end-date}}&delta={{delta}}",
                              "type":"GET",
                              "templated":true
                            },
                          "functionalities":
                            {
                              "href":"http://localhost/api/v1/devices?house-id=1",
                              "type":"GET"
                            },
                            "rooms":
                              {
                                "href":"http://localhost/api/v1/rooms?house-id=1",
                                "type":"GET"
                              }
                        }
                    }
                """;

        HouseId houseId = new HouseId(1L);
        String street = "Rua do Campo";
        String doorNUmber = "1";
        String zipCode = "4430-999";
        String city = "Porto";
        String country = "Portugal";
        Address address = new Address(street, doorNUmber, zipCode, city, country);
        GPS gps = new GPS(41.17, -8.60);

        House updatedHouse = mock(House.class);
        when(updatedHouse.identity()).thenReturn(houseId);
        when(updatedHouse.getAddress()).thenReturn(address);
        when(updatedHouse.getGPS()).thenReturn(gps);
        when(houseFactory.createAddress(any(), any(), any(), any(), any())).thenReturn(Optional.of(address));
        when(houseFactory.createGPS(anyDouble(), anyDouble())).thenReturn(Optional.of(gps));
        when(houseFactory.createHouse(any(), any(), any())).thenReturn(updatedHouse);
        when(houseRepository.configureLocation(anyLong(), any(), any())).thenReturn(updatedHouse);
        when(houseService.configureLocation(1L, address, gps)).thenReturn(updatedHouse);

        // Act
        ResultActions resultActions = mockMvc.perform(put("/api/v1/houses/1")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
                .getContentAsString(), false);
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionAddHouse() throws Exception
    {
        // Arrange
        String errorMessage = "Data integrity violation";
        String jsonRequest = """
                {
                 	"houseId": 1,
                 	"street": "Rua da Telha",
                 	"doorNumber": "1",
                 	"zipCode": "4430-548",
                 	"city": "Porto",
                    "country": "Portugal",
                 	"latitude": 41.17,
                 	"longitude": -8.60
                }
                """;

        String jsonResponse = """
                {
                    "error": "Data integrity violation",
                    "_links": {
                        "self": {
                            "href": "http://localhost/api/v1/houses"
                        }
                    }
                }
                """;

        when(houseService.add(any(), any(), any())).thenThrow(new DataIntegrityViolationException(errorMessage));

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/v1/houses")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionConfigureLocation() throws Exception
    {
        // Arrange
        String errorMessage = "Data integrity violation";
        String jsonRequest = """
                {
                 	"houseId": 1,
                 	"street": "Rua da Telha",
                 	"doorNumber": "1",
                 	"zipCode": "4430-548",
                 	"city": "Porto",
                    "country": "Portugal",
                 	"latitude": 41.17,
                 	"longitude": -8.60
                }
                """;

        String jsonResponse = """
                {
                    "error": "Data integrity violation",
                    "_links": {
                        "self": {
                            "href": "http://localhost/api/v1/houses"
                        }
                    }
                }
                """;

        when(houseService.configureLocation(anyLong(), any(), any())).thenThrow(new DataIntegrityViolationException(errorMessage));

        // Act
        ResultActions resultActions = mockMvc.perform(put("/api/v1/houses/1")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionFindHouseById() throws Exception
    {
        //Arrange
        String errorMessage = "House not found";
        String jsonResponse = """
                    {
                        "error": "House not found",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/houses"
                            }
                        }
                    }
                """;

        when(houseService.findById(2L)).thenThrow(new EntityNotFoundException(errorMessage));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/houses/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
                .getContentAsString(), false);
    }

    @Test
    void validPeakPower() throws Exception {
        //Arrange
        String jsonResponse = """
                 {
                         "peakPower": 100.0,
                         "_links":
                             {
                               "self":
                                 {
                                   "href":"http://localhost/api/v1/houses/1/peak-power"
                                 }
                             }
                     }
                """;

        when(peakPowerService.findPeakPower(any(), any(), any(), anyLong())).thenReturn(100.0);

        //Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/houses/1/peak-power?start-date=2021-01-01T00:00:00Z&end-date=2021-01-01T23:59:59Z&delta=900001"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        //Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void invalidDeltaForPeakPower() throws Exception {
        //Arrange
        String jsonResponse = """
                {
                    "error":"Minimum delta is 9000 milliseconds (15 minutes)",
                    "_links":
                        {"self":
                          {
                            "href":"http://localhost/api/v1/houses"
                          }
                        }
                }
                """;

        when(peakPowerService.findPeakPower(any(), any(), any(), anyLong())).thenThrow(
                new IllegalArgumentException("Minimum delta is 9000 milliseconds (15 minutes)")
        );

        //Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/houses/1/peak-power" +
                        "?start-date=2021-01-01T00:00:00Z" +
                        "&end-date=2021-01-01T23:59:59Z&delta=1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        //Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }
}
