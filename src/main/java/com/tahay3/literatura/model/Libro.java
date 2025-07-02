package com.tahay3.literatura.model;


import jakarta.persistence.*;

import java.util.Optional;
import java.util.OptionalDouble;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String idiomas;
    private Long descargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;


    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();

        // Crear el autor solo si hay al menos uno
        Autor autorObj = Optional.ofNullable(datosLibro.autores())
                .filter(a -> !a.isEmpty())
                .map(a -> {
                    DatosAutor datosAutor = a.get(0);
                    Autor nuevoAutor = new Autor();
                    nuevoAutor.setNombre(datosAutor.nombre());
                    nuevoAutor.setAnioNacimiento(datosAutor.anioNacimiento());
                    nuevoAutor.setAnioFallecimiento(datosAutor.anioFallecimiento());
                    return nuevoAutor;
                })
                .orElse(null); // si no hay autor, queda null

        this.autor = autorObj;

        this.idiomas = Optional.ofNullable(datosLibro.idiomas())
                .map(i -> String.join(", ", i))
                .orElse("N/A");

        this.descargas = Optional.ofNullable(datosLibro.descargas()).orElse(0L);
    }

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();
        this.autor = autor; // reutiliza el autor existente

        this.idiomas = Optional.ofNullable(datosLibro.idiomas())
                .map(i -> String.join(", ", i))
                .orElse("N/A");

        this.descargas = Optional.ofNullable(datosLibro.descargas()).orElse(0L);
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", idiomas='" + idiomas + '\'' +
                ", descargas=" + descargas
                ;
    }
}
