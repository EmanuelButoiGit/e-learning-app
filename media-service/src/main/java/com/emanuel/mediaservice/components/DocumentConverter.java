package com.emanuel.mediaservice.components;

import com.emanuel.mediaservice.dtos.AudioDto;
import com.emanuel.mediaservice.dtos.DocumentDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.AudioEntity;
import com.emanuel.mediaservice.entities.DocumentEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DocumentConverter extends MediaConverter {
    public DocumentDto toDto(DocumentEntity documentEntity){
        MediaDto media = super.toDto(documentEntity);
        return DocumentDto.builder()
                .mediaDto(media)
                .numberOfPages(documentEntity.getNumberOfPages())
                .build();
    }

    public DocumentEntity toEntity(DocumentDto documentDto){
        MediaEntity media = super.toEntity(documentDto);
        return DocumentEntity.builder()
                .mediaEntity(media)
                .numberOfPages(documentDto.getNumberOfPages())
                .build();
    }
}
