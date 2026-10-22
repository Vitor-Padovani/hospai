package sg.hospai.admission.dtos;

import java.time.LocalDateTime;

public record ActiveAdmissionResponse(
        String patientName,
        LocalDateTime admissionAt,
        long daysAdmitted
) {
}
