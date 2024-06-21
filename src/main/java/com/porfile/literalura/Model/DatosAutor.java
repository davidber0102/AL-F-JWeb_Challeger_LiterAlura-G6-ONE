package com.porfile.literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public record DatosAutor(
        @JsonAlias("name") String nombreAutor,
        @JsonAlias("birth_year") Integer anioNacimientoAutor,
        @JsonAlias("death_year") Integer anioFallecimientoAutor) {

    @Override
    public String toString() {
        return "Autor: " + nombreAutor;
    }
}
