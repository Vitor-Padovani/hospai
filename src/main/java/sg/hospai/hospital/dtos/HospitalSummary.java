package sg.hospai.hospital.dtos;

import sg.hospai.hospital.Hospital;
import sg.hospai.ward.Ward;

import java.util.List;

public record HospitalSummary(Hospital hospital, List<Ward> wards) {
}
