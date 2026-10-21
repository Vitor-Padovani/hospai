package sg.hospai.admission.dtos;

import sg.hospai.admission.AdmissionStatus;

import java.time.LocalDateTime;

public record AdmissionResponse(
        Long id,
        Long patientId,
        Long bedId,
        AdmissionStatus status,
        LocalDateTime admissionAt,
        LocalDateTime dischargeAt,
        LocalDateTime createdAt
) {
}
