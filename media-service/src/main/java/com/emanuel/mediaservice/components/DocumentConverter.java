package com.emanuel.mediaservice.components;

import com.emanuel.mediaservice.dtos.DocumentDto;
import com.emanuel.mediaservice.entities.DocumentEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DocumentConverter {
    public DocumentDto toDto(DocumentEntity documentEntity){
        DocumentDto dto = new DocumentDto();
        dto.setId(documentEntity.getId());
        dto.setTitle(documentEntity.getTitle());
        dto.setDescription(documentEntity.getDescription());
        dto.setFileName(documentEntity.getFileName());
        dto.setUploadDate(documentEntity.getUploadDate());
        dto.setMimeType(documentEntity.getMimeType());
        dto.setContent(documentEntity.getContent());
        dto.setSize(documentEntity.getSize());
        dto.setNumberOfPages(documentEntity.getNumberOfPages());
        return dto;
    }

    public DocumentEntity toEntity(DocumentDto documentDto){
        DocumentEntity entity = new DocumentEntity();
        entity.setId(documentDto.getId());
        entity.setTitle(documentDto.getTitle());
        entity.setDescription(documentDto.getDescription());
        entity.setFileName(documentDto.getFileName());
        entity.setUploadDate(documentDto.getUploadDate());
        entity.setMimeType(documentDto.getMimeType());
        entity.setContent(documentDto.getContent());
        entity.setSize(documentDto.getSize());
        entity.setNumberOfPages(documentDto.getNumberOfPages());
        return entity;
    }
}
