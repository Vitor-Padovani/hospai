package sg.hospai.hospital;

import org.mapstruct.Mapper;
import sg.hospai.hospital.requests.CreateHospitalRequest;
import sg.hospai.hospital.responses.HospitalResponse;

@Mapper(componentModel = "spring")
public interface HospitalMapper {

    Hospital toEntity(CreateHospitalRequest request);

    HospitalResponse toResponse(Hospital hospital);
}
