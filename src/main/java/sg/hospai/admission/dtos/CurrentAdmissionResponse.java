package sg.hospai.admission.dtos;

import sg.hospai.ward.Speciality;

import java.time.LocalDateTime;

public record CurrentAdmissionResponse(
        String hospitalName,
        Speciality speciality,
        String roomCode,
        String patientName,
        LocalDateTime admissionAt
) {
}
