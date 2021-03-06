package be.harm.carshare.reservations.bootstrap;

import be.harm.carshare.reservations.reservation.Reservation;
import be.harm.carshare.reservations.reservation.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Profile("local | dev")
@Slf4j
@Component
public class DataLoader implements CommandLineRunner {
    private final ReservationService reservationService;

    public DataLoader(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void run(String... args) {
        if (reservationService.findAllByCarId(1L).isEmpty()) {
            log.info("Loading testing set of reservations");
            saveReservations();
        }
    }


    private void saveReservations() {
        reservationService.save(
                Reservation.builder()
                        .carId(1L)
                        .userId(1L)
                        .start(LocalDateTime.of(2023, 1, 1, 10, 30))
                        .end(LocalDateTime.of(2023, 1, 2, 10, 30))
                        .build());
    }
}
