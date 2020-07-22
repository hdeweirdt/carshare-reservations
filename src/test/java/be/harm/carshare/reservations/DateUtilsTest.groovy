package be.harm.carshare.reservations


import spock.lang.Specification

import java.time.LocalDateTime

class DateUtilsTest extends Specification {

    def "is between others"() {
        expect:
        DateUtils.dateIsBetween(date, start, end) == expected

        where:
        date                                 | start                                | end                                   | expected
        LocalDateTime.of(2020, 8, 15, 12, 0) | LocalDateTime.of(2020, 1, 1, 1, 0)   | LocalDateTime.of(2020, 12, 30, 23, 0) | true
        LocalDateTime.of(2020, 8, 15, 12, 0) | LocalDateTime.of(2020, 8, 15, 12, 0) | LocalDateTime.of(2020, 12, 30, 23, 0) | false
        LocalDateTime.of(2020, 1, 1, 1, 0)   | LocalDateTime.of(2020, 8, 15, 12, 0) | LocalDateTime.of(2020, 12, 30, 23, 0) | false
    }
}
