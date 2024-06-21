package com.porfile.literalura.Principal;

import com.fasterxml.jackson.databind.JsonNode;
import com.porfile.literalura.Model.Autor;
import com.porfile.literalura.Model.DatosLibro;
import com.porfile.literalura.Model.Libro;
import com.porfile.literalura.Repositorio.LibroRepository;
import com.porfile.literalura.Service.ApiGutendex;
import com.porfile.literalura.Service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.DoubleSummaryStatistics;



@Component
public class Principal {
    @Autowired
    private LibroRepository repositorio;
    @Autowired
    private ApiGutendex consumoApi;
    @Autowired
    private ConvierteDatos conversor;
    private final Scanner teclado = new Scanner(System.in);

    private final String URL_BASE = "https://gutendex.com/books?";
    private final String busquedaLibro = "search=";
    private final String palabraClave = "topic=";
    private final String idioma = "languages=";
    private final String derechos = "copyright=";
    private final String año1 = "author_year_start=";
    private final String año2 = "author_year_end=";

    private List<Libro> librosMuestra;
    private Integer anio;
    private Year year;
    private List<Autor> autoresMuestra;

    public Principal(LibroRepository repository, ApiGutendex consumoApi, ConvierteDatos conversor) {
        this.repositorio = repository;
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }// FIN DE PRINCIPAL

    public void muestraElMenu() {
        boolean correr = true;
        while (correr) {
            mensajeBienvenida();
            Menu();
            var opcion = teclado.nextInt();
            teclado.nextLine();
            //LISTADO DE METODOS
            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivos();
                case 5 -> listarAutoresVivosNacimiento();
                case 6 -> listarAutoresAnioMuerte();
                case 7 -> listarLibrosPorIdioma();
                case 8 -> TopLibrosMasDescargados();
                case 9 -> estadisiticasLibro();
                //case 9 -> BuscarAutorPorNombre();
                case 0 -> {
                    System.out.println("Encerrando a Liter-Alura!");
                    correr = false;
                }//FIN CASO 0
                default -> System.out.println("Opcion inválida!");
            }//FIN DE SWITCH
        }//FIN DE WHILE
    }//FIN DE MUESTRA EL MENU



    // METODO DE MENSAJE DE BIEVENIDA
    private void mensajeBienvenida() {
        System.out.println("""
                ******************************************************************
                ******************************************************************          
                -----------Bienvenido al Challenger Liter-Alura-----------
                -------------------------------------------------------------
                --------Codificado por: Bernal Diaz David---------------
                -----------------------------------------------------------
                ---------------Curso Back-Emd ALURA-ORACLE----------------- 
                ******************************************************************
                ******************************************************************
                    """);
    }//FIN DE MENSAJE DE BIENVENIDA

    // METODO DE MUESTRA EL MENU
    private void Menu() {
        System.out.println("""
                 ******************************************************************
                 ******************************************************************
                 ----------------   CHALLENGER LITERALURA   -----------------------
                        Aplicacion generada en base de esferzos !
                        Puede elegir cualquier opcion del siguiente Menu:
                ******************************************************************
                                      Menu
                     -----------------------------------------------------------
                            1- Buscar libro por titulo
                            2- Listar libros registrados
                            3- Listar autores registrados
                            4- Listar autores vivos en un año determinado
                            5- Listar autores nacidos en un año determindo
                            6- Listar autores por año de su Muerte
                            7- Listar libros en un determinado Idioma
                            8- Top 10 libros más descargados
                            9- Estadisiticas del Libro
                            0- Salir
                     -----------------------------------------------------------
                 ******************************************************************
                 ******************************************************************
                 """);
    }// FIN DE MENU

    private void guardarLibros(List<Libro> libros) {        libros.forEach(repositorio::save);    }

    private void buscarLibroPorTitulo() {
        try {
            //TECLE EL NOMBRE DEL LIBRO A BUSCAR
            System.out.println("Opcion 1- Buscar libro por titulo");
            System.out.println("Escribe el titulo del Libro que deseas buscar: ");
            String nombreLibro = teclado.nextLine();
            //SE GUARDA LA LIGA GENERADA PARA CONSUTAR LA API
            String url = URL_BASE + busquedaLibro + nombreLibro.replace(" ", "%20");
            System.out.println("Liga de la API: " + url);
            //SE MUESTRAN LOS DATOS DEL REGRESO DE A LA API
            String jsonResponse = consumoApi.datosAPI(url);
            System.out.println("Regreso de API: " + jsonResponse);
            //SE VALIDA POR SI LOS DATOS QUE REGRES ALA API ESTAN VACIOS
            if (jsonResponse.isEmpty()) {
                System.out.println("Liga de API VACIA");
                return;
            }
            //Extraer lista de libros
            JsonNode nodeResponse = conversor.getObjectMapper().readTree(jsonResponse);
            JsonNode nodeResult = nodeResponse.path("results");
            // SE VALIDA POR SI EL RESULTADO NO ES ENCONTRADO
            if (nodeResult.isEmpty()) {
                System.out.println("No se pudo encontrar el libro requerido");
                return;            }
            //Convertir resultado de API EN objeto de DatosLibro
            List<DatosLibro> librostest = conversor.getObjectMapper()
                    .readerForListOf(DatosLibro.class).readValue(nodeResult);
            //quitar duplicados de bbdd
            List<Libro> librosExistentes = repositorio.findByTitulo(nombreLibro);
            if (!librosExistentes.isEmpty()) {
                System.out.println("quitando libros existentes en la base de datos......");
                for (Libro libroExistente : librosExistentes) {
                    librostest.removeIf(DatosLibro -> libroExistente.getTitulo().equals(DatosLibro.titulo()));                }
            }
            //guardando
            if (!librostest.isEmpty()) {
                System.out.println("Guardando libros nuevos encontrados.....");
                List<Libro> librosNuevos = librostest.stream().map(Libro::new).collect(Collectors.toList());
                guardarLibros(librosNuevos);
                System.out.println("Terminado de guardar Libros nuevos");
            } else {                System.out.println("Libro buscado esta registrado en la Base de Datos ");            }
            //mostrar libros encontrados
            if (!librostest.isEmpty()) {
                System.out.println("Libros Encontrados");
                Set<String> librosExibidos = new HashSet<>();
                for (DatosLibro libro : librostest) {  if (!librosExibidos.contains(libro.titulo())) ;                }
            }
        } catch (Exception e) {            System.out.println("Error al buscar el nombre del Libro..." + e.getMessage());        }
    }//fin de BUSCR LIBRO POR TITULO

    //METODO PARA BUSCAR LIBROS REGISTRADOS EN LA BASE DE DATOS
    private void listarLibrosRegistrados() {
        librosMuestra = repositorio.findAll();
        if (librosMuestra.isEmpty()) {            System.out.println("NO hay libros registrados");
        } else {            librosMuestra.forEach(System.out::println);        }
    }//fin de metodo lsiatr libros egistrados
    //METODO PARA LISTAR AUTORES REGISTRADOS EN LA BASE DE DATOS
    private void listarAutoresRegistrados() {
        librosMuestra = repositorio.findAll();
        if (librosMuestra.isEmpty()) {            System.out.println("NO hay libros registrados");
        } else {
            librosMuestra.stream().map(Libro::getAutor)
                    .distinct().forEach(autor -> System.out.println(autor.getNombreAutor()));
                }
    }// fin de autres registrados
    private void listarAutoresVivos() {
        System.out.println("Opcion 4-Listar autores vivos en un año determinado");
        System.out.println("Introduce el año que desea validar: ");
        //se guara en numero digitado
        anio = teclado.nextInt();
        teclado.nextLine();
        //se convierte a objeto año
        year = Year.of(anio);

        autoresMuestra = repositorio.findAutoresVivos(year);
         //validar la lista de autoresmuestra
        if (autoresMuestra.isEmpty()){System.out.println("NO hay autores registrados vivos en el año " + year);
        }  else {
            System.out.println("Autores registrados vivos en el año " + year + ": \n");
            autoresMuestra.forEach(autor -> {
                if (Autor.anio(autor.getAnioNacimientoAutor()) && Autor.anio(autor.getAnioFallecimientoAutor())){
                    String nomAutor = autor.getNombreAutor();
                    String nacimientoAutor = autor.getAnioNacimientoAutor().toString();
                    String fallecimientoAutor = autor.getAnioFallecimientoAutor().toString();
                    System.out.println("Autor: " + nomAutor + "(Nacido: " + nacimientoAutor + "-"
                            + "Muerto: " + fallecimientoAutor + ")");                }//fin if
            });
        }//fin else
    }//fin lista autores vivos

    private void listarAutoresVivosNacimiento() {
        System.out.println("Opcion 5 - Listar autores nacidos en un año determindo");
        System.out.println("Introduce el año que desea validar: ");
        //se guara en numero digitado
        anio = teclado.nextInt();
        teclado.nextLine();
        //se convierte a objeto año
        year = Year.of(anio);
        autoresMuestra= repositorio.findAutoresVivosNac(year);
        //validar dato de año
        if (autoresMuestra.isEmpty()){System.out.println("NO hay autores registrados vivos en el año " + year);
        }  else {
            System.out.println("Autores registrados nacidos en el año " + year + ": \n");
            autoresMuestra.forEach(autor -> {
                if (Autor.anio(autor.getAnioNacimientoAutor()) && Autor.anio(autor.getAnioFallecimientoAutor())){
                    String nomAutor = autor.getNombreAutor();
                    String nacimientoAutor = autor.getAnioNacimientoAutor().toString();
                    String fallecimientoAutor = autor.getAnioFallecimientoAutor().toString();
                    System.out.println("Autor: " + nomAutor + "(Nacido: " + nacimientoAutor +  ")");                }//fin if
            });
        }//fin else
    }// fin de lsita de autores nacidos

    private void listarAutoresAnioMuerte() {
        System.out.println("Opcion 6 - Listar autores por año de su Muerte");
        System.out.println("Introduce el año que desea validar: ");
        //se guara en numero digitado
        anio = teclado.nextInt();
        teclado.nextLine();
        //se convierte a objeto año
        year = Year.of(anio);
        autoresMuestra= repositorio.findAutoresMuerteAnio(year);
        //validar dato de año
        if (autoresMuestra.isEmpty()){System.out.println("NO hay autores registrados vivos en el año " + year);
        }  else {
            System.out.println("Autores registrados muerieron en el año de:  " + year + ": \n");
            autoresMuestra.forEach(autor -> {
                if (Autor.anio(autor.getAnioNacimientoAutor()) && Autor.anio(autor.getAnioFallecimientoAutor())){
                    String nomAutor = autor.getNombreAutor();
                    String nacimientoAutor = autor.getAnioNacimientoAutor().toString();
                    String fallecimientoAutor = autor.getAnioFallecimientoAutor().toString();
                    System.out.println("Autor: " + nomAutor + "(Muerto: " + fallecimientoAutor +  ")");                }//fin if
            });
        }//fin else
    }//fin de autores por año de muerte

    private void listarLibrosPorIdioma() {
        System.out.println("Opcion 7 - Listar libros en un determinado Idioma");
        System.out.println("""
                Introduce el idioma a buscar
                Ingles(en)
                Español(es)
                Portuges(pt)
                Frances(fr)                
                """);
        String idioma = teclado.nextLine();

        librosMuestra = repositorio.findByIdioma(idioma);

        if(librosMuestra.isEmpty()) {
            System.out.println("No se encontro ningun Idioma.");
        }else{
            librosMuestra.forEach(libro -> {
                String tit = libro.getTitulo();
                String autor = libro.getAutor().getNombreAutor();
                String idiomaLibro = libro.getIdioma();

                System.out.println("Titulo:"+ tit);
                System.out.println("Autor:" + autor);
                System.out.println ("Idioma:" + idiomaLibro);
                System.out.println("----------------------------------------");
            });
        }
    }//fin metodo listar libros por idioma
    private void TopLibrosMasDescargados() {
        System.out.println("Opcion 8 - Top 10 libros más descargados");

        librosMuestra = repositorio.top10MasDescargados();

        if(librosMuestra.isEmpty()) {
            System.out.println("No se encontro ningun Idioma.");
        }else{
            librosMuestra.forEach(libro -> System.out.println("Nombre de Libro: "
            + libro.getTitulo() + " ( Descargas Totales: " + libro.getDescargasTotales() + " )"));
        }
    }// fin de metodo top libros mas descargados

    //Top 10 libros mas descaragados consulta a api
    /*private void TopLibrosMasDescargados2() {
        System.out.println("Opcion 8 - Top 10 libros más descargados");


        List<DatosLibro> datosLibros = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
        System.out.println("Top 5 Episodios");
        datosLibros.stream()
                .filter(e -> !e.evaluaciones().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primer Filtro sin (N/A) " + e))
                .sorted(Comparator.comparing(DatosEpisodio::evaluaciones).reversed())
                .peek(e -> System.out.println(", Segundo Filtro Ordenacion descendente " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println(", Tercer Filtro MAYUSCULAS " + e))
                .limit(5)
                .peek(e -> System.out.println(", Cuarto Filtro limitado a 5 " + e))
                .forEach(System.out::println);

    }// fin de metodo top libros mas descargados 2*/

    /*
    public void estadisiticasLibro() {
        librosMuestra = librosMuestra.stream().flatMap(libros -> libros.getTitulo()
                .map(t -> new Libro(t.)));
        DoubleSummaryStatistics est = librosMuestra.stream()
                .filter(e -> e.getDescargasTotales() > 0.0 && e.getDescargasTotales() != null)
                .collect(Collectors.summarizingDouble(Libro::getDescargasTotales));

        System.out.println("-----Media de las evaluaciones; " + est.getAverage() +
                ", \n Episodio Mejor Evaluado:" + est.getMax() +
                ", \n Episodio Peor Evaluado:" + est.getMin());
    }// fin de metodo de estadisticas*/
}//fin de clase Princial

