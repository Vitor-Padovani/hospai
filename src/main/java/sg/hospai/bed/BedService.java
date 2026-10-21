package sg.hospai.bed;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.hospai.bed.dtos.CreateBedsRequest;
import sg.hospai.hospital.HospitalRepository;
import sg.hospai.room.Room;
import sg.hospai.room.RoomRepository;
import sg.hospai.shared.BusinessRuleException;
import sg.hospai.shared.NotFoundException;
import sg.hospai.ward.Speciality;

import java.util.ArrayList;
import java.util.List;

@Service
public class BedService {

    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final HospitalRepository hospitalRepository;

    public BedService(BedRepository bedRepository, RoomRepository roomRepository,
                       HospitalRepository hospitalRepository) {
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Transactional
    public List<Bed> create(Long roomId, CreateBedsRequest request) {
        Room room = getRoomOrThrow(roomId);
        return createBeds(room, request.count());
    }

    @Transactional
    public List<Bed> createBeds(Room room, int count) {
        int lastNumber = bedRepository.findMaxNumberByRoomId(room.getId());
        List<Bed> beds = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Bed bed = new Bed();
            bed.setRoom(room);
            bed.setNumber(lastNumber + i);
            bed.setStatus(Status.UNOCCUPIED);
            beds.add(bed);
        }
        return bedRepository.saveAll(beds);
    }

    public List<Bed> listByRoom(Long roomId) {
        // Just verifies existence
        getRoomOrThrow(roomId);
        return bedRepository.findByRoomIdOrderByNumber(roomId);
    }

    public List<Bed> listAvailable(Long hospitalId, Speciality speciality) {
        // Just verifies existence
        hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new NotFoundException("Hospital " + hospitalId + " not found"));
        return bedRepository.findAvailableByHospitalIdAndSpeciality(hospitalId, speciality, Status.UNOCCUPIED);
    }

    private Room getRoomOrThrow(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room " + roomId + " not found!"));
    }

    public Bed getById(Long id) {
        return bedRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bed " + id + " not found"));
    }

    @Transactional
    public Bed occupy(Long id) {
        Bed bed = getById(id);
        if (bed.getStatus() != Status.UNOCCUPIED) {
            throw new BusinessRuleException("Bed " + id + " is not available");
        }
        bed.setStatus(Status.OCCUPIED);
        return bedRepository.save(bed);
    }

    @Transactional
    public Bed prepare(Long id) {
        Bed bed = getById(id);
        if (bed.getStatus() != Status.OCCUPIED) {
            throw new BusinessRuleException("Bed " + id + " is not occupied");
        }
        bed.setStatus(Status.IN_PREPARATION);
        return bedRepository.save(bed);
    }

    @Transactional
    public Bed release(Long id) {
        Bed bed = getById(id);
        if (bed.getStatus() != Status.IN_PREPARATION) {
            throw new BusinessRuleException("Bed " + id + " is not in preparation");
        }
        bed.setStatus(Status.UNOCCUPIED);
        return bedRepository.save(bed);
    }
}
