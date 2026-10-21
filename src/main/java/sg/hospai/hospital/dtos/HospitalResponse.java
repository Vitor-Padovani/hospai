package sg.hospai.hospital.dtos;

public record HospitalResponse(
        Long id,
        String name,
        String phone,
        String cnpj
) {
}
