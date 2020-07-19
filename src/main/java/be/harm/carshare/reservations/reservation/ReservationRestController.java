package be.harm.carshare.reservations.reservation;

import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("reservations")
public class ReservationRestController {
    private final ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{carId}")
    public Set<Reservation> getReservationsForCar(@PathVariable Long carId) {
        return reservationService.findAllByCarId(carId);
    }

    @PostMapping
    public Reservation registerReservationForCar(@RequestBody Reservation reservation) {
        return reservationService.save(reservation);
    }
}
