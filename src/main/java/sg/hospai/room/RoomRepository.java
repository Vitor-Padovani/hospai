package sg.hospai.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByWardId(Long wardId);

    @Query("select coalesce(max(r.number), 0) from Room r where r.ward.id = :wardId")
    int findMaxNumberByWardId(Long wardId);

    @Query("select count(r) from Room r where r.ward.id = :wardId")
    int countByWardId(Long wardId);
}
