package sg.hospai.bed;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sg.hospai.bed.dtos.AvailableBedResponse;
import sg.hospai.bed.dtos.BedDetailResponse;
import sg.hospai.bed.dtos.BedResponse;
import sg.hospai.room.Room;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BedMapper {

    @Mapping(source = "room.id", target = "roomId")
    BedResponse toResponse(Bed bed);

    List<BedResponse> toResponse(List<Bed> bed);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room", target = "roomCode", qualifiedByName = "roomCode")
    @Mapping(source = "room.ward.id", target = "wardId")
    @Mapping(source = "room.ward.speciality", target = "speciality")
    @Mapping(source = "room.ward.hospital.id", target = "hospitalId")
    AvailableBedResponse toAvailableResponse(Bed bed);

    List<AvailableBedResponse> toAvailableResponse(List<Bed> beds);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room", target = "roomCode", qualifiedByName = "roomCode")
    @Mapping(source = "room.ward.id", target = "wardId")
    @Mapping(source = "room.ward.speciality", target = "speciality")
    @Mapping(source = "room.ward.hospital.id", target = "hospitalId")
    BedDetailResponse toDetailResponse(Bed bed);

    List<BedDetailResponse> toDetailResponse(List<Bed> beds);

    @Named("roomCode")
    default String roomCode(Room room) {
        return room.getWard().getSpeciality().getPrefix() + "-" + room.getNumber();
    }
}
