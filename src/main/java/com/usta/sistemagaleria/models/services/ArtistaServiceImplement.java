package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.ArtistaEntity;
import com.usta.sistemagaleria.models.DAO.ArtistaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service

public class ArtistaServiceImplement implements ArtistaService{
    @Autowired
    private ArtistaDAO artistaDAO;

    @Override
    public List<ArtistaEntity> findAll() {
        List<ArtistaEntity> artistas = artistaDAO.findAll();
        artistas.sort(Comparator.comparing(ArtistaEntity::getNombreArt)
                .thenComparing(ArtistaEntity::getApellidoArt));
        return artistas;
    }

    @Transactional
    @Override
    public void save(ArtistaEntity artista) {
        artistaDAO.save(artista);
    }

    @Transactional
    @Override
    public ArtistaEntity findById(Long id) {
        return artistaDAO.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteById(Long artista) {
        artistaDAO.deleteById(artista);
    }

    @Transactional
    @Override
    public ArtistaEntity actualizarArtistaEntity(ArtistaEntity artista) {
        return artistaDAO.save(artista);
    }

    @Transactional(readOnly = true)
    @Override
    public ArtistaEntity viewDetail(Long id) {
        return artistaDAO.viewDetails(id);
    }

    @Override
    public List<ArtistaEntity> findByArtistaIdObra(Long idObra) {
        return artistaDAO.findByArtistaIdObra(idObra);
    }

    @Override
    public List<ArtistaEntity> findByArtistaNombre(String nombre) {
        return artistaDAO.findByNombreOrApellido(nombre);
    }

    @Override
    public List<ArtistaEntity> findByArtistaDisciplina(String disciplina) {
        return artistaDAO.findByDisciplinaArtistica(disciplina);
    }

    @Override
    public List<ArtistaEntity> findByArtistaGenero(String genero) {
        return artistaDAO.findByGeneroArtistico(genero);
    }
}
