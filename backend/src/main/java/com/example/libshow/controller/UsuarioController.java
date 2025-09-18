package com.example.libshow.controller;

import com.example.libshow.model.Usuario;
import com.example.libshow.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repo;

    public UsuarioController(UsuarioRepository repo) {
        this.repo = repo;
    }

    // GET /usuarios -> lista todos
    @GetMapping
    public List<Usuario> list() {
        return repo.findAll();
    }

    // GET /usuarios/{id} -> busca por id
    @GetMapping("/{id}")
    public Usuario search(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    // POST /usuarios -> cria novo usuário
    @PostMapping
    public Usuario save(@RequestBody Usuario usuario) {
        return repo.save(usuario);
    }

    // PUT /usuarios/{id} -> atualiza usuário existente
    @PutMapping("/{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario usuario) {
        usuario.setIdUsuario(id); // garante que vai atualizar e não criar
        return repo.save(usuario);
    }

    // DELETE /usuarios/{id} -> remove usuário
    @DeleteMapping("/{id}")
    public void remove(@PathVariable String id) {
        repo.deleteById(id);
    }
}