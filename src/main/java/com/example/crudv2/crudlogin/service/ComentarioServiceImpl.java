package com.example.crudv2.crudlogin.service;

import com.example.crudv2.crudlogin.dto.ComentarioDTO;
import com.example.crudv2.crudlogin.entity.Comentario;
import com.example.crudv2.crudlogin.entity.Publicacion;
import com.example.crudv2.crudlogin.exceptions.BlogAppException;
import com.example.crudv2.crudlogin.exceptions.ResourceNotFoundException;
import com.example.crudv2.crudlogin.repository.ComentarioRepository;
import com.example.crudv2.crudlogin.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {

        Comentario comentario = MapEntity(comentarioDTO);

        Publicacion publicacion = publicacionRepository
                .findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = comentarioRepository.save(comentario);

        return mapDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId) {

        List<Comentario> comentarios = comentarioRepository.findByPublicacionId(publicacionId);

        return comentarios.stream().map(comentario -> mapDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {

        Publicacion publicacion = publicacionRepository
                .findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));


        Comentario comentario = comentarioRepository
                .findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");

        }

        return mapDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudDeComentario) {

        Publicacion publicacion = publicacionRepository
                .findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));


        Comentario comentario = comentarioRepository
                .findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");

        }

        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());

        Comentario comentarioRespuesta = comentarioRepository.save(comentario);

        return mapDTO(comentarioRespuesta);

    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {

        Publicacion publicacion = publicacionRepository
                .findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));


        Comentario comentario = comentarioRepository
                .findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");

        }

        comentarioRepository.delete(comentario);

    }

    //Funciones
    private ComentarioDTO mapDTO(Comentario comentario){

        ComentarioDTO comentarioDTO;

        return comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
    }

    private Comentario MapEntity(ComentarioDTO comentarioDTO){

        Comentario comentario;

        return comentario = modelMapper.map(comentarioDTO, Comentario.class);

    }

}
