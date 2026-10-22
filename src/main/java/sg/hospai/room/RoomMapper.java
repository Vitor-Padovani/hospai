package sg.hospai.room;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sg.hospai.room.dtos.AvailableRoomResponse;
import sg.hospai.room.dtos.RoomOccupancyResponse;
import sg.hospai.room.dtos.RoomOccupancySummary;
import sg.hospai.room.dtos.RoomResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "ward.id", target = "ward")
    @Mapping(source = "room", target = "roomCode", qualifiedByName = "roomCode")
    RoomResponse toResponse(Room room);

    List<RoomResponse> toResponse(List<Room> rooms);

    @Mapping(source = "ward.speciality", target = "speciality")
    @Mapping(source = "room", target = "roomCode", qualifiedByName = "roomCode")
    AvailableRoomResponse toAvailableResponse(Room room);

    List<AvailableRoomResponse> toAvailableResponse(List<Room> rooms);

    @Mapping(source = "freeRooms", target = "free")
    @Mapping(source = "totalRooms", target = "total")
    @Mapping(target = "occupied", expression = "java(summary.totalRooms() - summary.freeRooms())")
    RoomOccupancyResponse toOccupancyResponse(RoomOccupancySummary summary);

    List<RoomOccupancyResponse> toOccupancyResponse(List<RoomOccupancySummary> summaries);

    @Named("roomCode")
    default String roomCode(Room room) {
        return room.getWard().getSpeciality().getPrefix() + "-" + room.getNumber();
    }
}
