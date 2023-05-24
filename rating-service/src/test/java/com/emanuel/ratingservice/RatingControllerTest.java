package com.emanuel.ratingservice;

import com.emanuel.ratingservice.proxies.MediaServiceProxy;
import com.emanuel.ratingservice.services.RatingService;
import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.dtos.RatingDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class RatingControllerTest {


    @Autowired
    protected TestRestTemplate rest;
    private final HttpHeaders httpHeaders = new HttpHeaders();

    @MockBean
    protected MediaServiceProxy mediaServiceProxy;

    @Test
    void addRatingTest() {
        // assume
        final RatingDto ratingDto = new RatingDto(
                1L,
                1L,
                "Test Title",
                "Test Description",
                0f,
                7.5f,
                7.5f,
                7.5f,
                7.5f,
                7.5f,
                7.5f);

        HttpEntity<RatingDto> requestEntity = new HttpEntity<>(ratingDto, httpHeaders);
        Mockito.when(mediaServiceProxy.getMediaById(ratingDto.getMediaId()))
                .thenReturn(new MediaDto());

        // act
        ResponseEntity<RatingDto> result = rest.exchange("/api/rating", HttpMethod.POST, requestEntity, RatingDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        RatingDto ratingFromServer = result.getBody();
        assertThat(ratingFromServer).isNotNull();
        assertEquals(ratingDto.getId(), ratingFromServer.getId());
        assertEquals(ratingDto.getTitle(), ratingFromServer.getTitle());
        assertEquals(ratingDto.getDescription(), ratingFromServer.getDescription());
        Float expectedGeneralRating = calculateGeneralRating(
                ratingDto.getTutorRating(),
                ratingDto.getContentRating(),
                ratingDto.getContentStructureRating(),
                ratingDto.getPresentationRating(),
                ratingDto.getEngagementRating(),
                ratingDto.getDifficultyRating()
        );
        assertEquals(expectedGeneralRating, ratingFromServer.getGeneralRating());
        assertEquals(ratingDto.getTutorRating(), ratingFromServer.getTutorRating());
        assertEquals(ratingDto.getContentRating(), ratingFromServer.getContentRating());
        assertEquals(ratingDto.getContentStructureRating(), ratingFromServer.getContentStructureRating());
        assertEquals(ratingDto.getPresentationRating(), ratingFromServer.getPresentationRating());
        assertEquals(ratingDto.getEngagementRating(), ratingFromServer.getEngagementRating());
        assertEquals(ratingDto.getDifficultyRating(), ratingFromServer.getDifficultyRating());
    }

    private Float calculateGeneralRating(Float tutorRating, Float contentRating, Float contentStructureRating,
                                         Float presentationRating, Float engagementRating, Float difficultyRating){
        float sumOfRatings = tutorRating + contentRating + contentStructureRating + presentationRating + engagementRating + difficultyRating;
        return sumOfRatings / 6;
    }


}
