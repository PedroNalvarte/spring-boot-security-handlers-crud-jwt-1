package com.example.crudv2.crudlogin.service;

import com.example.crudv2.crudlogin.dto.PublicacionDTO;
import com.example.crudv2.crudlogin.dto.PublicacionRespuestaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PublicacionService{

    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
    public PublicacionRespuestaDTO obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);
    public PublicacionDTO obtenerPublicacionPorId(long id);
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id);
    public void eliminarPublicacion(long id);
}
