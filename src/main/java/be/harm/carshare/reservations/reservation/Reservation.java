package be.harm.carshare.reservations.reservation;

import be.harm.carshare.reservations.DateUtils;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
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
    @Null
    @Getter
    private Long id;

    @Getter
    @NotNull
    @Positive
    private Long userId;

    @Getter
    @NotNull
    @Positive
    private Long carId;

    @Getter
    @NotNull
    @Future
    private LocalDateTime start;

    @Getter
    @NotNull
    @Future
    private LocalDateTime end;

    public boolean overLapsWith(Reservation other) {
        if (DateUtils.dateIsBetween(other.getEnd(), this.getStart(), this.getEnd())) {
            return true;
        }
        if (DateUtils.dateIsBetween(other.getStart(), this.getStart(), this.getEnd())) {
            return true;
        }
        if (other.getStart().isBefore(this.getStart()) && other.getEnd().isAfter(this.getEnd())) {
            return true;
        }
        if (other.getStart().isEqual(this.getStart()) && other.getEnd().isEqual(this.getEnd())) {
            return true;
        }
        return false;
    }

}
