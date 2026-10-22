package sg.hospai.admission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    boolean existsByPatientIdAndStatus(Long patientId, AdmissionStatus status);

    @Query("""
        select a from Admission a
        where a.status = sg.hospai.admission.AdmissionStatus.ACTIVE
          and a.patient.id = :patientId
        """)
    Optional<Admission> getCurrentAdmissionByPatient(Long patientId);
}
