package sg.hospai.bed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.hospai.ward.Speciality;

import java.util.List;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {

    List<Bed> findByRoomIdOrderByNumber(Long roomId);

    @Query("select coalesce(max(b.number), 0) from Bed b where b.room.id = :roomId")
    int findMaxNumberByRoomId(Long roomId);

    @Query("""
            select b from Bed b
            join fetch b.room r
            join fetch r.ward w
            where w.hospital.id = :hospitalId
              and w.speciality = :speciality
              and b.status = :status
            order by b.number
            """)
    List<Bed> findAvailableByHospitalIdAndSpeciality(Long hospitalId, Speciality speciality, Status status);

    @Query("""
            select b from Bed b
            join fetch b.room r
            join fetch r.ward w
            where w.speciality = :speciality
              and b.status = :status
            order by b.number
            """)
    List<Bed> findAvailableBySpeciality(Speciality speciality, Status status);

    @Query("""
            select b from Bed b
            join fetch b.room r
            join fetch r.ward w
            join fetch w.hospital h
            order by h.id, w.speciality, r.number, b.number
            """)
    List<Bed> findAllWithLocation();

    @Query("select count(b) from Bed b where b.room.ward.id = :wardId")
    int countByWardId(Long wardId);
}
