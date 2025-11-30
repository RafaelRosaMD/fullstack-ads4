package com.senac.full.infra.usuario;

import com.senac.full.domain.usuario.Usuario;
import com.senac.full.domain.usuario.UsuarioRepository;
import com.senac.full.infra.usuario.jpa.SpringDataUsuarioRepository;
import com.senac.full.infra.usuario.jpa.UsuarioEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final SpringDataUsuarioRepository jpaRepo;

    public UsuarioRepositoryImpl(SpringDataUsuarioRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        var entity = UsuarioEntity.fromDomain(usuario);
        var salvo = jpaRepo.save(entity);
        return salvo.toDomain();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return jpaRepo.findById(id).map(UsuarioEntity::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpaRepo.findByEmail(email).map(UsuarioEntity::toDomain);
    }

    @Override
    public List<Usuario> listar() {
        return jpaRepo.findAll().stream()
                .map(UsuarioEntity::toDomain)
                .toList();
    }
}