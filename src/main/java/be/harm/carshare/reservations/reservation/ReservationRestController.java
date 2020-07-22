package be.harm.carshare.reservations.reservation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(ReservationRestController.ENDPOINT)
public class ReservationRestController {
    protected static final String ENDPOINT = "/reservations";

    private final ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<Set<Reservation>> getReservations(
            Optional<Long> userId,
            Optional<Long> carId) {
        if (userId.isPresent() && carId.isEmpty()) {
            return new ResponseEntity<>(reservationService.findAllByUserId(userId.get()), HttpStatus.OK);
        } else if (carId.isPresent() && userId.isEmpty()) {
            return new ResponseEntity<>(reservationService.findAllByCarId(carId.get()), HttpStatus.OK);
        } else if (carId.isPresent() && userId.isPresent()) {
            return new ResponseEntity<>(reservationService.findByUserIdAndCarId(userId.get(), carId.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping
    public ResponseEntity registerReservationForCar(@RequestBody Reservation reservation) {
        var savedReservation = reservationService.save(reservation);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", ENDPOINT + savedReservation.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReservation(@PathVariable Long reservationId, Reservation reservation) {
        reservationService.update(reservation);
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long reservationId, Reservation reservation) {
        reservationService.delete(reservation);
    }
}
