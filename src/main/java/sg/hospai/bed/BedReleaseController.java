package sg.hospai.bed;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.hospai.bed.dtos.BedResponse;

@RestController
@RequestMapping("/beds")
public class BedReleaseController {

    private final BedService bedService;
    private final BedMapper bedMapper;

    public BedReleaseController(BedService bedService, BedMapper bedMapper) {
        this.bedService = bedService;
        this.bedMapper = bedMapper;
    }

    @PostMapping("/{id}/release")
    BedResponse release(@PathVariable Long id) {
        return bedMapper.toResponse(bedService.release(id));
    }
}
