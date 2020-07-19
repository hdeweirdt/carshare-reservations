package be.harm.carshare.reservations.reservation;

import java.util.Set;

public interface ReservationService {

    Set<Reservation> findAllByCarId(Long carId);

    Reservation save(Reservation reservation);
}
