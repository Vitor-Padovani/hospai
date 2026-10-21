package sg.hospai.room;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sg.hospai.room.dtos.RoomResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "ward.id", target = "ward")
    RoomResponse toResponse(Room room);

    List<RoomResponse> toResponse(List<Room> rooms);
}
