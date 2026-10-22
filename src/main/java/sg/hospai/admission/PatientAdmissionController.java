package sg.hospai.admission;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.hospai.admission.dtos.CurrentAdmissionResponse;
import sg.hospai.admission.dtos.PatientAdmissionHistoryResponse;

@RestController
@RequestMapping("/patients/{id}")
public class PatientAdmissionController {

    private final AdmissionService admissionService;
    private final AdmissionMapper admissionMapper;

    public PatientAdmissionController(AdmissionService admissionService, AdmissionMapper admissionMapper) {
        this.admissionService = admissionService;
        this.admissionMapper = admissionMapper;
    }

    @GetMapping("/current-admission")
    CurrentAdmissionResponse getCurrentAdmission(@PathVariable Long id) {
        return admissionMapper.toCurrentResponse(admissionService.getCurrentAdmissionByPatient(id));
    }

    @GetMapping("/admissions")
    PagedModel<PatientAdmissionHistoryResponse> listAdmissions(@PathVariable Long id, Pageable pageable) {
        return new PagedModel<>(
                admissionService.listByPatient(id, pageable).map(admissionMapper::toPatientHistoryResponse));
    }
}
