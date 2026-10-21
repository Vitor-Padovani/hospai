package sg.hospai.hospital;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.hospai.hospital.dtos.CreateHospitalRequest;
import sg.hospai.hospital.dtos.HospitalSummaryResponse;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;
    private final HospitalMapper hospitalMapper;

    public HospitalController(HospitalService hospitalService, HospitalMapper hospitalMapper) {
        this.hospitalService = hospitalService;
        this.hospitalMapper = hospitalMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    HospitalSummaryResponse create(@Valid @RequestBody CreateHospitalRequest request) {
        return hospitalMapper.toSummaryResponse(hospitalService.create(request));
    }

    @GetMapping("/{id}")
    HospitalSummaryResponse getById(@PathVariable Long id) {
        return hospitalMapper.toSummaryResponse(hospitalService.getSummary(id));
    }
}
