package be.harm.carshare.reservations.reservation


import spock.lang.Specification

import java.time.LocalDateTime

class ReservationJPAServiceTest extends Specification {

    def "should allow non-overlapping reservations"() {
        given: "a collection of non-overlapping reservations"
        def reservations = [
                Reservation.builder()
                        .start(LocalDateTime.of(2020, 1, 1, 8, 0))
                        .end(LocalDateTime.of(2020, 1, 2, 8, 0))
                        .build(),
                Reservation.builder()
                        .start(LocalDateTime.of(2020, 1, 2, 9, 0))
                        .end(LocalDateTime.of(2020, 1, 2, 19, 0))
                        .build()
        ]

        and: "a repository containing these reservations"
        def reservationRepository = Stub(ReservationRepository.class, {
            findAll() >> reservations
            save(_ as Reservation) >> { Reservation reservation -> reservations.add(reservation); reservation }
        })

        and: "a reservationService using this repository"
        def reservationService = new ReservationJPAService(reservationRepository)

        and: "we create a new reservation that does not overlap wih an existing one"
        def start = LocalDateTime.of(2022, 2, 1, 10, 30)
        def end = LocalDateTime.of(2022, 3, 1, 12, 30)
        def reservation = new Reservation(1L, 1L, 1L, start, end)

        when: "we save the reservation"
        reservationService.save(reservation)

        then: "it is saved succesfully to the repository"
        reservationRepository.findAll().contains(reservation)
        notThrown(IllegalArgumentException)

    }

    def "should not allow overlapping reservations"() {
        given: "a collection of non-overlapping reservations"
        def reservations = [
                Reservation.builder()
                        .start(LocalDateTime.of(2020, 1, 1, 8, 0))
                        .end(LocalDateTime.of(2020, 1, 2, 8, 0))
                        .build(),
                Reservation.builder()
                        .start(LocalDateTime.of(2020, 1, 2, 9, 0))
                        .end(LocalDateTime.of(2020, 1, 2, 19, 0))
                        .build()
        ]

        and: "a repository containing these reservations"
        def reservationRepository = Stub(ReservationRepository.class, {
            findAll() >> reservations
            save(_ as Reservation) >> null
        })

        and: "a reservationService using this repository"
        def reservationService = new ReservationJPAService(reservationRepository)

        and: "we create a new reservation that overlaps wih an existing one"
        def reservation = new Reservation(1L, 1L, 1L, start, end)

        when: "we save the reservation"
        reservationService.save(reservation)

        then: "the reservation results in an error"
        thrown IllegalArgumentException

        where:
        start                                | end
        LocalDateTime.of(2020, 1, 1, 7, 30)  | LocalDateTime.of(2020, 1, 1, 9, 30) // Overlap with beginning
        LocalDateTime.of(2020, 1, 1, 10, 30) | LocalDateTime.of(2020, 1, 2, 12, 30) // Overlap with end
        LocalDateTime.of(2020, 1, 1, 7, 30)  | LocalDateTime.of(2020, 1, 2, 12, 30) // Overlap fully

    }
}
