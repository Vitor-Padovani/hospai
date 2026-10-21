package sg.hospai.hospital.dtos;

import sg.hospai.ward.dtos.WardResponse;

import java.util.List;

public record HospitalSummaryResponse(
        Long id,
        String name,
        String phone,
        String cnpj,
        List<WardResponse> wards
) {
}
