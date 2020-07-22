package be.harm.carshare.reservations.reservation;

import java.util.Set;

public interface ReservationService {

    Set<Reservation> findAllByCarId(Long carId);

    Set<Reservation> findAllByUserId(Long userId);

    Set<Reservation> findByUserIdAndCarId(Long userId, Long carId);

    Reservation save(Reservation reservation);

    void update(Reservation reservation);

    void delete(Reservation reservation);
}
