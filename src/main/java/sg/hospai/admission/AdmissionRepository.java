package sg.hospai.admission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    boolean existsByPatientIdAndStatus(Long patientId, AdmissionStatus status);
}
