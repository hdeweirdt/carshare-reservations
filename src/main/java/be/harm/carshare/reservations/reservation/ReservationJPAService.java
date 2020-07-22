package be.harm.carshare.reservations.reservation;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.StreamSupport;

@Service
class ReservationJPAService implements ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationJPAService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Set<Reservation> findAllByCarId(Long carId) {
        return reservationRepository.findByCarId(carId);
    }

    @Override
    public Set<Reservation> findAllByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    @Override
    public Set<Reservation> findByUserIdAndCarId(Long userId, Long carId) {
        return reservationRepository.findByUserIdAndCarId(userId, carId);
    }

    @Override
    public Reservation save(@Valid @RequestBody Reservation newReservation) {
        if (overlapsWithCurrentReservations(newReservation)) {
            throw new IllegalArgumentException("New reservation overlaps with existing!");
        } else {
            return reservationRepository.save(newReservation);
        }
    }

    private boolean overlapsWithCurrentReservations(Reservation newReservation) {
        Iterable<Reservation> currentReservations = reservationRepository.findAll();
        return StreamSupport.stream(currentReservations.spliterator(), false)
                .anyMatch(currentReservation -> currentReservation.overLapsWith(newReservation));
    }

    @Override
    public void update(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

}
