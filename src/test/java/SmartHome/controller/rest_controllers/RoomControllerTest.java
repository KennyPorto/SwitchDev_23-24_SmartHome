package SmartHome.controller.rest_controllers;

import SmartHome.domain.repository.RoomRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.service.RoomService;
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

@WebMvcTest(RoomController.class)
@AutoConfigureMockMvc
public class RoomControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoomService roomService;

    @MockBean
    RoomRepository repository;

    @MockBean
    RoomFactory roomFactory;

    @MockBean
    Room room;

    @BeforeEach
    void setUp()
    {
        RoomName roomName = new RoomName("Dining Room");
        RoomId roomId = new RoomId(1L);
        HouseId houseId = new HouseId(1L);
        Floor floor = new Floor("ground floor");
        Dimensions dimensions = new Dimensions(1.6, 3.5, 2.0);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.INDOOR;

        when(roomFactory.createRoom(roomName, roomId, houseId, floor, dimensions, outdoorIndoor))
              .thenReturn(room);
        when(room.identity()).thenReturn(roomId);
        when(room.getRoomName()).thenReturn(roomName);
        when(room.getHouseId()).thenReturn(houseId);
        when(room.getHouseFloor()).thenReturn(floor);
        when(room.getDimensions()).thenReturn(dimensions);
        when(room.getOutdoorIndoor()).thenReturn(outdoorIndoor);
    }

    @Test
    void getRooms() throws Exception
    {
        // Arrange
        String jsonResponse = """
              [
                  {
                   	"roomName": "Dining Room",
                   	"roomId": 1,
                   	"houseId": 1,
                   	"houseFloor": "ground floor",
                   	"height": 1.6,
                   	"length": 2.0,
                   	"width": 3.5,
                   	"outdoorIndoor": "INDOOR",
                    "links": [
                          {
                              "rel": "self",
                              "href": "http://localhost/api/v1/rooms/1"
                          },
                          {
                              "rel":"devices-in-room",
                              "href":"http://localhost/api/v1/devices?room-id=1",
                              "type":"GET"
                          }
                      ]
                    }
                ]
              """;

        when(roomService.findAll()).thenReturn(List.of(room));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms"))
              .andExpect(status().isOk());

        // Assert
        resultActions.andExpect(content().json(jsonResponse));
    }

    @Test
    void getRoomsEmptyList() throws Exception
    {
        // Arrange
        String jsonResponse =
              """
                    []
                    """;

        when(roomService.findAll()).thenReturn(List.of());

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms"))
              .andExpect(status().isOk());

        // Assert
        resultActions.andExpect(content().json(jsonResponse));
    }

    @Test
    void getRoomByRoomId() throws Exception
    {
        //Arrange
        String jsonResponse = """
                  {
                   	"roomName": "Dining Room",
                   	"roomId": 1,
                   	"houseId": 1,
                   	"houseFloor": "ground floor",
                   	"height": 1.6,
                   	"length": 2.0,
                   	"width": 3.5,
                   	"outdoorIndoor": "INDOOR",
                    "_links": {
                          "self": {
                                "href": "http://localhost/api/v1/rooms"
                              },
                          "devices-in-room": {
                                "href": "http://localhost/api/v1/devices?room-id=1",
                                "type": "GET"
                		      }
                      }
                    }
              """;

        when(roomService.findById(1L)).thenReturn(room);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms/1"))
              .andExpect(status().isOk());

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
              .getContentAsString(), false);
    }

    @Test
    void getRoomByRoomIdInvalidId() throws Exception
    {
        //Arrange
        String jsonResponse = """
                  {
                      "error": "Bad id input",
                      "_links": {
                          "self": {
                              "href": "http://localhost/api/v1/rooms"
                          }
                      }
                  }
              """;

        when(roomService.findById(-12L)).thenThrow(new IllegalArgumentException("Bad id input"));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms/-12"))
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
              .getContentAsString(), false);
    }

    @Test
    void getRoomByRoomIdNotFound() throws Exception
    {
        //Arrange
        String jsonResponse = """
                  {
                      "error": "Not found by id",
                      "_links": {
                          "self": {
                              "href": "http://localhost/api/v1/rooms"
                          }
                      }
                  }
              """;

        when(roomService.findById(2L)).thenThrow(new IllegalArgumentException("Not found by id"));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms/2"))
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
              .getContentAsString(), false);
    }

    @Test
    void addRoom() throws Exception
    {
        // Arrange
        String jsonRequest = """
              {
                  "roomName": "Dining Room",
                 	"roomId": 1,
                 	"houseId": 1,
                 	"houseFloor": "ground floor",
                 	"height": 1.6,
                 	"length": 2.0,
                 	"width": 3.5,
                 	"outdoorIndoor": "INDOOR"
              }
              """;

        String jsonResponse = """
                  {
                   	"roomName": "Dining Room",
                   	"roomId": 1,
                   	"houseId": 1,
                   	"houseFloor": "ground floor",
                   	"height": 1.6,
                   	"length": 2.0,
                   	"width": 3.5,
                   	"outdoorIndoor": "INDOOR",
                    "_links": {
                          "self": {
                                 "href": "http://localhost/api/v1/rooms/1"
                              },
                          "devices-in-room": {
                                "href": "http://localhost/api/v1/devices?room-id=1",
                                "type": "GET"
                		      }
                      }
                    }
              """;

        when(roomService.add(any(), any(), any(), any(), any(), any())).thenReturn(room);

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/v1/rooms")
                    .contentType("application/json")
                    .content(jsonRequest))
              .andExpect(status().isCreated())
              .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
              .getContentAsString(), false);
    }

    @Test
    void getRoomsByHouseId() throws Exception
    {
        // Arrange
        String jsonResponse = """
              [
                  {
                   	"roomName": "Dining Room",
                   	"roomId": 1,
                   	"houseId": 1,
                   	"houseFloor": "ground floor",
                   	"height": 1.6,
                   	"length": 2.0,
                   	"width": 3.5,
                   	"outdoorIndoor": "INDOOR",
                    "links": [
                          {
                              "rel": "self",
                              "href": "http://localhost/api/v1/rooms/1"
                          },
                          {
                              "rel":"devices-in-room",
                              "href":"http://localhost/api/v1/devices?room-id=1",
                              "type":"GET"
                          }
                      ]
                    }
                ]
              """;

        when(roomService.findAllByHouseId(1L)).thenReturn(List.of(room));

        // Act
        ResultActions resultActions = mockMvc.perform(
              get("/api/v1/rooms").param("house-id", "1"))
              .andExpect(status().isOk());

        // Assert
        resultActions.andExpect(content().json(jsonResponse));
    }


    @Test
    void getRoomsByHouseIdNoRooms() throws Exception
    {
        // Arrange
        String jsonResponse = """
              []
              """;

        when(roomService.findAllByHouseId(1L)).thenReturn(List.of());

        // Act
        ResultActions resultActions = mockMvc.perform(
                    get("/api/v1/rooms").param("house-id", "1"))
              .andExpect(status().isOk());

        // Assert
        resultActions.andExpect(content().json(jsonResponse));
    }
}
