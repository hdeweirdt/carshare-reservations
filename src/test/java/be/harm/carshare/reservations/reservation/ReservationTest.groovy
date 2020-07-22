package be.harm.carshare.reservations.reservation

import spock.lang.Specification

import java.time.LocalDateTime

class ReservationTest extends Specification {

    def "should recognize overlap with another Reservation"() {
        given: "An existing reservation"
        def reservation = Reservation.builder()
                .start(LocalDateTime.of(2020, 8, 10, 10, 0))
                .end(LocalDateTime.of(2020, 8, 12, 10, 0))
                .build()

        when: "checking overlap with an other reservation"
        def doesOverlap = reservation.overLapsWith(Reservation.builder()
                .start(start)
                .end(end)
                .build())

        then: "it correctly recognizes overlap."
        doesOverlap == expected

        where:
        start                                | end                                  | expected
        LocalDateTime.of(2020, 8, 10, 10, 0) | LocalDateTime.of(2020, 8, 12, 10, 0) | true
        LocalDateTime.of(2020, 8, 9, 10, 0)  | LocalDateTime.of(2020, 8, 11, 10, 0) | true
        LocalDateTime.of(2020, 8, 11, 10, 0) | LocalDateTime.of(2020, 8, 13, 10, 0) | true
        LocalDateTime.of(2020, 8, 9, 10, 0)  | LocalDateTime.of(2020, 8, 13, 10, 0) | true
        LocalDateTime.of(2020, 8, 1, 10, 0)  | LocalDateTime.of(2020, 8, 3, 10, 0)  | false
        LocalDateTime.of(2020, 8, 20, 10, 0) | LocalDateTime.of(2020, 8, 23, 10, 0) | false
    }
}
