package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.components.DocumentConverter;
import com.emanuel.mediaservice.dtos.DocumentDto;
import com.emanuel.mediaservice.entities.DocumentEntity;
import com.emanuel.mediaservice.exceptions.DocumentException;
import com.emanuel.mediaservice.exceptions.InfectedFileException;
import com.emanuel.mediaservice.repositories.DocumentRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
@AllArgsConstructor
public class DocumentService {

    private final ScanService scanService;
    private final DocumentRepository documentRepository;
    private final DocumentConverter documentConverter;

    @SneakyThrows
    public DocumentDto uploadDocument(MultipartFile file, String title, String description) {
        boolean isInfected = scanService.scanFileForViruses(file);
        if (isInfected) {
            throw new InfectedFileException("The uploaded file is infected with viruses.");
        }
        String fileName = file.getOriginalFilename();
        byte[] content = file.getBytes();
        String contentType = file.getContentType();
        Long size = file.getSize();
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        assert fileName != null;
        String[] parts = fileName.split("\\.");
        String extension = parts[parts.length - 1];
        int numberOfPages = 0;
        if("doc".equals(extension) || "docx".equals(extension)) {
            try (InputStream inputStream = file.getInputStream()) {
                XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(IOUtils.toByteArray(inputStream)));
                numberOfPages = document.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
                document.close();
            } catch (IOException e) {
                throw new DocumentException("Can't close the document: " + e.getMessage());
            }
        } else if ("pdf".equals(extension)){
            try (InputStream inputStream = file.getInputStream()) {
                PDDocument document = PDDocument.load(inputStream);
                numberOfPages = document.getNumberOfPages();
                document.close();
            } catch (IOException e) {
                throw new DocumentException("Can't close the document: " + e.getMessage());
            }
        }

        DocumentEntity documentEntity = new DocumentEntity(null, title, description, fileName, date, contentType, content, size, numberOfPages);
        DocumentEntity savedEntity = documentRepository.save(documentEntity);
        return documentConverter.toDto(savedEntity);
    }
}
