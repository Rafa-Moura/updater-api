package br.com.rafamoura.updater.domain.user.abstractions.clients;

import br.com.rafamoura.updater.application.user.dto.ExternalClientResponseDTO;

import java.io.IOException;

public interface ExternalClient {

    ExternalClientResponseDTO getStreetAddress(String email) throws IOException, InterruptedException;

}