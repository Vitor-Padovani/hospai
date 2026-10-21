package sg.hospai.ward;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sg.hospai.hospital.Hospital;

@Entity
@Table(name = "wards")
@Getter
@Setter
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Speciality speciality;
}
