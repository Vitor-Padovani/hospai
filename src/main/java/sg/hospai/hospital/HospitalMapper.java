package sg.hospai.hospital;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sg.hospai.hospital.dtos.CreateHospitalRequest;
import sg.hospai.hospital.dtos.HospitalResponse;
import sg.hospai.hospital.dtos.HospitalSummary;
import sg.hospai.hospital.dtos.HospitalSummaryResponse;
import sg.hospai.ward.WardMapper;

@Mapper(componentModel = "spring", uses = WardMapper.class)
public interface HospitalMapper {

    @Mapping(target = "id", ignore = true)
    Hospital toEntity(CreateHospitalRequest request);

    HospitalResponse toResponse(Hospital hospital);

    @Mapping(target = ".", source = "hospital")
    HospitalSummaryResponse toSummaryResponse(HospitalSummary summary);
}
