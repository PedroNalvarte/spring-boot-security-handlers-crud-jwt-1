package com.example.crudv2.crudlogin.service;

import com.example.crudv2.crudlogin.dto.PublicacionDTO;
import com.example.crudv2.crudlogin.dto.PublicacionRespuestaDTO;
import com.example.crudv2.crudlogin.entity.Publicacion;
import com.example.crudv2.crudlogin.exceptions.ResourceNotFoundException;
import com.example.crudv2.crudlogin.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {

        Publicacion publicacion = mapToEntity(publicacionDTO);
        Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);
        PublicacionDTO publicacionDTORespuesta = mapToDTO(nuevaPublicacion);

        return publicacionDTORespuesta;
    }

    @Override
    public PublicacionRespuestaDTO obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Publicacion> publicaciones = publicacionRepository.findAll(pageable);

        List<Publicacion> listaDePublicaciones = publicaciones.getContent();
        List<PublicacionDTO> contenido =  listaDePublicaciones.stream().map(publicacion -> mapToDTO(publicacion)).collect(Collectors.toList());

        PublicacionRespuestaDTO publicacionRespuestaDTO = new PublicacionRespuestaDTO();
        publicacionRespuestaDTO.setContenido(contenido);
        publicacionRespuestaDTO.setNumeroPagina(publicaciones.getNumber());
        publicacionRespuestaDTO.setMedidaPagina(publicaciones.getSize());
        publicacionRespuestaDTO.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuestaDTO.setTotalPaginas(publicaciones.getTotalPages());
        publicacionRespuestaDTO.setUltima(publicaciones.isLast());

        return publicacionRespuestaDTO;
    }

    @Override
    public PublicacionDTO obtenerPublicacionPorId(long id) {

        Publicacion publicacion = publicacionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        return mapToDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {

        Publicacion publicacion = publicacionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        publicacion.setTitulo(publicacionDTO.getContenido());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        Publicacion publicacionActualizada = publicacionRepository.save(publicacion);

        return mapToDTO(publicacionActualizada);
    }

    @Override
    public void eliminarPublicacion(long id) {
        Publicacion publicacion = publicacionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        publicacionRepository.delete(publicacion);
    }

    //Utiles

    private PublicacionDTO mapToDTO(Publicacion publicacion){

        PublicacionDTO publicacionDTO;
        return publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);

    }

    private Publicacion mapToEntity(PublicacionDTO publicacionDTO){

         Publicacion publicacion;
         return publicacion = modelMapper.map(publicacionDTO, Publicacion.class);

    }
}
