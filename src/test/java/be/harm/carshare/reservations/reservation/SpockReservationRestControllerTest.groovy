package be.harm.carshare.reservations.reservation

import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ReservationRestController.class)
class SpockReservationRestControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    ReservationService reservationService = Stub() {
        reservationService.save(_ as Reservation) >> { Reservation res -> res }
    }

    @WithMockUser(username = "user", password = "test")
    def "should return only the reservations made by a given user when the userId is passed"() {
        given: "a collection of reservations made by various users, for various cars"
        // TODO

        when: "performing a GET with a userId parameter"
        mockMvc.perform(get("/reservations")
                .param("userId", "1"))
                .andExpect(status().isOk())
        then:
        true
    }


}
