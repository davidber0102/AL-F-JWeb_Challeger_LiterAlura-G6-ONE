package com.porfile.literalura.Repositorio;

import com.porfile.literalura.Model.Autor;
import com.porfile.literalura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) = LOWER(:titulo)")
    List<Libro> findByTitulo(String titulo);

    @Query("SELECT a FROM Autor a WHERE a.anioNacimientoAutor <= :ano AND (a.anioFallecimientoAutor IS NULL OR a.anioFallecimientoAutor >= :ano)")
    List<Autor> findAutoresVivos(@Param("ano") Year ano);

    @Query("SELECT a FROM Autor a WHERE a.anioNacimientoAutor = :ano AND (a.anioFallecimientoAutor IS NULL OR a.anioFallecimientoAutor >= :ano)")
    List<Autor> findAutoresVivosNac(@Param("ano") Year ano);

    @Query("SELECT a FROM Autor a WHERE a.anioNacimientoAutor <= :ano AND a.anioFallecimientoAutor = :ano")
    List<Autor> findAutoresMuerteAnio(@Param("ano") Year ano);

    @Query("SELECT l FROM Libro l WHERE l.idioma LIKE %:idioma%")
    List<Libro> findByIdioma(@Param("idioma") String idioma);

    @Query("SELECT l FROM Libro l ORDER BY l.descargasTotales DESC LIMIT 10")
    List<Libro> top10MasDescargados();

}
