package com.tahay3.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonProperty("title") String titulo,
        @JsonProperty("authors") List<DatosAutor> autores,
        @JsonProperty("languages") List<String> idiomas,
        @JsonProperty("download_count") Long descargas)
{ }
