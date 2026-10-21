package sg.hospai.hospital;

import org.springframework.stereotype.Service;
import sg.hospai.shared.BusinessRuleException;
import sg.hospai.shared.NotFoundException;
import sg.hospai.hospital.dtos.CreateHospitalRequest;
import sg.hospai.hospital.dtos.HospitalSummary;
import sg.hospai.ward.WardRepository;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;
    private final WardRepository wardRepository;

    public HospitalService(HospitalRepository hospitalRepository, HospitalMapper hospitalMapper,
                           WardRepository wardRepository) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalMapper = hospitalMapper;
        this.wardRepository = wardRepository;
    }

    public Hospital create(CreateHospitalRequest request) {
        if (hospitalRepository.existsByCnpj(request.cnpj())) {
            throw new BusinessRuleException("A hospital with CNPJ " + request.cnpj() + " already exists");
        }
        Hospital hospital = hospitalMapper.toEntity(request);
        return hospitalRepository.save(hospital);
    }

    public Hospital getById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hospital " + id + " not found"));
    }

    public HospitalSummary getSummary(Long id) {
        Hospital hospital = getById(id);
        return new HospitalSummary(hospital, wardRepository.findByHospitalIdOrderById(id));
    }
}
