package sg.hospai.room.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateRoomsRequest(
        @NotNull @Min(1) @Max(50) Integer number_of_rooms
) {
}
