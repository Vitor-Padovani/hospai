package sg.hospai.hospital;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.hospai.hospital.requests.CreateHospitalRequest;
import sg.hospai.hospital.responses.HospitalResponse;

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
    HospitalResponse create(@Valid @RequestBody CreateHospitalRequest request) {
        return hospitalMapper.toResponse(hospitalService.create(request));
    }
}
