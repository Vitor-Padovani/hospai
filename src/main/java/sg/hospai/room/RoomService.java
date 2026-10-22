package sg.hospai.room;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.hospai.bed.BedService;
import sg.hospai.shared.NotFoundException;
import sg.hospai.room.dtos.CreateRoomsRequest;
import sg.hospai.ward.Ward;
import sg.hospai.ward.WardRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final WardRepository wardRepository;
    private final BedService bedService;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper, WardRepository wardRepository,
                        BedService bedService) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.wardRepository = wardRepository;
        this.bedService = bedService;
    }

    @Transactional
    public List<Room> create(Long wardId, CreateRoomsRequest request) {
        Ward ward = getWardOrThrow(wardId);
        int lastNumber = roomRepository.findMaxNumberByWardId(wardId);
        List<Room> rooms = new ArrayList<>();
        for (int i = 1; i < request.number_of_rooms() + 1; i++) { // Bad performance?
            Room room = new Room();
            room.setWard(ward);
            room.setNumber(lastNumber + i);
            rooms.add(room);
            roomRepository.save(room);
        }
        return rooms;
    }

    @Transactional
    public Room createRoom(Ward ward, int bedCount) {
        Room room = createRoom(ward);
        if (bedCount > 0) {
            bedService.createBeds(room, bedCount);
        }
        return room;
    }

    @Transactional
    public Room createRoom(Ward ward) {
        int lastNumber = roomRepository.findMaxNumberByWardId(ward.getId());
        Room room = new Room();
        room.setWard(ward);
        room.setNumber(lastNumber + 1);
        return roomRepository.save(room);
    }

    public List<Room> listByWard(Long wardId) {
        getWardOrThrow(wardId);
        return roomRepository.findByWardId(wardId);
    }

    public Room getById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room " + id + " not found!"));
    }

    private Ward getWardOrThrow(Long wardId) {
        return wardRepository.findById(wardId)
                .orElseThrow(() -> new NotFoundException("Ward " + wardId + " not found!"));
    }
}
