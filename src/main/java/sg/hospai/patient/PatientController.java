package sg.hospai.patient;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.hospai.patient.dtos.CreatePatientRequest;
import sg.hospai.patient.dtos.PatientResponse;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    public PatientController(PatientService patientService, PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PatientResponse create(@Valid @RequestBody CreatePatientRequest request) {
        return patientMapper.toResponse(patientService.create(request));
    }

    @GetMapping("/{id}")
    PatientResponse getById(@PathVariable Long id) {
        return patientMapper.toResponse(patientService.getById(id));
    }
}
