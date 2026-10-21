package sg.hospai.room;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.hospai.room.dtos.CreateRoomsRequest;
import sg.hospai.room.dtos.RoomResponse;

import java.util.List;

@RestController
@RequestMapping("/wards/{wardId}/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    public RoomController(RoomService roomService, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<RoomResponse> create(
            @PathVariable Long wardId, @Valid @RequestBody CreateRoomsRequest request
    ) {
        return roomMapper.toResponse(roomService.create(wardId, request));
    }
}
