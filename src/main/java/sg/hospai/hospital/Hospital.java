package sg.hospai.hospital;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "hospitals")
@Getter
@Setter
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(nullable = false)
    private String name;

    @Column(length = 14)
    private String phone;

    @Column(length = 14, unique = true)
    private String cnpj;
}
