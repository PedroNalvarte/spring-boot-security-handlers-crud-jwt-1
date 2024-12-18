package com.example.crudv2.crudlogin.repository;

import com.example.crudv2.crudlogin.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    public List<Comentario> findByPublicacionId(long publicacionId);

}
