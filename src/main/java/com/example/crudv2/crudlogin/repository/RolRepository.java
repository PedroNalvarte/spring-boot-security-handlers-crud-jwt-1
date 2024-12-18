package com.example.crudv2.crudlogin.repository;

import com.example.crudv2.crudlogin.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    public Optional<Rol> findByNombre(String nombre);


}
