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
        return realizarChamadaLista(url, null);
    }

    public List<FilmeDTO> buscarLista(String categoria, String tipoMidia) {
        String url;

        if ("awards".equals(categoria)) {
            int minVotes = "tv".equals(tipoMidia) ? 1000 : 5000;
            url = apiUrl + "/discover/" + tipoMidia + "?sort_by=vote_average.desc&vote_count.gte=" + minVotes + "&language=pt-BR";
        } else if ("upcoming".equals(categoria)) {
            // >>> NOVA LÓGICA: Exige 20 resultados do futuro direto do TMDB <<<
            String dataHoje = java.time.LocalDate.now().toString();
            String dataParam = "tv".equals(tipoMidia) ? "first_air_date.gte" : "primary_release_date.gte";

            // Usamos o /discover/ com a data de hoje, e ordenamos por popularidade para ter os melhores primeiro
            url = apiUrl + "/discover/" + tipoMidia + "?" + dataParam + "=" + dataHoje + "&sort_by=popularity.desc&language=pt-BR";
        } else {
            url = apiUrl + "/" + tipoMidia + "/" + categoria + "?language=pt-BR&region=BR";
        }

        return realizarChamadaLista(url, tipoMidia);
    }

    private List<FilmeDTO> realizarChamadaLista(String url, String tipoForcado) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<TmdbResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, TmdbResponse.class);
            List<FilmeDTO> resultados = response.getBody() != null ? response.getBody().getResults() : List.of();

            return resultados.stream().map(item -> {
                        if (tipoForcado != null) item.setTipoMidia(tipoForcado);
                        return item;
                    })
                    .filter(item -> "movie".equals(item.getTipoMidia()) || "tv".equals(item.getTipoMidia()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar lista no TMDB: " + e.getMessage());
        }
    }

    public FilmeDTO buscarPorId(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // >>> AQUI ADICIONEI O ,videos PARA ELE MANDAR OS TRAILERS <<<
            String urlMovie = apiUrl + "/movie/" + id + "?language=pt-BR&append_to_response=credits,videos";
            ResponseEntity<FilmeDTO> response = restTemplate.exchange(urlMovie, HttpMethod.GET, entity, FilmeDTO.class);
            if (response.getBody() != null) {
                response.getBody().setTipoMidia("movie");
                return response.getBody();
            }
        } catch (Exception e) {}

        try {
            // >>> AQUI ADICIONEI O ,videos PARA ELE MANDAR OS TRAILERS <<<
            String urlTv = apiUrl + "/tv/" + id + "?language=pt-BR&append_to_response=credits,videos";
            ResponseEntity<FilmeDTO> responseTv = restTemplate.exchange(urlTv, HttpMethod.GET, entity, FilmeDTO.class);
            if (responseTv.getBody() != null) {
                responseTv.getBody().setTipoMidia("tv");
                return responseTv.getBody();
            }
        } catch (Exception e) {
            throw new RuntimeException("ID não encontrado no TMDB.");
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