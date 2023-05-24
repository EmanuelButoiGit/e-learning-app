package com.emanuel.recommendationservice.services;

import com.emanuel.recommendationservice.proxies.MediaServiceProxy;
import com.emanuel.recommendationservice.proxies.RatingServiceProxy;
import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.dtos.RatingDto;
import com.emanuel.starterlibrary.exceptions.DataBaseException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {
    private static final String RATING_MEDIA_ENDPOINT = "http://localhost:8081/api/rating/media/";
    private static final String DB_MEDIA_EXCEPTION = "Can't get random recommended media. The database is empty. Please upload something";
    private final MediaServiceProxy mediaServiceProxy;
    private final RatingServiceProxy ratingServiceProxy;
    private final Random random = new Random();
    @Value("${minRating}")
    private float minRating;

    @SneakyThrows
    public <T> List<T> getDtoListFromDatabase(Class<T> mediaClassType, String mediaType) {
        List<?> medias = switch (mediaType) {
            case "media" -> mediaServiceProxy.getAllMedias();
            case "audio" -> mediaServiceProxy.getAllAudios();
            case "document" -> mediaServiceProxy.getAllDocuments();
            case "image" -> mediaServiceProxy.getAllImages();
            case "video" -> mediaServiceProxy.getAllVideos();
            default -> null;
        };
        if (medias == null) {
            throw new NullPointerException("The table is empty!");
        }
        return medias.stream()
                .map(mediaClassType::cast)
                .toList();
    }

    public <T extends MediaDto> Float getGeneralRatingBasedOnMedia(T media) {
        RatingDto rating;
        try {
            rating = ratingServiceProxy.getMediaByRatingId(media.getId());
            if (rating == null){
                return 0f;
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return 0F;
        }
        return rating.getGeneralRating();
    }


    public <T extends MediaDto> List<T> getSortedMedia(int numberOfMedias, List<T> medias, Map<Long, Double> scores) {
        List<Map.Entry<Long, Double>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        List<Long> mediaIds = sortedScores.stream()
                .limit(numberOfMedias)
                .map(Map.Entry::getKey)
                .toList();

        List<T> sortedMedia = new ArrayList<>();
        for (Long id : mediaIds) {
            for (T media : medias) {
                if (media.getId().equals(id)) {
                    sortedMedia.add(media);
                    break;
                }
            }
        }
        return sortedMedia;
    }

    @SneakyThrows
    public <T extends MediaDto> T getRandomRecommendedMedia(Class<T> mediaClassType, String mediaType) {
        List<T> medias = getDtoListFromDatabase(mediaClassType, mediaType);
        if(medias.isEmpty()){
            throw new DataBaseException(DB_MEDIA_EXCEPTION);
        }
        List<T> passMedias = new ArrayList<>();
        for (T media : medias) {
            Float generalRating = getGeneralRatingBasedOnMedia(media);
            if (generalRating >= minRating) {
                passMedias.add(media);
            }
        }
        if (passMedias.isEmpty()) {
            log.info("No media has a rating above {}", minRating);
            return medias.get(random.nextInt(medias.size()));
        }
        return passMedias.get(random.nextInt(passMedias.size()));
    }

    public Integer calculateResolutionQuality(Integer quality) {
        int score = 0;
        if (quality == 144) {
            score = 3;
        } else if (quality == 360) {
            score = 4;
        } else if (quality == 480) {
            score = 5;
        } else if (quality == 720) {
            score = 6;
        } else if (quality == 1080) {
            score = 7;
        } else if (quality == 1440) {
            score = 8;
        } else if (quality == 2160) {
            score = 9;
        } else if (quality == 4320) {
            score = 10;
        }
        return score;
    }

    @SneakyThrows
    public <T extends MediaDto> List<String> getTitlesOfMediasBasedOnRating(Class<T> mediaClassType, String mediaType, @NotNull @Min(value = 1) int numberOfMedias) {
        List<T> medias = getDtoListFromDatabase(mediaClassType, mediaType);
        if(medias.isEmpty()){
            throw new DataBaseException(DB_MEDIA_EXCEPTION);
        }
        HashMap<String, Float> map = new HashMap<>();
        for (T media : medias) {
            Float generalRating = getGeneralRatingBasedOnMedia(media);
            map.put(media.getTitle(), generalRating);
        }
        if (map.isEmpty()) {
            log.info("No media has a rating");
        }
        List<Map.Entry<String, Float>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.<String, Float>comparingByValue().reversed());
        return entries.stream()
                .map(Map.Entry::getKey)
                .limit(numberOfMedias)
                .toList();
    }

    @SneakyThrows
    public <T extends MediaDto> List<T> getMediasBasedOnRating(Class<T> mediaClassType, String mediaType, @NotNull @Min(value = 1) int numberOfMedias) {
        List<T> medias = getDtoListFromDatabase(mediaClassType, mediaType);
        if(medias.isEmpty()){
            throw new DataBaseException(DB_MEDIA_EXCEPTION);
        }
        HashMap<T, Float> map = new HashMap<>();
        for (T media : medias) {
            Float generalRating = getGeneralRatingBasedOnMedia(media);
            map.put(media, generalRating);
        }
        if (map.isEmpty()) {
            log.info("No media has a rating");
        }
        List<Map.Entry<T, Float>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.<T, Float>comparingByValue().reversed());
        return entries.stream()
                .map(Map.Entry::getKey)
                .limit(numberOfMedias)
                .toList();
    }
}
