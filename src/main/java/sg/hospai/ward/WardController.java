package sg.hospai.ward;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.hospai.ward.dtos.CreateWardRequest;
import sg.hospai.ward.dtos.WardResponse;

import java.util.List;

@RestController
@RequestMapping("/hospitals/{hospitalId}/wards")
public class WardController {

    private final WardService wardService;
    private final WardMapper wardMapper;

    public WardController(WardService wardService, WardMapper wardMapper) {
        this.wardService = wardService;
        this.wardMapper = wardMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    WardResponse create(@PathVariable Long hospitalId, @Valid @RequestBody CreateWardRequest request) {
        return wardMapper.toResponse(wardService.create(hospitalId, request));
    }

    @GetMapping
    List<WardResponse> list(@PathVariable Long hospitalId) {
        return wardMapper.toResponse(wardService.listByHospital(hospitalId));
    }
}
