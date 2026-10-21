package sg.hospai.room.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateRoomsRequest(
        @NotNull Integer number_of_rooms
) {
}
