package sg.hospai.hospital.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateHospitalRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Size(max = 14) String phone,
        @NotBlank @Size(max = 14) String cnpj) {
}
