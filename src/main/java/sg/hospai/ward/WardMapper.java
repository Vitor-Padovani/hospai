package sg.hospai.ward;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sg.hospai.ward.dtos.CreateWardRequest;
import sg.hospai.ward.dtos.WardResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WardMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hospital", ignore = true)
    Ward toEntity(CreateWardRequest request);

    // Rooms and beds arrive in slices 2 and 3; until then the counts are always zero.
    @Mapping(target = "roomCount", constant = "0")
    @Mapping(target = "bedCount", constant = "0")
    WardResponse toResponse(Ward ward);

    List<WardResponse> toResponse(List<Ward> wards);
}
