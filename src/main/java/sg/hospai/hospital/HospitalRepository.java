package sg.hospai.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    boolean existsByCnpj(String cnpj);
}
