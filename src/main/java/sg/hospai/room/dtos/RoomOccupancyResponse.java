package sg.hospai.room.dtos;

import sg.hospai.ward.Speciality;

public record RoomOccupancyResponse(
        Speciality speciality,
        long free,
        long occupied,
        long total
) {
}
