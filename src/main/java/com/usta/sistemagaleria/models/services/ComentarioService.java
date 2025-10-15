package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.ComentarioEntity;

import java.util.List;

public interface ComentarioService {
    public List<ComentarioEntity> findAll();
    public void save(ComentarioEntity comentario);
    public ComentarioEntity findById(Long id);
    public void deleteById(Long comentario);
    public ComentarioEntity actualizarComentarioEntity(ComentarioEntity comentario);
    public List<ComentarioEntity> findByObraTitulo(String titulo);
    public List<ComentarioEntity> findByUsuarioNombre(String nombre);
}
