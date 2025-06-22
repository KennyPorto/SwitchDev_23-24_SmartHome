package SmartHome.controller;

import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.service.HouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientForwardController.class)
@AutoConfigureMockMvc
class ClientForwardControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    HouseService houseService;

    @MockBean
    House house;

    @MockBean
    HouseFactory houseFactory;

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
    public void testForward() throws Exception {
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

        mockMvc.perform(get("/api/v1/houses"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/"));
    }
}