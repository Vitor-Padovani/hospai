package sg.hospai.ward;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, Long> {

    boolean existsByHospitalIdAndSpeciality(Long hospitalId, Speciality speciality);

    List<Ward> findByHospitalIdOrderById(Long hospitalId);
}
