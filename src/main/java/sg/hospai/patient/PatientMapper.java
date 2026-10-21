package sg.hospai.patient;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sg.hospai.patient.dtos.CreatePatientRequest;
import sg.hospai.patient.dtos.PatientResponse;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    Patient toEntity(CreatePatientRequest request);

    PatientResponse toResponse(Patient patient);
}
