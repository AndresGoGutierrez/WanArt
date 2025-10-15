package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.ArtistaEntity;

import java.util.List;

public interface ArtistaService {
    public List<ArtistaEntity> findAll();
    public void save(ArtistaEntity artista);
    public ArtistaEntity findById(Long id);
    public void deleteById(Long artista);
    public ArtistaEntity actualizarArtistaEntity(ArtistaEntity artista);
    public ArtistaEntity viewDetail(Long id);
    public List<ArtistaEntity> findByArtistaIdObra (Long idObra);
    public List<ArtistaEntity> findByArtistaNombre (String nombre);
    public List<ArtistaEntity> findByArtistaDisciplina (String disciplina);
    public List<ArtistaEntity> findByArtistaGenero (String genero);
}