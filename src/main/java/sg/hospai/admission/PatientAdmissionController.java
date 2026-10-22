package sg.hospai.admission;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.hospai.admission.dtos.AdmissionResponse;

@RestController
@RequestMapping("/patients/{id}/current-admission")
public class PatientAdmissionController {

    private final AdmissionService admissionService;
    private final AdmissionMapper admissionMapper;

    public PatientAdmissionController(AdmissionService admissionService, AdmissionMapper admissionMapper) {
        this.admissionService = admissionService;
        this.admissionMapper = admissionMapper;
    }

    @GetMapping
    AdmissionResponse getCurrentAdmission(@PathVariable Long id) {
        return admissionMapper.toResponse(admissionService.getCurrentAdmissionByPatient(id));
    }
}
