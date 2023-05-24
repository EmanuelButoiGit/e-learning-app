package com.emanuel.mediaservice.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
@Service
public class ScanService {

    @Value("${virusTotalApiKey}")
    private String virusTotalApiKey;

    @SneakyThrows
    public boolean scanFileForViruses(MultipartFile file) {
        // Encode the file as base64
        String fileContent = new String(file.getBytes(), StandardCharsets.ISO_8859_1);
        String encodedFileContent = Base64.getEncoder().encodeToString(fileContent.getBytes(StandardCharsets.UTF_8));

        // Build the API request URL
        String apiUrl = "https://www.virustotal.com/vtapi/v2/file/scan";
        String urlParameters = "apikey=" + virusTotalApiKey + "&file=" + URLEncoder.encode(Objects.requireNonNull(file.getOriginalFilename()), StandardCharsets.UTF_8);
        URL url = new URL(apiUrl + "?" + urlParameters);

        return sendRequest(url, encodedFileContent);
    }

    @SneakyThrows
    public boolean scanContentForViruses(byte[] content, String fileName) {
        // Encode the file as base64
        String fileContent = new String(content, StandardCharsets.ISO_8859_1);
        String encodedFileContent = Base64.getEncoder().encodeToString(fileContent.getBytes(StandardCharsets.UTF_8));

        // Build the API request URL
        String apiUrl = "https://www.virustotal.com/vtapi/v2/file/scan";
        String urlParameters = "apikey=" + virusTotalApiKey + "&file=" + URLEncoder.encode(Objects.requireNonNull(fileName), StandardCharsets.UTF_8);
        URL url = new URL(apiUrl + "?" + urlParameters);

        return sendRequest(url, encodedFileContent);
    }

    private boolean sendRequest(URL url, String encodedFileContent) throws IOException {
        if (encodedFileContent.isEmpty()){
            log.warn("The file content is empty");
        }
        // Send the API request and capture the response
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        os.write(encodedFileContent.getBytes());
        os.flush();
        os.close();

        // Check the API response for virus detections
        String response;
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            response = scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            log.error("Failed to get API response: {}", e.getMessage());
            return true;
        }
        log.info("Virus Total, API response: {}", response);
        return response.contains("\"response_code\": 1");
    }
}
