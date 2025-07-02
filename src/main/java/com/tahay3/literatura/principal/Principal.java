package com.tahay3.literatura.principal;

import com.tahay3.literatura.model.Autor;
import com.tahay3.literatura.model.DatosAutor;
import com.tahay3.literatura.model.DatosLibro;
import com.tahay3.literatura.model.Libro;
import com.tahay3.literatura.repository.AutorRepository;
import com.tahay3.literatura.repository.LibroRepository;
import com.tahay3.literatura.service.ConsumoAPI;
import com.tahay3.literatura.service.ConvierteDatos;
import com.tahay3.literatura.service.RespuestaLibro;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);

    private final LibroRepository repositorioLibro;
    private final AutorRepository repositorioAutor;

    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_API = "https://gutendex.com/books/?search=";

    private ConvierteDatos conversor = new ConvierteDatos();

    private List<Libro> libros;
    private List<Autor> autores;


    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.repositorioLibro = libroRepository;
        this.repositorioAutor = autorRepository;
    }


    public void muestraElMenu(){
        var opcion = -1;
        while(opcion != 0) {
            var menu = """
                     1 - Buscar libro por titulo
                     2 - Listar libros registrados
                     3 - Listar autores registrados
                     4 - Listar autores vivos en un determinado año
                     5 - Listar libros por idiomas 
                     
                     0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    // opcion 1
    private void buscarSerieWeb(){
        DatosLibro datos = getDatosLibro();
        if (datos == null) {
            System.out.println("No se obtuvo ningún dato de libro.");
            return;
        }

        // Verificar si libro existe por título
        Optional<Libro> libroExistente = repositorioLibro.findByTituloIgnoreCase(datos.titulo());
        System.out.println("El libro existente dice que es: "+ libroExistente);
        if (libroExistente.isPresent()) {

            System.out.println("El libro con título '" + datos.titulo() + "' ya existe en la base de datos.");
            return; // no guardar
        }

        // Verificar si autor existe por nombre, para no crear duplicados
        Autor autor = null;
        if (datos.autores() != null && !datos.autores().isEmpty()) {
            String nombreAutor = datos.autores().get(0).nombre();
            Optional<Autor> autorExistente = repositorioAutor.findByNombreIgnoreCase(nombreAutor);
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            }else {
                // Crear y guardar nuevo autor porque no existe
                DatosAutor datosAutor = datos.autores().get(0);
                Autor nuevoAutor = new Autor();
                nuevoAutor.setNombre(datosAutor.nombre());
                nuevoAutor.setAnioNacimiento(datosAutor.anioNacimiento());
                nuevoAutor.setAnioFallecimiento(datosAutor.anioFallecimiento());
                autor = repositorioAutor.save(nuevoAutor);
            }
        }

        Libro libro = new Libro(datos, autor); // usar autor ya existente o recién creado
        System.out.println("DatosLibro recibido: ");
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autores: " + libro.getAutor());
        System.out.println("Idiomas: " + libro.getIdiomas());
        System.out.println("Descargas: " + libro.getDescargas());
        repositorioLibro.save(libro);
        System.out.println("Libro guardado correctamente: " + libro.getTitulo());
    }


    // parte de opcion 1
    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro a buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_API + nombreLibro.replace(" ", "+"));
        System.out.println(json);

        RespuestaLibro respuesta = conversor.obtenerDatos(json, RespuestaLibro.class);

        if (respuesta.results() == null || respuesta.results().isEmpty()) {
            System.out.println("No se encontró ningún libro.");
            return null;
        }

        return respuesta.results().get(0); // el primer libro encontrado
    }


    //opcion 2
    private void mostrarLibrosBuscados(){
        libros = repositorioLibro.findAll();

        libros.stream()
                .forEach(System.out::println);
    }

    //opcion 3
    private void mostrarAutoresRegistrados(){
        autores = repositorioAutor.findAll();

        autores.stream()
                .forEach(System.out::println);
    }

    //opcion 4
    private void autoresVivosAnio(){
        System.out.println("Escribe el año para buscar autores vivos");
        try {
            String anioString = teclado.nextLine();
            int anio = Integer.parseInt(anioString);
            List<Autor> autoresAnioVivos = repositorioAutor.findByAnioFallecimientoGreaterThan(anio);

            if(autoresAnioVivos == null || autoresAnioVivos.isEmpty())
            {
                System.out.println("No se encontró ningún autor vivo en el año: "+ anio);
            }
            else{
                System.out.println("autores vivos en el año: "+ anio);
                autoresAnioVivos.forEach(s -> System.out.println(s.getNombre()));
            }

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Debes ingresar un número entero válido. Inténtalo de nuevo.");
        }


    }

    //opcion 5
    private void listarLibrosPorIdioma(){
        System.out.println("Escribe el idioma para fitrar libros");
        var idioma = teclado.nextLine();

        List<Libro> filtroLibros = repositorioLibro.findByIdiomas(idioma);

        if(filtroLibros == null || filtroLibros.isEmpty())
        {
            System.out.println("No se encontró ningún libro registrado con el idioma: "+ idioma);
        }
        else{
            System.out.println("*** Libros filtrados para el idioma: "+ idioma);
            filtroLibros.forEach(s ->
                    System.out.println(s.getTitulo() + "  - idioma: " + s.getIdiomas()));
        }
    }
}
