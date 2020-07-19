package be.harm.carshare.reservations.reservation;

import org.springframework.stereotype.Service;

import java.util.Set;

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
    public Reservation save(Reservation reservation) {
        // TODO: add checking of times
        return reservationRepository.save(reservation);
    }

}
