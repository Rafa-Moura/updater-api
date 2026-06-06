package br.com.rafamoura.updater.application.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalClientResponseDTO(
        @JsonProperty("logradouro")
        String streetAddress,
        String owner
) {
}
