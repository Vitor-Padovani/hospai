package sg.hospai.bed.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateBedsRequest(
        @NotNull @Min(1) @Max(50) Integer count
) {
}
