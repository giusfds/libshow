package com.example.libshow.service;

import com.example.libshow.config.SenhaUtils;
import com.example.libshow.model.Usuario;
import com.example.libshow.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario createUser(Usuario user) {
        if (!isPasswordValid(user.getSenha())) {
            throw new IllegalArgumentException("Senha inválida");
        }

        String senhaCryptographic = SenhaUtils.encode(user.getSenha());
        user.setSenha(senhaCryptographic);

        return repo.save(user);
    }

    private boolean isPasswordValid (String senha){
        return senha != null && senha.length() >= 8;
    }

}
