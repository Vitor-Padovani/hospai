package sg.hospai.room.dtos;

import sg.hospai.ward.Speciality;

public record AvailableRoomResponse(
        Speciality speciality,
        String roomCode
) {
}
