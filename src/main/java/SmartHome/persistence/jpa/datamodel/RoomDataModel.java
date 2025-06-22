package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.room.Room;
import SmartHome.domain.valueObjects.OutdoorIndoor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room")
@Getter
@NoArgsConstructor
public class RoomDataModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roomId;
    @Column(name = "room_name")
    private String roomName;
    @Column(name = "house_id")
    private long houseId;
    @Column(name = "house_floor")
    private String floor;
    @Column(name = "height")
    private double height;
    @Column(name = "width")
    private double width;
    @Column(name = "length")
    private double length;
    @Enumerated(EnumType.STRING)
    private OutdoorIndoor outdoorIndoor;
    @Version
    @Column(columnDefinition = "integer DEFAULT 0", nullable = false)
    private int version;

    public RoomDataModel(Room room)
    {
        this.roomName = room.getRoomName().name;
        this.houseId = room.getHouseId().id;
        this.floor = room.getHouseFloor().floor;
        this.height = room.getDimensions().height;
        this.width = room.getDimensions().width;
        this.length = room.getDimensions().length;
        this.outdoorIndoor = room.getOutdoorIndoor();
    }
}
