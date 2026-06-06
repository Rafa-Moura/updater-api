package br.com.rafamoura.updater.infrastructure.user.clients;

import br.com.rafamoura.updater.application.user.dto.ExternalClientResponseDTO;
import br.com.rafamoura.updater.domain.user.abstractions.clients.ExternalClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExternalClientImpl implements ExternalClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${spring.clients.external-service}")
    private String externalServiceUri;

    @Override
    public ExternalClientResponseDTO getStreetAddress(String email) throws IOException, InterruptedException {

        try {

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(externalServiceUri.concat("/ws/01001000/json/")))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            if (!HttpStatus.valueOf(response.statusCode()).is2xxSuccessful()) {
                log.error("[ExternalClientImpl.getStreetAddress] - External api returns an error status code. [{}]", response.statusCode());

                throw new HttpClientErrorException(HttpStatus.valueOf(response.statusCode()),
                        "An error occurred during external api call");
            }

            return objectMapper.readValue(response.body(), ExternalClientResponseDTO.class);

        } catch (IOException e) {
            log.error("[ExternalClientImpl.getStreetAddress] - An IOException occurred during call external api", e);

            throw e;

        } catch (InterruptedException e) {
            log.error("[ExternalClientImpl.getStreetAddress] - An InterruptedException occurred during call external api", e);

            throw e;
        }
    }
}
