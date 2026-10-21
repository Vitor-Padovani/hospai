package sg.hospai.room;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sg.hospai.room.dtos.RoomResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "ward.id", target = "ward")
    @Mapping(source = "room", target = "roomCode", qualifiedByName = "roomCode")
    RoomResponse toResponse(Room room);

    List<RoomResponse> toResponse(List<Room> rooms);

    @Named("roomCode")
    default String roomCode(Room room) {
        return room.getWard().getSpeciality().getPrefix() + "-" + room.getNumber();
    }
}
