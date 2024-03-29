package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.options.FileOption;
import com.emanuel.mediaservice.converters.DocumentConverter;
import com.emanuel.mediaservice.converters.MediaConverter;
import com.emanuel.mediaservice.entities.DocumentEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.repositories.DocumentRepository;
import com.emanuel.starterlibrary.dtos.DocumentDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.exceptions.DataBaseException;
import com.emanuel.starterlibrary.exceptions.DocumentException;
import com.emanuel.starterlibrary.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class DocumentService {

    private final ValidationService validationService;
    private final MediaService mediaService;
    private final MediaConverter mediaConverter;
    private final DocumentRepository documentRepository;
    private final DocumentConverter documentConverter;

    @SneakyThrows
    public DocumentDto uploadDocument(MultipartFile file, String title, String description) {
        String extension = validationService.validateFile(FileOption.getDOCUMENT_EXTENSIONS(), file);
        MediaDto mediaFields = mediaService.getMediaFields(file, title, description, extension);
        int numberOfPages = 0;
        if("docx".equalsIgnoreCase(extension)) {
            try (InputStream inputStream = file.getInputStream()) {
                XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(IOUtils.toByteArray(inputStream)));
                numberOfPages = document.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
                document.close();
            } catch (IOException e) {
                throw new DocumentException("Can't process the document: " + e);
            }
        } else if ("pdf".equalsIgnoreCase(extension)){
            try (InputStream inputStream = file.getInputStream()) {
                PDDocument document = PDDocument.load(inputStream);
                numberOfPages = document.getNumberOfPages();
                document.close();
            } catch (IOException e) {
                throw new DocumentException("Can't close the document: " + e);
            }
        }

        MediaEntity entity = mediaConverter.toEntity(mediaFields);
        DocumentEntity documentEntity = new DocumentEntity(entity, numberOfPages);
        DocumentEntity savedEntity = documentRepository.save(documentEntity);
        mediaService.sendNotification(savedEntity.getTitle());
        return documentConverter.toDto(savedEntity);
    }

    @SneakyThrows
    public List<DocumentDto> getAllDocuments() {
        try {
            List<DocumentEntity> allDocuments = documentRepository.findAll();
            return allDocuments.stream()
                    .map(documentConverter::toDto)
                    .toList();
        } catch (Exception e) {
            throw new DataBaseException(MediaService.DB_FETCH_EXCEPTION + e);
        }
    }

    @SneakyThrows
    public DocumentDto getDocumentById(@NotNull @Min(value = 0) Long id) {
        DocumentEntity document = new DocumentEntity();
        final DocumentEntity entity = document;
        document = documentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("%s not found with id %s ", entity.getClass(), id));
        return documentConverter.toDto(document);
    }

    public DocumentDto deleteDocument(@NotNull @Min(value = 0) Long id) {
        DocumentDto document = getDocumentById(id);
        documentRepository.delete(documentConverter.toEntity(document));
        return document;
    }

    @SneakyThrows
    public DocumentDto updateDocument(@NotNull @Min(value = 0) Long id, DocumentDto dto) {
        MediaDto media = mediaService.updateMediaFields(id, dto);
        DocumentDto updatedDocument = new DocumentDto(media, dto.getNumberOfPages());
        DocumentEntity documentEntity = documentRepository.save(documentConverter.toEntity(updatedDocument));
        return documentConverter.toDto(documentEntity);
    }

    public void deleteAllDocuments() {
        documentRepository.deleteAll();
    }
}
