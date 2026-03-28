package br.com.jones.cinehub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmeDTO {

    private Long id;

    @JsonProperty("title")
    private String titulo;

    @JsonProperty("release_date")
    private String dataLancamento;

    @JsonProperty("name")
    private String nome;

    @JsonProperty("first_air_date")
    private String primeiraExibicao;

    @JsonProperty("overview")
    private String sinopse;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("media_type")
    private String tipoMidia;

    @JsonProperty("vote_average")
    private Double notaTmdb;

    @JsonProperty("tagline")
    private String tagline;

    @JsonProperty("runtime")
    private Integer runtime;

    @JsonProperty("genres")
    private List<GeneroDTO> generos;

    @JsonProperty("credits")
    private CreditsDTO credits;

    // >>> NOVO CAMPO: MAPEANDO OS VÍDEOS <<<
    @JsonProperty("videos")
    private VideosResponseDTO videos;

    // --- MÉTODOS INTELIGENTES --- //

    public String getTituloExibicao() {
        return (titulo != null) ? titulo : nome;
    }

    public String getDataExibicao() {
        return (dataLancamento != null) ? dataLancamento : primeiraExibicao;
    }

    public String getLinkPoster() {
        if (posterPath == null) return null;
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }

    public String getLinkBackdrop() {
        if (backdropPath == null) return null;
        return "https://image.tmdb.org/t/p/original" + backdropPath;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }
    public String getBackdropPath() { return backdropPath; }
    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }
    public String getDataLancamento() { return dataLancamento; }
    public void setDataLancamento(String dataLancamento) { this.dataLancamento = dataLancamento; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getPrimeiraExibicao() { return primeiraExibicao; }
    public void setPrimeiraExibicao(String primeiraExibicao) { this.primeiraExibicao = primeiraExibicao; }
    public String getTipoMidia() { return tipoMidia; }
    public void setTipoMidia(String tipoMidia) { this.tipoMidia = tipoMidia; }
    public Double getNotaTmdb() { return notaTmdb; }
    public void setNotaTmdb(Double notaTmdb) { this.notaTmdb = notaTmdb; }
    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }
    public Integer getRuntime() { return runtime; }
    public void setRuntime(Integer runtime) { this.runtime = runtime; }
    public List<GeneroDTO> getGeneros() { return generos; }
    public void setGeneros(List<GeneroDTO> generos) { this.generos = generos; }
    public CreditsDTO getCredits() { return credits; }
    public void setCredits(CreditsDTO credits) { this.credits = credits; }
    public VideosResponseDTO getVideos() { return videos; }
    public void setVideos(VideosResponseDTO videos) { this.videos = videos; }

    // --- CLASSES INTERNAS MUDADAS PARA PUBLIC STATIC --- //

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeneroDTO {
        @JsonProperty("name")
        private String nome;
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CreditsDTO {
        @JsonProperty("cast")
        private List<CastDTO> cast;
        @JsonProperty("crew")
        private List<CrewDTO> crew;
        public List<CastDTO> getCast() { return cast; }
        public void setCast(List<CastDTO> cast) { this.cast = cast; }
        public List<CrewDTO> getCrew() { return crew; }
        public void setCrew(List<CrewDTO> crew) { this.crew = crew; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CastDTO {
        @JsonProperty("name")
        private String nome;
        @JsonProperty("character")
        private String personagem;
        @JsonProperty("profile_path")
        private String profilePath;

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getPersonagem() { return personagem; }
        public void setPersonagem(String personagem) { this.personagem = personagem; }
        public String getProfilePath() { return profilePath; }
        public void setProfilePath(String profilePath) { this.profilePath = profilePath; }

        public String getLinkFoto() {
            if (profilePath == null) return null;
            return "https://image.tmdb.org/t/p/w185" + profilePath;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CrewDTO {
        @JsonProperty("name")
        private String nome;
        @JsonProperty("job")
        private String cargo;
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getCargo() { return cargo; }
        public void setCargo(String cargo) { this.cargo = cargo; }
    }

    // --- CLASSES INTERNAS PARA O TRAILER (VIDEOS) --- //
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideosResponseDTO {
        @JsonProperty("results")
        private List<VideoDTO> results;
        public List<VideoDTO> getResults() { return results; }
        public void setResults(List<VideoDTO> results) { this.results = results; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoDTO {
        @JsonProperty("key")
        private String key; // A chave do vídeo no YouTube (ex: dQw4w9WgXcQ)
        @JsonProperty("type")
        private String type; // Se é Trailer, Teaser, Featurette, etc
        @JsonProperty("site")
        private String site; // YouTube, Vimeo, etc

        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getSite() { return site; }
        public void setSite(String site) { this.site = site; }
    }
}