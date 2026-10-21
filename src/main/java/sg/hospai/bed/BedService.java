package sg.hospai.bed;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.hospai.bed.dtos.CreateBedsRequest;
import sg.hospai.room.Room;
import sg.hospai.room.RoomService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BedService {

    private final BedRepository bedRepository;
    private final RoomService roomService;

    public BedService(BedRepository bedRepository, RoomService roomService) {
        this.bedRepository = bedRepository;
        this.roomService = roomService;
    }

    @Transactional
    public List<Bed> create(Long roomId, CreateBedsRequest request) {
        Room room = roomService.getById(roomId);
        List<Bed> beds = new ArrayList<>();
        for (int i = 1; i <= request.count(); i++) {
            Bed bed = new Bed();
            bed.setRoom(room);
            bed.setNumber(i);
            bed.setStatus(Status.UNOCCUPIED);
            beds.add(bed);
        }
        return bedRepository.saveAll(beds);
    }

    public List<Bed> listByRoom(Long roomId) {
        // Just verifies existence
        roomService.getById(roomId);
        return bedRepository.findByRoomIdOrderByNumber(roomId);
    }
}
