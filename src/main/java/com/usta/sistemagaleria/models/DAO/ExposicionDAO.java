package com.usta.sistemagaleria.models.DAO;

import com.usta.sistemagaleria.entities.ExposicionEntity;
import com.usta.sistemagaleria.entities.ObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ExposicionDAO extends JpaRepository<ExposicionEntity, Long> {
    @Transactional
    @Query("SELECT EX FROM ExposicionEntity EX WHERE EX.idExpo=?1")
    public ExposicionEntity viewDetails(Long idExpo);

    @Transactional
    @Query("SELECT e FROM ExposicionEntity e JOIN e.obras o WHERE o.idObra = ?1")
    public List<ExposicionEntity> findByObrasByExposicion (Long idObra);

    @Transactional
    @Query("SELECT e FROM ExposicionEntity e WHERE "+
            "LOWER(e.tituloExpo) LIKE LOWER (CONCAT('%', :expoTitulo, '%'))")
    List<ExposicionEntity> findExposicionTitulo(@Param("expoTitulo") String expoTitulo);

    @Transactional
    @Query("SELECT e FROM ExposicionEntity e WHERE EXTRACT(YEAR FROM e.fechaInicio) = ?1")
    public List<ExposicionEntity> findExposicionesByYear(Long year);

    @Transactional
    @Query("SELECT e FROM ExposicionEntity e WHERE "+
            "LOWER(e.ubicacion) LIKE LOWER (CONCAT('%', :ubicacion, '%'))")
    List<ExposicionEntity> findExposicionUbicacion(@Param("ubicacion") String ubicacion);


}
