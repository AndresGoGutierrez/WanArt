package com.usta.sistemagaleria.models.DAO;

import com.usta.sistemagaleria.entities.ArtistaEntity;
import com.usta.sistemagaleria.entities.ExposicionEntity;
import com.usta.sistemagaleria.entities.ObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ObraDAO extends JpaRepository<ObraEntity, Long> {
    @Transactional
    @Query("SELECT OB FROM ObraEntity OB WHERE OB.clasificacionObra=?1")
    public ObraEntity findByClasificacion(String clasificacionObra);

    @Transactional
    @Query("SELECT OB FROM ObraEntity OB WHERE OB.idArtista.idArtista=?1")
    public List<ObraEntity> findByObrasIdArtista(Long idArtista);

    @Transactional
    @Query("SELECT ob FROM ObraEntity ob JOIN ob.exposiciones ex WHERE ex.idExpo = ?1")
    public List<ObraEntity> findObrasByExposicionId(Long idExpo);

    @Transactional
    @Query("SELECT OB FROM ObraEntity OB WHERE OB.clasificacionObra=?1")
    public List<ObraEntity> findByClasificacionTipoObra(String clasificacionTipoObra);

    @Transactional
    @Query("SELECT OB FROM ObraEntity OB WHERE OB.idObra=?1")
    public ObraEntity viewDetail(Long idObra);

    @Transactional
    @Query("SELECT o FROM ObraEntity o WHERE "+
            "LOWER(o.idArtista.nombreArt) LIKE LOWER (CONCAT('%', :nombre, '%'))"+
            "OR LOWER(o.idArtista.apellidoArt) LIKE LOWER (CONCAT('%', :nombre, '%'))")
    List<ObraEntity> findArtistaNombreOrApellido(@Param("nombre") String nombre);

    @Transactional
    @Query("SELECT o FROM ObraEntity o WHERE " +
            "LOWER(o.tituloObra) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<ObraEntity> findByTituloObra(@Param("titulo") String titulo);

    @Transactional
    @Query("SELECT o FROM ObraEntity o WHERE o.anoCreacion = :annoCreacion")
    List<ObraEntity> findByAnnoCreacionObra(@Param("annoCreacion") Long annoCreacion);

    @Transactional
    @Query("SELECT o FROM ObraEntity o WHERE " +
            "LOWER(o.dimensionesObra) LIKE LOWER(CONCAT('%', :dimensiones, '%'))")
    List<ObraEntity> findByDimensionesObra(@Param("dimensiones") String dimensiones);

}
