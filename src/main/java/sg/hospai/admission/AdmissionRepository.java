package sg.hospai.admission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    boolean existsByPatientIdAndStatus(Long patientId, AdmissionStatus status);

    @Query("""
        select a from Admission a
        join fetch a.patient
        join fetch a.bed b
        join fetch b.room r
        join fetch r.ward w
        join fetch w.hospital
        where a.status = sg.hospai.admission.AdmissionStatus.ACTIVE
          and a.patient.id = :patientId
        """)
    Optional<Admission> getCurrentAdmissionByPatient(Long patientId);

    @Query(value = """
        select a from Admission a
        join fetch a.patient
        join fetch a.bed b
        join fetch b.room r
        join fetch r.ward
        where a.patient.id = :patientId
        order by a.admissionAt desc
        """,
        countQuery = "select count(a) from Admission a where a.patient.id = :patientId")
    Page<Admission> findHistoryByPatient(Long patientId, Pageable pageable);

    @Query("""
        select a from Admission a
        join fetch a.patient
        join fetch a.bed b
        where b.id = :bedId
        order by a.admissionAt desc
        """)
    List<Admission> findHistoryByBed(Long bedId);

    @Query("""
        select a from Admission a
        join fetch a.patient p
        join fetch a.bed b
        join fetch b.room r
        join fetch r.ward w
        where a.status = sg.hospai.admission.AdmissionStatus.ACTIVE
        order by w.speciality, p.name
        """)
    List<Admission> findActiveOrderedBySpecialityAndPatientName();
}
