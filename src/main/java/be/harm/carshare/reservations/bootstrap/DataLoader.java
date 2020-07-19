package be.harm.carshare.reservations.bootstrap;

import be.harm.carshare.reservations.reservation.Reservation;
import be.harm.carshare.reservations.reservation.ReservationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {
    private final ReservationService reservationService;

    public DataLoader(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void run(String... args) {
        if (reservationService.findAllByCarId(1L).isEmpty()) {
            saveReservations();
        }
    }


    private void saveReservations() {
        reservationService.save(
                Reservation.builder()
                        .id(1L)
                        .carId(1L)
                        .userId(1L)
                        .start(LocalDateTime.of(2020, 1, 1, 10, 30))
                        .end(LocalDateTime.of(2020, 1, 2, 10, 30))
                        .build());
    }
}
