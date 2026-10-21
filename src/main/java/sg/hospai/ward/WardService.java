package sg.hospai.ward;

import org.springframework.stereotype.Service;
import sg.hospai.shared.BusinessRuleException;
import sg.hospai.hospital.Hospital;
import sg.hospai.hospital.HospitalService;
import sg.hospai.ward.dtos.CreateWardRequest;

import java.util.List;

@Service
public class WardService {

    private final WardRepository wardRepository;
    private final WardMapper wardMapper;
    private final HospitalService hospitalService;

    public WardService(WardRepository wardRepository, WardMapper wardMapper, HospitalService hospitalService) {
        this.wardRepository = wardRepository;
        this.wardMapper = wardMapper;
        this.hospitalService = hospitalService;
    }

    public Ward create(Long hospitalId, CreateWardRequest request) {
        Hospital hospital = hospitalService.getById(hospitalId);
        if (wardRepository.existsByHospitalIdAndSpeciality(hospitalId, request.speciality())) {
            throw new BusinessRuleException(
                    "Hospital " + hospitalId + " already has a " + request.speciality() + " ward");
        }
        Ward ward = wardMapper.toEntity(request);
        ward.setHospital(hospital);
        return wardRepository.save(ward);
    }

    public List<Ward> listByHospital(Long hospitalId) {
        // Just verifies existence
        hospitalService.getById(hospitalId);
        return wardRepository.findByHospitalIdOrderById(hospitalId);
    }
}
