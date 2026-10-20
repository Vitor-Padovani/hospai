package sg.hospai.hospital.responses;

public record HospitalResponse(
        Long id,
        String name,
        String phone,
        String cnpj
) {
}
