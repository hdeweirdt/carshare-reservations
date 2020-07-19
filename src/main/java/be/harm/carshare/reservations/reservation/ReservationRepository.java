package be.harm.carshare.reservations.reservation;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    Set<Reservation> findByCarId(Long id);
}
