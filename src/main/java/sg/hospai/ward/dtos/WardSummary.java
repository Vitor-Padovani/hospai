package sg.hospai.ward.dtos;

import sg.hospai.ward.Ward;

public record WardSummary(Ward ward, int roomCount, int bedCount) {
}
