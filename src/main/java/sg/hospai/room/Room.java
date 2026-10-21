package sg.hospai.room;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sg.hospai.ward.Ward;

@Entity
@Table(name = "rooms")
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @Column(nullable = false)
    private Integer number;
}
