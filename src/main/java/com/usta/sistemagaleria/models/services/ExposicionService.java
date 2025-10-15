package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.ExposicionEntity;
import com.usta.sistemagaleria.entities.ObraEntity;

import java.util.List;

public interface ExposicionService {
    public List<ExposicionEntity> findAll();
    public void save(ExposicionEntity exposicion);
    public ExposicionEntity findById(Long id);
    public void deleteById(Long exposion);
    public ExposicionEntity actualizarExposicionEntity(ExposicionEntity exposicion);
    public List<ExposicionEntity> findByObrasByExposicion (Long idObra);
    public ExposicionEntity viewDetail(Long id);

    public List<ExposicionEntity> findByExposicionTitulo (String tituloExposicion);
    public List<ExposicionEntity> findByObraAnno (Long annoInicio);
    public List<ExposicionEntity> findByExposicionUbicacion (String ubicacion);
}
