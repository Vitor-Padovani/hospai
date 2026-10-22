package sg.hospai.bed.dtos;

import sg.hospai.ward.Speciality;

public record AvailableBedResponse(
        Long id,
        Long hospitalId,
        Long wardId,
        Integer number,
        Long roomId,
        String roomCode,
        Speciality speciality
) {
}
