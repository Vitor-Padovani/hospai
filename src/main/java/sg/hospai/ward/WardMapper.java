package sg.hospai.ward;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sg.hospai.ward.dtos.CreateWardRequest;
import sg.hospai.ward.dtos.WardResponse;
import sg.hospai.ward.dtos.WardSummary;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WardMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hospital", ignore = true)
    Ward toEntity(CreateWardRequest request);

    // Used by the plain ward listing, which doesn't compute room/bed counts.
    @Mapping(target = "roomCount", constant = "0")
    @Mapping(target = "bedCount", constant = "0")
    WardResponse toResponse(Ward ward);

    List<WardResponse> toResponse(List<Ward> wards);

    @Mapping(target = ".", source = "ward")
    WardResponse toResponse(WardSummary summary);

    List<WardResponse> toResponseFromSummaries(List<WardSummary> summaries);
}
