package com.emanuel.mediaservice.converters;

import com.emanuel.mediaservice.entities.DocumentEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.starterlibrary.dtos.DocumentDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DocumentConverter extends MediaConverter {
    public DocumentDto toDto(DocumentEntity documentEntity){
        MediaDto media = super.toDto(documentEntity);
        return new DocumentDto(media, documentEntity.getNumberOfPages());
    }

    public DocumentEntity toEntity(DocumentDto documentDto){
        MediaEntity media = super.toEntity(documentDto);
        return new DocumentEntity(media, documentDto.getNumberOfPages());
    }
}
