package sg.hospai.admission;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.hospai.admission.dtos.CreateAdmissionRequest;
import sg.hospai.bed.Bed;
import sg.hospai.bed.BedService;
import sg.hospai.patient.Patient;
import sg.hospai.patient.PatientService;
import sg.hospai.shared.BusinessRuleException;
import sg.hospai.shared.NotFoundException;

import java.time.LocalDateTime;

@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final PatientService patientService;
    private final BedService bedService;

    public AdmissionService(AdmissionRepository admissionRepository, PatientService patientService,
                             BedService bedService) {
        this.admissionRepository = admissionRepository;
        this.patientService = patientService;
        this.bedService = bedService;
    }

    @Transactional
    public Admission admit(CreateAdmissionRequest request) {
        Patient patient = patientService.getById(request.patientId());
        if (admissionRepository.existsByPatientIdAndStatus(patient.getId(), AdmissionStatus.ACTIVE)) {
            throw new BusinessRuleException("Patient " + patient.getId() + " already has an active admission");
        }
        Bed bed = bedService.occupy(request.bedId());

        LocalDateTime now = LocalDateTime.now();
        Admission admission = new Admission();
        admission.setPatient(patient);
        admission.setBed(bed);
        admission.setStatus(AdmissionStatus.ACTIVE);
        admission.setAdmissionAt(now);
        admission.setCreatedAt(now);
        return admissionRepository.save(admission);
    }

    public Admission getById(Long id) {
        return admissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Admission " + id + " not found"));
    }

    @Transactional
    public Admission discharge(Long id) {
        Admission admission = getById(id);
        if (admission.getStatus() != AdmissionStatus.ACTIVE) {
            throw new BusinessRuleException("Admission " + id + " is already discharged");
        }
        bedService.prepare(admission.getBed().getId());
        admission.setStatus(AdmissionStatus.INACTIVE);
        admission.setDischargeAt(LocalDateTime.now());
        return admissionRepository.save(admission);
    }
}
