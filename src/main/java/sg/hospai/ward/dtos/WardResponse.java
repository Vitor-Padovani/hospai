package sg.hospai.ward.dtos;

import sg.hospai.ward.Speciality;

public record WardResponse(
        Long id,
        Speciality speciality,
        int roomCount,
        int bedCount
) {
}
