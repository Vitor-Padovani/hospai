package sg.hospai.hospital;

import org.springframework.stereotype.Service;
import sg.hospai.hospital.requests.CreateHospitalRequest;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;

    public HospitalService(HospitalRepository hospitalRepository, HospitalMapper hospitalMapper) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalMapper = hospitalMapper;
    }

    public Hospital create(CreateHospitalRequest request) {
        Hospital hospital = hospitalMapper.toEntity(request);
        return hospitalRepository.save(hospital);
    }

    public Hospital getById(Long id) {
        return hospitalRepository.findById(id).orElseThrow();
    }
}
