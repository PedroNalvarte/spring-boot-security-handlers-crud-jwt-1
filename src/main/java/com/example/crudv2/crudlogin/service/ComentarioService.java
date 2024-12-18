package com.example.crudv2.crudlogin.service;

import com.example.crudv2.crudlogin.dto.ComentarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComentarioService {

    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO);
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId);
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId);
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudDeComentario);
    public void eliminarComentario(Long publicacionId, Long comentarioId);
}
