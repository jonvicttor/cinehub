package br.com.jones.cinehub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora o lixo do JSON que a gente não vai usar
public class FilmeDTO {

    private Long id;

    // Campos de Filmes
    @JsonProperty("title")
    private String titulo;

    @JsonProperty("release_date")
    private String dataLancamento;

    // Campos de Séries (TV)
    @JsonProperty("name")
    private String nome;

    @JsonProperty("first_air_date")
    private String primeiraExibicao;

    // Campos Comuns
    @JsonProperty("overview")
    private String sinopse;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("media_type")
    private String tipoMidia; // Vai nos dizer se é "movie" (filme) ou "tv" (série)

    // --- MÉTODOS INTELIGENTES PARA O FRONT-END --- //

    // Devolve o título se for filme, ou o nome se for série
    public String getTituloExibicao() {
        return (titulo != null) ? titulo : nome;
    }

    // Devolve a data do filme, ou a data da série
    public String getDataExibicao() {
        return (dataLancamento != null) ? dataLancamento : primeiraExibicao;
    }

    // Método que gera o link completo da imagem do pôster
    public String getLinkPoster() {
        if (posterPath == null) return null;
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }

    // Getters e Setters Padrões
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }
    public String getDataLancamento() { return dataLancamento; }
    public void setDataLancamento(String dataLancamento) { this.dataLancamento = dataLancamento; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getPrimeiraExibicao() { return primeiraExibicao; }
    public void setPrimeiraExibicao(String primeiraExibicao) { this.primeiraExibicao = primeiraExibicao; }
    public String getTipoMidia() { return tipoMidia; }
    public void setTipoMidia(String tipoMidia) { this.tipoMidia = tipoMidia; }
}