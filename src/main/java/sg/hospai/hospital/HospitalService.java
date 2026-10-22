package sg.hospai.hospital;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.hospai.bed.BedRepository;
import sg.hospai.room.RoomRepository;
import sg.hospai.shared.BusinessRuleException;
import sg.hospai.shared.NotFoundException;
import sg.hospai.hospital.dtos.CreateHospitalRequest;
import sg.hospai.hospital.dtos.HospitalSummary;
import sg.hospai.ward.Ward;
import sg.hospai.ward.WardRepository;
import sg.hospai.ward.WardService;
import sg.hospai.ward.dtos.CreateWardRequest;
import sg.hospai.ward.dtos.WardSummary;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;
    private final WardRepository wardRepository;
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final WardService wardService;

    public HospitalService(HospitalRepository hospitalRepository, HospitalMapper hospitalMapper,
                            WardRepository wardRepository, RoomRepository roomRepository,
                            BedRepository bedRepository, WardService wardService) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalMapper = hospitalMapper;
        this.wardRepository = wardRepository;
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
        this.wardService = wardService;
    }

    @Transactional
    public HospitalSummary create(CreateHospitalRequest request) {
        if (hospitalRepository.existsByCnpj(request.cnpj())) {
            throw new BusinessRuleException("A hospital with CNPJ " + request.cnpj() + " already exists");
        }
        Hospital hospital = hospitalMapper.toEntity(request);
        hospital = hospitalRepository.save(hospital);

        List<WardSummary> wardSummaries = new ArrayList<>();
        // Process inner wards request
        if (request.wards() != null) {
            for (CreateWardRequest wardRequest : request.wards()) {
                wardSummaries.add(wardService.create(hospital, wardRequest));
            }
        }

        return new HospitalSummary(hospital, wardSummaries);
    }

    public Hospital getById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hospital " + id + " not found"));
    }

    public HospitalSummary getSummary(Long id) {
        Hospital hospital = getById(id);
        List<Ward> wards = wardRepository.findByHospitalIdOrderById(id);
        List<WardSummary> wardSummaries = wards.stream()
                .map(ward -> new WardSummary(ward, roomRepository.countByWardId(ward.getId()),
                        bedRepository.countByWardId(ward.getId())))
                .toList();
        return new HospitalSummary(hospital, wardSummaries);
    }
}
