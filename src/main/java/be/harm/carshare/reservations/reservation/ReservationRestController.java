package be.harm.carshare.reservations.reservation;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@Slf4j
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
    public ResponseEntity registerReservation(@RequestBody Reservation reservation) {
        log.info("New reservation coming in: {}", reservation);

        var savedReservation = reservationService.save(reservation);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", ENDPOINT + savedReservation.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReservation(@PathVariable Long reservationId, Reservation reservation) {
        log.info("Updating reservation: {}", reservation);
        reservationService.update(reservation);
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long reservationId) {
        log.info("Deleting reservation with iD: {}", reservationId);
        reservationService.delete(reservationId);
    }
}
