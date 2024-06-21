# 001-Challeger LiterAlura ALURA -ONE
## Elaborado por: David Bernal Diaz
##
### Curso Practicando Spring Boot: Challenge Literalura
### Instructor Eric Monné Fraga de Oliveira
##

Descpricion: 
Liter-Alura  surge como aplicación Java/Spring Boot para la busqueda y almacenamiento de los libros mediante tecnologias de API. De maner general, la aplicacion en Linea de Comandos
se puede interactuar con libros y algunas caracteristicas.
 <p align="left">
   <img src="https://img.shields.io/badge/STATUS-EN%20DESAROLLO-green">
  
![GitHub](https://img.shields.io/github/license/dropbox/dropbox-sdk-java)
![Maven Central](https://img.shields.io/maven-central/v/com.dropbox.core/dropbox-core-sdk)
   </p>
## Menu de Metodos
1. **Buscar libro por titulo**: Consulta el API  Gutendex para la busqueda de Libros.
2. **Listar libros registrados**: Muesta en pantala los libros buscados en el paso anterior y almacenados en la BBDD.
3. **Listar autores registrados**: Muesta en pantala los autres almacenados en la BBDD.
4. **Listar autores vivos en un año determinado**: Muestra la lista de autores que estuvieron vivos en un año determinado.
5. **Listar autores nacidos en un año determindo**: Muestra la lista de autores que nacieron en un año determinado.
6. **Listar autores por año de su Muerte**: Muestra la lista de autores que muerieron en un año determinado.
7. **Listar libros en un determinado Idioma**: Muestra la lista de Libros registrados en la BBDD en un idioma especifico.
8. **Top 10 libros más descargados**: Muestra Estadisticas de los 10 libros mas descargados
9. **Buscar autor por nombre**: Consulta el nombre de autor.
0. **Salir**: Opcion para Salir de la Applicacion.

## Teconologas Utilizadas

- **Java 17**
- **IntelliJ IDEA**
- **Java Orientada a Objectos**
- **Spring Boot 2.7**
- **Hibernate**
- **PostgreSQL**
- **Gutendex API**
- **Maven**

## Configuraciones para utilizar el proyecto
### Requisitos previos - instalacion:
 - Java 17
 - Mave
 - PostgreSQL

### Repositorio.
Debes Clonar el repositorio creado con el siguiente comando dentro de la carpeta que desees:
```bash
   git clone (https://github.com/davidber0102/001-Challeger_LiterAlura.git)
   ```
### Propiedades de Base de DAtos.
Debes modificar las variables de entorno para alamcenar
- direccion de la base de datos: ${DB_HOST}
- nombre de base de datos: literalura o ${DB_nombre}
- usuario con el que aceddes a Postgres
- por ultimo la contraseña para acceder a postgres
```properties
    spring.datasource.url= jdbc:postgresql://${DB_HOST}/literalura
    spring.datasource.username= ${DB_USER}
    spring.datasource.password=sua-${DB_PASSWORD}
    spring.datasource.driver-class-name = org.postgresql.Driver
    hibernate.dialect = org.hibernate.dialect.HSQLDialect
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.generate-ddl=true
    spring.jpa.hibernate.ddl-auto = update
    spring.jpa.show-sql=true
    spring.jpa.format-sql=true
    server.port = 8090
   ```
-ya podrias ejecutar el proyecto de manera correcta.

## Administracion de Paquetes
 -`.com.porfile.literalura`: Clase Principal para ejecutar el programa
  - `principal`: Contiene la clase `Princiapl`, en el cual se encuentran todos los metodos programados
  - `model`: Contiene las clases creadas para la aplicaicon (`Libro`, `Autor`, `DatosLibro`, `DAtosAutor`).
  - `repository`: Contiene la clase interfaz para crear un repositorio con Spring Data JPA.
  - `service`: Contiene las clases para usar su servicio  (`ApiGutendex`, `ConvierteDados`).


## Uso de la Aplicacion
Al moento de ejecutar de manera correcta dentro de **IntelliJ IDEA**, dentro de la consola del programa sale un menu con 10 Opciones:
- ### Opcion 1 **Buscar libro por titulo**
      - Cuando Digite 1 y presione Enter.
      - Podra buscar el libro o parte del nombre del libro.
      - La aplicacion utilizara el api y alamacenara libros y autores.
- ### Opcion 2 **Listar libros registrados**
      - Cuando Digite 2 y presione Enter.
      - Listara el total de libros almacenados en la BBDD en la Opcion 1.
- ### Opcion 3  **Listar autores registrados**
      - Cuando Digite 3 y presione Enter.
      - Listara el total de autores almacenados en la BBDD en la Opcion 1.
- ### Opcion 4 **Listar autores vivos en un año determinado**
      - Cuando Digite 4 y presione Enter.
      - Debera Digital el año a buscar
      - Listara el total de autores vivos en año determinado almacenados en la BBDD en la Opcion 1.
- ### Opcion 5 **Listar autores nacidos en un año determindo**
      - Cuando Digite 5 y presione Enter.
      - Debera Digital el año a buscar
      - Listara el total de autores nacidos en año determinado almacenados en la BBDD en la Opcion 1.
- ### Opcion 6 **Listar autores por año de su Muerte**
      - Cuando Digite 6 y presione Enter.
      - Debera Digital el año a buscar.
      - Listara el total de autores muertos en año determinado almacenados en la BBDD en la Opcion 1.
- ### Opcion 7 **Listar libros en un determinado Idioma**
      - Cuando Digite 7 y presione Enter.
      - Debera Digital el idioma a buscar.
      - Listara el total de autores en un determinado idioma almacenados en la BBDD en la Opcion 1. 
- ### Opcion 8 **Top 10 libros más descargados**
      - Cuando Digite 8 y presione Enter.
- ### Opcion 9 **Buscar autor por nombre**
      - Cuando Digite 9 y presione Enter.
- ### Opcion 0 **Salir**
      - Cuando Digite 0 y presione Enter.
      - Debera Digital el idioma a buscar.

## Licencia
- **Proyecto con Licencia MIT**
 ```bash
   [MIT-LICENSE.txt](https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt)
   ```

## Contacto
**Si cuenta con una aportacion o dada, puede contacarme.**
