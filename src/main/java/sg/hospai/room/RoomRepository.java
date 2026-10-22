package sg.hospai.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.hospai.bed.Status;
import sg.hospai.room.dtos.RoomOccupancySummary;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByWardId(Long wardId);

    @Query("select coalesce(max(r.number), 0) from Room r where r.ward.id = :wardId")
    int findMaxNumberByWardId(Long wardId);

    @Query("""
            select r from Room r
            join fetch r.ward w
            where exists (select 1 from Bed b where b.room = r and b.status = :status)
            order by w.speciality, r.number
            """)
    List<Room> findWithBedByStatus(Status status);

    @Query("""
            select new sg.hospai.room.dtos.RoomOccupancySummary(
                w.speciality,
                count(r),
                sum(case when exists (select 1 from Bed b where b.room = r and b.status = :status)
                         then 1 else 0 end))
            from Room r
            join r.ward w
            group by w.speciality
            order by w.speciality
            """)
    List<RoomOccupancySummary> summarizeBySpeciality(Status status);

    @Query("select count(r) from Room r where r.ward.id = :wardId")
    int countByWardId(Long wardId);
}
