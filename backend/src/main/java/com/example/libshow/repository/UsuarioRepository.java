package com.example.libshow.repository;

import com.example.libshow.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByNome(String nome);
}
