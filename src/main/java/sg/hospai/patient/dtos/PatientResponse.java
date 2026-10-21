package sg.hospai.patient.dtos;

public record PatientResponse(
        Long id,
        String name,
        String cpf,
        String phone
) {
}
