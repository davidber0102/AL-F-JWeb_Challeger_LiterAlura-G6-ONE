package com.porfile.literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("download_count") Double descargasTotales,
        @JsonAlias("copyright") String copyright,
        //@JsonAlias("subjects") String topic,
        @JsonAlias("authors") List<DatosAutor> autores,
         @JsonAlias("languages") List<String> idioma


) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Autor(es): \n");
        for (DatosAutor autor : autores) {
            sb.append("  - ").append(autor.nombreAutor()).append("\n");
        }
        sb.append("Idioma(s): ").append(String.join(", ", idioma)).append("\n");
        sb.append("Downloads: ").append(descargasTotales).append("\n");
        sb.append("----------------------------------------");
        return sb.toString();
    }
}
