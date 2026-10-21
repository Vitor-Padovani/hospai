package sg.hospai.bed;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sg.hospai.room.Room;

@Entity
@Table(name = "beds")
@Setter
@Getter
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Status status;
}
