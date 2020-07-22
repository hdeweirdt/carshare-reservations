package be.harm.carshare.reservations.reservation;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "userIndex", columnList = "userId"),
        @Index(name = "carIndex", columnList = "carId")}
)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private Long carId;

    @Getter
    private LocalDateTime start;

    @Getter
    private LocalDateTime end;

}
