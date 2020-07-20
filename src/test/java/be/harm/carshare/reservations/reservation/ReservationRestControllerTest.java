package be.harm.carshare.reservations.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationRestController.class)
class ReservationRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ReservationService reservationService;

    @BeforeEach
    void setUpMocks() {
        when(reservationService.save(any(Reservation.class))).thenReturn(Reservation.builder().id(1L).build());
    }

    @Test
    void getShouldReturnReservation() throws Exception {
        mockMvc.perform(get("/reservations/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void postShouldCreateNewReservation() throws Exception {
        var reservationDTO = Reservation.builder().build();
        String reservationJson = objectMapper.writeValueAsString(reservationDTO);

        mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationJson))
                .andExpect(status().isCreated());
    }
}