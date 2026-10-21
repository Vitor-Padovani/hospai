package sg.hospai.room;

import org.springframework.stereotype.Service;
import sg.hospai.shared.NotFoundException;
import sg.hospai.room.dtos.CreateRoomsRequest;
import sg.hospai.ward.Ward;
import sg.hospai.ward.WardService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final WardService wardService;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper, WardService wardService) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.wardService = wardService;
    }

    public List<Room> create(Long wardId, CreateRoomsRequest request) {
        Ward ward = wardService.getById(wardId);
        List<Room> rooms = new ArrayList<Room>();
        for (int i = 1; i < request.number_of_rooms() + 1; i++) { // Bad performance
            Room room = new Room();
            room.setWard(ward);
            room.setNumber(i);
            rooms.add(room);
            roomRepository.save(room);
        }
        return rooms;
    }

    public List<Room> listByWard(Long wardId) {
        wardService.getById(wardId);
        return roomRepository.findByWardId(wardId);
    }

    public Room getById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room " + id + " not found!"));
    }
}
