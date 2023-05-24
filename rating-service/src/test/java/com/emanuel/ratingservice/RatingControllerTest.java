package com.emanuel.ratingservice;

import com.emanuel.ratingservice.controllers.RatingController;
import com.emanuel.ratingservice.services.RatingService;
import com.emanuel.starterlibrary.dtos.RatingDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RatingController.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Autowired
    private ObjectMapper objectMapper;

    private final RatingDto rating = new RatingDto(
            1L,
            1L,
            "A nice media",
            "I think it was the most informative",
            10.0F,
            10.0F,
            10.0F,
            10.0F,
            10.0F,
            10.0F,
            10.0F
    );

    @Test
    @SneakyThrows
    void testAddRating() {
        Mockito.when(ratingService.addRating(any(RatingDto.class))).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rating)))
                .andExpect(status().isCreated());

        verify(ratingService, times(1)).addRating(any(RatingDto.class));
    }

    @Test
    @SneakyThrows
    void testGetAllRatings() {
        Mockito.when(ratingService.getAllRatings()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/rating")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(ratingService, times(1)).getAllRatings();
    }

    @Test
    @SneakyThrows
    void testDeleteAllRatings() {
        Mockito.doNothing().when(ratingService).deleteAllRatings();

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/rating")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(ratingService, times(1)).deleteAllRatings();
    }

    @Test
    @SneakyThrows
    void testGetMediaByRatingId() {
        Mockito.when(ratingService.getMediaByRatingId(anyLong())).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/rating/media/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(ratingService, times(1)).getMediaByRatingId(anyLong());
    }
}
