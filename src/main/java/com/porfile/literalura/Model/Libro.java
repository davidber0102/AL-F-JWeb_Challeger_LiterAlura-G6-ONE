package com.porfile.literalura.Model;

import jakarta.persistence.*;

import java.time.Year;

@Entity
@Table
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;
    private Integer anioNacimientoAutor;
    private Integer anioFallecimientoAutor;
    private String idioma;
    private Double descargasTotales;
    private String copyright;
   // private String topic;

   //Getter and Setters

    public Integer getAnioNacimientoAutor() {
        return anioNacimientoAutor;
    }

    public void setAnioNacimientoAutor(Integer anioNacimientoAutor) {
        this.anioNacimientoAutor = anioNacimientoAutor;
    }

    public Integer getAnioFallecimientoAutor() {
        return anioFallecimientoAutor;
    }

    public void setAnioFallecimientoAutor(Integer anioFallecimientoAutor) {
        this.anioFallecimientoAutor = anioFallecimientoAutor;
    }

    public Long getIdLibro() {return idLibro;}

    public void setIdLibro(Long idLibro) {this.idLibro = idLibro;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public Autor getAutor() {return autor;}

    public void setAutor(Autor autor) {this.autor = autor;}

   public String getIdioma() {return idioma;}

    public void setIdioma(String idioma) {this.idioma = idioma;}

    public Double getDescargasTotales() {return descargasTotales;}

    public void setDescargasTotales(Double descargasTotales) {
        this.descargasTotales = descargasTotales;}

    public String getCopyright() {return copyright;}

    public void setCopyright(String copyright) {this.copyright = copyright;}

    //public String getTopic() {return topic;}

    //public void setTopic(String topic) {this.topic = topic;}

    //Construcores
    public Libro(){ }

    public Libro (DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        Autor autor = new Autor(datosLibro.autores().get(0));
        this.autor = autor;
        this.idioma = datosLibro.idioma().get(0);
        this.descargasTotales = Double.valueOf(datosLibro.descargasTotales());
        this.copyright = datosLibro.copyright();
       //this.topic = datosLibro.topic();
    }

    public Libro(Autor autor, String titulo, String idioma, Integer descargasTotales, String copyright, String topic) {
        this.autor = autor;
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargasTotales = Double.valueOf(descargasTotales);
    }

    @Override
    public String toString() {
        return
                "Nombre del Libro='" + titulo + '\'' +
                ", Idioma del Libro='" + idioma + '\'' +
                ", Descargas Totales=" + descargasTotales +
                ", copyright='" + copyright + '\'' +
                "\n" +
               "----------------------------------------";
    }
}

