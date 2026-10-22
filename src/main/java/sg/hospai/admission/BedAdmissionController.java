package sg.hospai.admission;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.hospai.admission.dtos.BedAdmissionHistoryResponse;

import java.util.List;

@RestController
@RequestMapping("/beds/{id}/admissions")
public class BedAdmissionController {

    private final AdmissionService admissionService;
    private final AdmissionMapper admissionMapper;

    public BedAdmissionController(AdmissionService admissionService, AdmissionMapper admissionMapper) {
        this.admissionService = admissionService;
        this.admissionMapper = admissionMapper;
    }

    @GetMapping
    List<BedAdmissionHistoryResponse> list(@PathVariable Long id) {
        return admissionMapper.toBedHistoryResponse(admissionService.listByBed(id));
    }
}
