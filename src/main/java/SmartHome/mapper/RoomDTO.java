package SmartHome.mapper;

import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
public class RoomDTO extends RepresentationModel<RoomDTO>
{
   public String roomName;
   public long roomId = 1;
   public long houseId;
   public String houseFloor;
   public double height;
   public double length;
   public double width;
   public String outdoorIndoor;

   public RoomDTO(String roomName, long roomId, long houseId, String houseFloor,
                  double height, double width, double length, String outdoorIndoor) {
      this.roomName = roomName;
      this.roomId = roomId;
      this.houseId = houseId;
      this.houseFloor = houseFloor;
      this.height = height;
      this.length = length;
      this.width = width;
      this.outdoorIndoor = outdoorIndoor.toUpperCase();
   }

   public RoomDTO(String roomName, long houseId, String houseFloor,
                  double height, double width, double length, String outdoorIndoor) {
      this.roomName = roomName;
      this.houseId = houseId;
      this.houseFloor = houseFloor;
      this.height = height;
      this.length = length;
      this.width = width;
      this.outdoorIndoor = outdoorIndoor.toUpperCase();
   }
}
