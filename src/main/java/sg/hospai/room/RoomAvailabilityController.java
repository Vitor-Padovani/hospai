package sg.hospai.room;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.hospai.room.dtos.AvailableRoomResponse;
import sg.hospai.room.dtos.RoomOccupancyResponse;

import java.util.List;

@RestController
public class RoomAvailabilityController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    public RoomAvailabilityController(RoomService roomService, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    @GetMapping("/rooms/occupancy")
    List<RoomOccupancyResponse> occupancy() {
        return roomMapper.toOccupancyResponse(roomService.summarizeOccupancy());
    }

    @GetMapping("/rooms/available")
    List<AvailableRoomResponse> listAvailable() {
        return roomMapper.toAvailableResponse(roomService.listAvailable());
    }
}
