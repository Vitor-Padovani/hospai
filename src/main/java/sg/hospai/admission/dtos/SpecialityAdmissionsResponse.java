package sg.hospai.admission.dtos;

import sg.hospai.ward.Speciality;

import java.util.List;

public record SpecialityAdmissionsResponse(
        Speciality speciality,
        List<ActiveAdmissionResponse> patients
) {
}
