package sg.hospai.admission.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateAdmissionRequest(
        @NotNull Long patientId,
        @NotNull Long bedId) {
}
