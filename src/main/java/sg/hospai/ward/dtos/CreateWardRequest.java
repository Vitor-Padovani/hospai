package sg.hospai.ward.dtos;

import jakarta.validation.constraints.NotNull;
import sg.hospai.ward.Speciality;

public record CreateWardRequest(
        @NotNull Speciality speciality) {
}
