package sg.hospai.patient.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreatePatientRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank String cpf,
        @Size(max = 14) String phone) {
}
