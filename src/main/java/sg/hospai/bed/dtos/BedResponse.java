package sg.hospai.bed.dtos;

public record BedResponse(
        Long id,
        Long roomId,
        Integer number,
        String status
) {
}
