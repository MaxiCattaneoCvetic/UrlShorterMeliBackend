package com.example.MeliUrlShorter.presentation.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request Url", name = "RequestUrl")
public record RequestUrl(
        @Schema(description = "Ingresa el protocolo", example = "http", required = true)
        String protocol,
        @Schema(description = "Ingresa el dominio", example = "mercadolibre", required = true)
        String domain,
        @Schema(description = "Ingresa el tld", example = ".com", required = true)
        String tld,
        @Schema(description = "Ingresa el puerto si es necesario", example = "8080", required = true)
        String port,
        @Schema(description = "Ingresa la ruta", example = "/mis/envios", required = true)
        String route

) {
}
