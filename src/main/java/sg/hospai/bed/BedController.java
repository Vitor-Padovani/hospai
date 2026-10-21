package sg.hospai.bed;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.hospai.bed.dtos.BedResponse;
import sg.hospai.bed.dtos.CreateBedsRequest;

import java.util.List;

@RestController
@RequestMapping("/rooms/{roomId}/beds")
public class BedController {

    private final BedService bedService;
    private final BedMapper bedMapper;

    public BedController(BedService bedService, BedMapper bedMapper) {
        this.bedService = bedService;
        this.bedMapper = bedMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<BedResponse> create(
            @PathVariable Long roomId, @Valid @RequestBody CreateBedsRequest request
    ) {
        return bedMapper.toResponse(bedService.create(roomId, request));
    }

    @GetMapping
    public List<BedResponse> list(@PathVariable Long roomId) {
        return bedMapper.toResponse(bedService.listByRoom(roomId));
    }

    @PostMapping("/{id}/release")
    public BedResponse release(@PathVariable Long id) {
        return bedMapper.toResponse(bedService.release(id));
    }
}
