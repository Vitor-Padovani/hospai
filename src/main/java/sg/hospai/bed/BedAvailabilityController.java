package sg.hospai.bed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sg.hospai.bed.dtos.AvailableBedResponse;
import sg.hospai.bed.dtos.BedDetailResponse;
import sg.hospai.ward.Speciality;

import java.util.List;

@RestController
public class BedAvailabilityController {

    private final BedService bedService;
    private final BedMapper bedMapper;

    public BedAvailabilityController(BedService bedService, BedMapper bedMapper) {
        this.bedService = bedService;
        this.bedMapper = bedMapper;
    }

    @GetMapping("/beds")
    List<BedDetailResponse> listAll() {
        return bedMapper.toDetailResponse(bedService.listAll());
    }

    @GetMapping("/beds/available")
    List<AvailableBedResponse> listAvailable(
            @RequestParam Speciality speciality, @RequestParam(required = false) Long hospitalId
    ) {
        return bedMapper.toAvailableResponse(bedService.listAvailable(speciality, hospitalId));
    }
}
