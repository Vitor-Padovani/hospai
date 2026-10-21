package sg.hospai.bed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {

    List<Bed> findByRoomIdOrderByNumber(Long roomId);

    @Query("select coalesce(max(b.number), 0) from Bed b where b.room.id = :roomId")
    int findMaxNumberByRoomId(Long roomId);
}
