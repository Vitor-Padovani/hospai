package sg.hospai.admission;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sg.hospai.bed.Bed;
import sg.hospai.patient.Patient;

import java.time.LocalDateTime;

@Entity
@Table(name = "admissions")
@Getter
@Setter
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bed_id")
    private Bed bed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AdmissionStatus status;

    @Column(nullable = false)
    private LocalDateTime admissionAt;

    private LocalDateTime dischargeAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
