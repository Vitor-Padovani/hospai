package sg.hospai.ward;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.hospai.shared.BusinessRuleException;
import sg.hospai.hospital.Hospital;
import sg.hospai.hospital.HospitalRepository;
import sg.hospai.room.RoomService;
import sg.hospai.room.dtos.CreateRoomRequest;
import sg.hospai.shared.NotFoundException;
import sg.hospai.ward.dtos.CreateWardRequest;
import sg.hospai.ward.dtos.WardSummary;

import java.util.List;

@Service
public class WardService {

    private final WardRepository wardRepository;
    private final WardMapper wardMapper;
    private final HospitalRepository hospitalRepository;
    private final RoomService roomService;

    public WardService(WardRepository wardRepository, WardMapper wardMapper, HospitalRepository hospitalRepository,
                        RoomService roomService) {
        this.wardRepository = wardRepository;
        this.wardMapper = wardMapper;
        this.hospitalRepository = hospitalRepository;
        this.roomService = roomService;
    }

    @Transactional
    public WardSummary create(Long hospitalId, CreateWardRequest request) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new NotFoundException("Hospital " + hospitalId + " not found"));
        return create(hospital, request);
    }

    @Transactional
    public WardSummary create(Hospital hospital, CreateWardRequest request) {
        if (wardRepository.existsByHospitalIdAndSpeciality(hospital.getId(), request.speciality())) {
            throw new BusinessRuleException(
                    "Hospital " + hospital.getId() + " already has a " + request.speciality() + " ward");
        }
        Ward ward = wardMapper.toEntity(request);
        ward.setHospital(hospital);
        ward = wardRepository.save(ward);

        int roomCount = 0;
        int bedCount = 0;
        if (request.roomCount() != null) {
            int bedsPerRoom = request.bedsPerRoom() != null ? request.bedsPerRoom() : 0;
            roomCount = request.roomCount();
            for (int i = 0; i < roomCount; i++) {
                roomService.createRoom(ward, bedsPerRoom);
            }
            bedCount = roomCount * bedsPerRoom;
        } else if (request.rooms() != null) {
            roomCount = request.rooms().size();
            for (CreateRoomRequest roomRequest : request.rooms()) {
                int roomBedCount = roomRequest.bedCount() != null ? roomRequest.bedCount() : 0;
                roomService.createRoom(ward, roomBedCount);
                bedCount += roomBedCount;
            }
        }
        return new WardSummary(ward, roomCount, bedCount);
    }

    public List<Ward> listByHospital(Long hospitalId) {
        // Just verifies existence
        getHospitalOrThrow(hospitalId);
        return wardRepository.findByHospitalIdOrderById(hospitalId);
    }

    public Ward getById(Long id) {
        return wardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ward " + id + " not found!"));
    }

    private Hospital getHospitalOrThrow(Long hospitalId) {
        return hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new NotFoundException("Hospital " + hospitalId + " not found"));
    }
}
