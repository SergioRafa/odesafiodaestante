package br.com.sergiorafa.odesafiodaestante.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ConsumoApiGutendex {

    private final HttpClient client = HttpClient.newHttpClient();

    public String obterDados(String endereco) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();

            System.out.println(">>> JSON BRUTO RECEBIDO PELA ConsumoApiGutendex: " + jsonResponse);

            return jsonResponse;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao obter dados da API: " + e.getMessage(), e);
        }
    }
}