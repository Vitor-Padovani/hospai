package sg.hospai.hospital.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateHospitalRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Size(max = 14) String phone,
        @NotBlank @Pattern(regexp = "\\d{14}") String cnpj) {
}
