package com.tahay3.literatura.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tahay3.literatura.model.DatosLibro;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaLibro(List<DatosLibro> results) {
}
