package sg.hospai.patient;

import org.springframework.stereotype.Service;
import sg.hospai.patient.dtos.CreatePatientRequest;
import sg.hospai.shared.BusinessRuleException;
import sg.hospai.shared.NotFoundException;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public Patient create(CreatePatientRequest request) {
        if (patientRepository.existsByCpf(request.cpf())) {
            throw new BusinessRuleException("A patient with CPF " + request.cpf() + " already exists");
        }
        Patient patient = patientMapper.toEntity(request);
        return patientRepository.save(patient);
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient " + id + " not found"));
    }
}
