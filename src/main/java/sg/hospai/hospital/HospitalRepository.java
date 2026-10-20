package sg.hospai.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface HospitalRepository extends JpaRepository<Hospital, BigInteger> {
}
