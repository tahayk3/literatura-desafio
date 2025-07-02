package com.tahay3.literatura.repository;

import com.tahay3.literatura.model.Autor;
import com.tahay3.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    //@Query("SELECT s FROM Autor s WHERE s.anioFallecimiento > :anio")
    //List<Autor> autoresAnioVivos(String anio);
    List<Autor> findByAnioFallecimientoGreaterThan(Integer anio);



    Optional<Autor> findByNombreIgnoreCase(String nombre);
}

