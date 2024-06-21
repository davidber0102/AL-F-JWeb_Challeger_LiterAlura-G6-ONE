package com.porfile.literalura.Model;

import jakarta.persistence.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor;
    @Column(nullable = false)
    private String nombreAutor;
    private Year anioNacimientoAutor;
    private Year anioFallecimientoAutor;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    //getters and setters
    public Long getIdAutor() {return idAutor;}

    public void setIdAutor(Long idAutor) {this.idAutor = idAutor;}

    public String getNombreAutor() {return nombreAutor;}

    public void setNombreAutor(String nombreAutor) {this.nombreAutor = nombreAutor;}

    public Year getAnioNacimientoAutor() {return anioNacimientoAutor;}

    public void setAnioNacimientoAutor(Year anioNacimientoAutor) {this.anioNacimientoAutor = anioNacimientoAutor;}

    public Year getAnioFallecimientoAutor() {return anioFallecimientoAutor;}

    public void setAnioFallecimientoAutor(Year anioFallecimientoAutor) {this.anioFallecimientoAutor = anioFallecimientoAutor;}

    public List<Libro> getLibros() {return libros;}

    public void setLibros(List<Libro> libros) {this.libros = libros;}

    //Constructoress
   public Autor (){ }

    public static boolean anio(Year anio) {       return anio != null && !anio.equals(Year.of(0));    }

    public Autor(DatosAutor datosAutor){
        this.nombreAutor = datosAutor.nombreAutor();
        this.anioNacimientoAutor = datosAutor.anioNacimientoAutor() != null ? Year.of(datosAutor.anioNacimientoAutor()) :null;
        this.anioFallecimientoAutor = datosAutor.anioFallecimientoAutor() != null ? Year.of(datosAutor.anioFallecimientoAutor()) :null;
    }

    public Autor(String nombreAutor, Year anioNacimientoAutor, Year anioFallecimientoAutor) {
        this.nombreAutor = nombreAutor;
        this.anioNacimientoAutor = anioNacimientoAutor;
        this.anioFallecimientoAutor = anioFallecimientoAutor;
    }

    @Override
    public String toString() {
        String anio1String = anioNacimientoAutor != null ? anioNacimientoAutor.toString(): "Sin Registro de Año";
        String anio2String = anioFallecimientoAutor != null ? anioFallecimientoAutor.toString(): "Sin Registro de Año";

        return
                " Nombre del Autor='" + nombreAutor + '\'' +
                "(Nacido en: " + anio1String +
                ", Fallecido en: " + anio2String  + ")";
    }
}
