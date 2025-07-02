package com.tahay3.literatura.repository;

import com.tahay3.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    //@Query("SELECT s FROM Libro s WHERE s.idiomas =:idioma")
    //List<Libro> listarLibrosPorIdioma(String idioma);

    List<Libro> findByIdiomas(String idioma);

    Optional<Libro> findByTituloIgnoreCase(String titulo);


}
