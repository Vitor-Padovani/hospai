package sg.hospai.hospital.dtos;

import sg.hospai.hospital.Hospital;
import sg.hospai.ward.dtos.WardSummary;

import java.util.List;

public record HospitalSummary(Hospital hospital, List<WardSummary> wards) {
}
