package sg.hospai.admission;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.hospai.admission.dtos.AdmissionResponse;
import sg.hospai.admission.dtos.CreateAdmissionRequest;

@RestController
@RequestMapping("/admissions")
public class AdmissionController {

    private final AdmissionService admissionService;
    private final AdmissionMapper admissionMapper;

    public AdmissionController(AdmissionService admissionService, AdmissionMapper admissionMapper) {
        this.admissionService = admissionService;
        this.admissionMapper = admissionMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AdmissionResponse admit(@Valid @RequestBody CreateAdmissionRequest request) {
        return admissionMapper.toResponse(admissionService.admit(request));
    }

    @GetMapping("/{id}")
    AdmissionResponse getById(@PathVariable Long id) {
        return admissionMapper.toResponse(admissionService.getById(id));
    }

    @PostMapping("/{id}/discharge")
    AdmissionResponse discharge(@PathVariable Long id) {
        return admissionMapper.toResponse(admissionService.discharge(id));
    }
}
