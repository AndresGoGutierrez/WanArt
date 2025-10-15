package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.ComentarioEntity;
import com.usta.sistemagaleria.models.DAO.ComentarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service

public class ComentarioServiceImplement implements ComentarioService {
    @Autowired
    private ComentarioDAO ComentarioDao;
    @Autowired
    private ComentarioDAO comentarioDAO;


    @Override
    @Transactional
    public List<ComentarioEntity> findAll() {
        List<ComentarioEntity> comentarios = ComentarioDao.findAll();
        comentarios.sort(Comparator.comparing(ComentarioEntity::getFechaComentario).reversed());
        return comentarios;
    }

    @Override
    @Transactional
    public void save(ComentarioEntity comentario) {
        ComentarioDao.save(comentario);
    }

    @Override
    @Transactional
    public ComentarioEntity findById(Long id) {
        return  ComentarioDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long comentario) {
        ComentarioDao.deleteById(comentario);
    }

    @Override
    @Transactional
    public ComentarioEntity actualizarComentarioEntity(ComentarioEntity comentario) {
        return ComentarioDao.save(comentario);
    }

    @Transactional
    public List<ComentarioEntity> findByObraTitulo(String titulo) {
        return comentarioDAO.findByIdObra_TituloObraContainingIgnoreCase(titulo);
    }

    @Transactional
    public List<ComentarioEntity> findByUsuarioNombre(String nombre) {
        return comentarioDAO.findByIdUsuario_NombreUsrContainingIgnoreCase(nombre);
    }
}
