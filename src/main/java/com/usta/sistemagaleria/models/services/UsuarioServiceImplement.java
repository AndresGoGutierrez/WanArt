package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.UsuarioEntity;
import com.usta.sistemagaleria.models.DAO.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class UsuarioServiceImplement implements UsuarioService {
    @Autowired
    private UsuarioDAO UsuarioDAO;

    @Override
    @Transactional
    public List<UsuarioEntity> findAll() {
        return (List<UsuarioEntity>) UsuarioDAO.findAll();
    }

    @Override
    @Transactional
    public void save(UsuarioEntity usuario) {
        UsuarioDAO.save(usuario);
    }

    @Override
    @Transactional
    public UsuarioEntity findById(Long id) {
        return UsuarioDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long usuario) {
        UsuarioDAO.deleteById(usuario);
    }

    @Override
    @Transactional
    public UsuarioEntity actualizarUsuarioEntity(UsuarioEntity usuario) {
        return UsuarioDAO.save(usuario);
    }

    @Override
    @Transactional
    public UsuarioEntity findByEmail(String email) {
        return UsuarioDAO.findByEmail(email);
    }
}
