package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.ExposicionEntity;
import com.usta.sistemagaleria.entities.ObraEntity;
import com.usta.sistemagaleria.models.DAO.ExposicionDAO;
import com.usta.sistemagaleria.models.DAO.ObraDAO;
import com.usta.sistemagaleria.models.DAO.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service

public class ObraServiceImplement implements ObraService{
    @Autowired
    private ObraDAO ObraDao;
    @Autowired
    private ObraDAO obraDAO;
    @Autowired
    private ExposicionDAO exposicionDAO;

    @Override
    @Transactional
    public List<ObraEntity> findAll() {
        return (List<ObraEntity>) ObraDao.findAll();
    }

    @Override
    @Transactional
    public List<ObraEntity> findAllByClasificacion() {
        List<ObraEntity> obras = obraDAO.findAll();
        obras.sort(Comparator.comparing(ObraEntity::getClasificacionObra));
        return obras;
    }

    @Override
    @Transactional
    public void save(ObraEntity obra) {
        ObraDao.save(obra);
    }

    @Override
    @Transactional
    public ObraEntity findById(Long id) {
        return ObraDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long obra) {
        ObraDao.deleteById(obra);
    }

    @Override
    @Transactional
    public ObraEntity actualizarObraEntity(ObraEntity obra) {
        return ObraDao.save(obra);
    }

    @Override
    @Transactional(readOnly = true)
    public ObraEntity findByClasificacion(String clasificacionObra) {
        return ObraDao.findByClasificacion(clasificacionObra);
    }

    @Override
    @Transactional
    public List<ObraEntity> findByObraByArtistaId(Long idArtista) {
        return ObraDao.findByObrasIdArtista(idArtista);
    }

    @Override
    public List<ObraEntity> findObrasByExposicionId(Long idExpo) {
        return ObraDao.findObrasByExposicionId(idExpo);
    }


    @Override
    public List<ObraEntity> findByClasificacionTipoObra(String clasificacionTipoObra) {
        return ObraDao.findByClasificacionTipoObra(clasificacionTipoObra);
    }

    @Override
    @Transactional (readOnly = true)
    public ObraEntity viewDetail(Long id) {
        return ObraDao.viewDetail(id);
    }

    @Override
    public List<ObraEntity> findByArtistaNombre(String nombre) {
        return ObraDao.findArtistaNombreOrApellido(nombre);
    }

    @Override
    public List<ObraEntity> findByObraTitulo(String titulo) {
        return ObraDao.findByTituloObra(titulo);
    }

    @Override
    public List<ObraEntity> findByObraAnno(Long annoCreacion) {
        return obraDAO.findByAnnoCreacionObra(annoCreacion);
    }


    @Override
    public List<ObraEntity> findByObraDimension(String dimension) {
        return obraDAO.findByDimensionesObra(dimension);
    }
}
