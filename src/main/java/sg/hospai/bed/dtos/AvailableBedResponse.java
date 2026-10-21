package sg.hospai.bed.dtos;

import sg.hospai.ward.Speciality;

public record AvailableBedResponse(
        Long id,
        Integer number,
        Long roomId,
        String roomCode,
        Long wardId,
        Speciality speciality
) {
}
