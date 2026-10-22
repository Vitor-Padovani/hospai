package sg.hospai.admission.dtos;

import java.time.LocalDateTime;

public record BedAdmissionHistoryResponse(
        Integer bedNumber,
        String patientName,
        LocalDateTime admissionAt,
        LocalDateTime dischargeAt
) {
}
