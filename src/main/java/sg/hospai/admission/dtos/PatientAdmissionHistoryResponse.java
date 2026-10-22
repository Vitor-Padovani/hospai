package sg.hospai.admission.dtos;

import sg.hospai.ward.Speciality;

import java.time.LocalDateTime;

public record PatientAdmissionHistoryResponse(
        String patientName,
        Speciality speciality,
        LocalDateTime admissionAt,
        LocalDateTime dischargeAt
) {
}
