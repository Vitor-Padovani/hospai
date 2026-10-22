package sg.hospai.room.dtos;

import sg.hospai.ward.Speciality;

public record RoomOccupancySummary(Speciality speciality, long totalRooms, long freeRooms) {
}
