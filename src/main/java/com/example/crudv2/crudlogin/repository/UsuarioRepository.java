package com.example.crudv2.crudlogin.repository;

import com.example.crudv2.crudlogin.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByEmail(String email);
    public Optional<Usuario> findByUsername(String username);
    public Optional<Usuario> findByEmailOrUsername(String email, String username);

    public Boolean existsByUsername(String username);
    public Boolean existsByEmail(String email);
}

