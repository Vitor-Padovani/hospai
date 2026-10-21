package sg.hospai.admission;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sg.hospai.admission.dtos.AdmissionResponse;

@Mapper(componentModel = "spring")
public interface AdmissionMapper {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "bed.id", target = "bedId")
    AdmissionResponse toResponse(Admission admission);
}
