package com.ampmap.ampmap.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeocodingService {

    private final RestTemplate restTemplate;

    @Value("${geocoding.nominatim.url}")
    private String nominatimUrl;

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    

    @SuppressWarnings("deprecation")
    public Map<String, Double> getCoordinatesFromAddress(String logradouro, String numero, String cidade, String estado) {
        String fullAddress = String.format("%s, %s, %s, %s", logradouro, numero, cidade, estado);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(nominatimUrl)
                .queryParam("q", fullAddress)
                .queryParam("format", "json")
                .queryParam("limit", 1);

        // A API do Nominatim retorna um array de resultados
        JsonNode[] results = restTemplate.getForObject(uriBuilder.toUriString(), JsonNode[].class);

        if (results != null && results.length > 0) {
            JsonNode firstResult = results[0];
            double lat = firstResult.get("lat").asDouble();
            double lon = firstResult.get("lon").asDouble();
            
            Map<String, Double> coordinates = new HashMap<>();
            coordinates.put("latitude", lat);
            coordinates.put("longitude", lon);
            return coordinates;
        }

        // Lançar exceção se o endereço não for encontrado
        throw new RuntimeException("Endereço não encontrado ou inválido: " + fullAddress);
    }
}