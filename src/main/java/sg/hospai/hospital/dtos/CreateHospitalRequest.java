package sg.hospai.hospital.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import sg.hospai.ward.dtos.CreateWardRequest;

import java.util.List;

public record CreateHospitalRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Size(max = 14) String phone,
        @NotBlank @Pattern(regexp = "\\d{14}") String cnpj,
        @Valid List<CreateWardRequest> wards) {
}
