package br.com.jones.cinehub.service;

import br.com.jones.cinehub.model.FilmeDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TmdbService {

    @Value("${tmdb.api.token}")
    private String apiToken;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<FilmeDTO> buscarFilmes(String query) {
        String url = apiUrl + "/search/multi?query=" + query + "&language=pt-BR";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<TmdbResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    TmdbResponse.class
            );

            List<FilmeDTO> resultadosBrutos = response.getBody() != null ? response.getBody().getResults() : List.of();

            return resultadosBrutos.stream()
                    .filter(item -> "movie".equals(item.getTipoMidia()) || "tv".equals(item.getTipoMidia()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar no TMDB: " + e.getMessage());
        }
    }

    // NOVO MÉTODO: Busca inteligente por ID (Tenta Filme, se falhar tenta Série)
    public FilmeDTO buscarPorId(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Tentativa 1: Buscar como Filme
        try {
            String urlMovie = apiUrl + "/movie/" + id + "?language=pt-BR";
            ResponseEntity<FilmeDTO> response = restTemplate.exchange(urlMovie, HttpMethod.GET, entity, FilmeDTO.class);
            FilmeDTO result = response.getBody();
            if (result != null) {
                result.setTipoMidia("movie"); // Marca que é um filme
                return result;
            }
        } catch (Exception e) {
            // Se der erro (ex: 404 porque não é filme), ele ignora e passa para a tentativa 2
        }

        // Tentativa 2: Buscar como Série
        try {
            String urlTv = apiUrl + "/tv/" + id + "?language=pt-BR";
            ResponseEntity<FilmeDTO> responseTv = restTemplate.exchange(urlTv, HttpMethod.GET, entity, FilmeDTO.class);
            FilmeDTO resultTv = responseTv.getBody();
            if (resultTv != null) {
                resultTv.setTipoMidia("tv"); // Marca que é uma série
                return resultTv;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro: ID " + id + " não encontrado nem como Filme nem como Série no TMDB.");
        }

        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class TmdbResponse {
        @JsonProperty("results")
        private List<FilmeDTO> results;

        public List<FilmeDTO> getResults() { return results; }
        public void setResults(List<FilmeDTO> results) { this.results = results; }
    }
}