package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    public List<UsuarioEntity> findAll();
    public void save(UsuarioEntity usuario);
    public UsuarioEntity findById(Long id);
    public void deleteById(Long usuario);
    public UsuarioEntity actualizarUsuarioEntity(UsuarioEntity usuario);
    public UsuarioEntity findByEmail(String email);
}
