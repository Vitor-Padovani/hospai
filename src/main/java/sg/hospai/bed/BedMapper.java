package sg.hospai.bed;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sg.hospai.bed.dtos.BedResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BedMapper {

    @Mapping(source = "room.id", target = "roomId")
    BedResponse toResponse(Bed bed);

    List<BedResponse> toResponse(List<Bed> bed);
}
