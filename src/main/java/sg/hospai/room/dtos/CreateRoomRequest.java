package sg.hospai.room.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CreateRoomRequest(
        @Min(0) @Max(50) Integer bedCount) {
}
