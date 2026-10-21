package sg.hospai.ward.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import sg.hospai.room.dtos.CreateRoomRequest;
import sg.hospai.ward.Speciality;

import java.util.List;

public record CreateWardRequest(
        @NotNull Speciality speciality,
        @Min(1) @Max(100) Integer roomCount,
        @Min(0) @Max(50) Integer bedsPerRoom,
        @Valid @Size(max = 100) List<CreateRoomRequest> rooms) {

    @AssertTrue(message = "roomCount and rooms are mutually exclusive")
    public boolean isRoomsConfigurationValid() {
        return roomCount == null || rooms == null;
    }

    @AssertTrue(message = "bedsPerRoom requires roomCount")
    public boolean isBedsPerRoomValid() {
        return bedsPerRoom == null || roomCount != null;
    }
}
