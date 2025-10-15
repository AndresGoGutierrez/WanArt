package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.ExposicionEntity;
import com.usta.sistemagaleria.models.DAO.ExposicionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service

public class ExposicionServiceImplement implements ExposicionService {
    @Autowired
    private ExposicionDAO ExposicionDao;

    @Override
    @Transactional
    public List<ExposicionEntity> findAll() {
        List<ExposicionEntity> exposiciones = ExposicionDao.findAll();
        exposiciones.sort(Comparator.comparing(ExposicionEntity::getTituloExpo).thenComparing(ExposicionEntity::getFechaInicio).reversed());
        return exposiciones;
    }

    @Override
    @Transactional
    public void save(ExposicionEntity exposicion) {
        ExposicionDao.save(exposicion);
    }

    @Override
    @Transactional
    public ExposicionEntity findById(Long id) {
        return ExposicionDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long exposion) {
        ExposicionDao.deleteById(exposion);
    }

    @Override
    @Transactional
    public ExposicionEntity actualizarExposicionEntity(ExposicionEntity exposicion) {
        return ExposicionDao.save(exposicion);
    }

    @Override
    public List<ExposicionEntity> findByObrasByExposicion(Long idObra) {
        return ExposicionDao.findByObrasByExposicion(idObra);
    }

    @Override
    @Transactional
    public ExposicionEntity viewDetail(Long id) {
        return ExposicionDao.viewDetails(id);
    }

    @Override
    public List<ExposicionEntity> findByExposicionTitulo(String tituloExposicion) {
        return ExposicionDao.findExposicionTitulo(tituloExposicion);
    }

    @Override
    public List<ExposicionEntity> findByObraAnno(Long annoInicio) {
        return ExposicionDao.findExposicionesByYear(annoInicio);
    }

    @Override
    public List<ExposicionEntity> findByExposicionUbicacion(String ubicacion) {
        return ExposicionDao.findExposicionUbicacion(ubicacion);
    }
}
