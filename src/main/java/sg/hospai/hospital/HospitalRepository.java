package sg.hospai.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
