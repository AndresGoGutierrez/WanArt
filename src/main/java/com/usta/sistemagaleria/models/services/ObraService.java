package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.ArtistaEntity;
import com.usta.sistemagaleria.entities.ExposicionEntity;
import com.usta.sistemagaleria.entities.ObraEntity;

import java.util.List;

public interface ObraService {
    public List<ObraEntity> findAll();
    public List<ObraEntity> findAllByClasificacion();
    public void save(ObraEntity obra);
    public ObraEntity findById(Long id);
    public void deleteById(Long obra);
    public ObraEntity actualizarObraEntity(ObraEntity obra);
    public ObraEntity findByClasificacion(String clasificacionObra);
    public List<ObraEntity> findByObraByArtistaId(Long idArtista);
    public List<ObraEntity> findObrasByExposicionId(Long idExpo);
    public List<ObraEntity> findByClasificacionTipoObra(String clasificacionTipoObra);
    public ObraEntity viewDetail(Long id);

    public List<ObraEntity> findByArtistaNombre (String nombre);
    public List<ObraEntity> findByObraTitulo (String titulo);
    public List<ObraEntity> findByObraAnno (Long annoCreacion);
    public List<ObraEntity> findByObraDimension (String dimension);
}
