package com.usta.sistemagaleria.models.DAO;

import com.usta.sistemagaleria.entities.ArtistaEntity;
import com.usta.sistemagaleria.entities.ObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArtistaDAO extends JpaRepository<ArtistaEntity, Long> {
    @Transactional
    @Query("SELECT AR FROM ArtistaEntity AR WHERE AR.idArtista=?1")
    public ArtistaEntity viewDetails(Long idArtista);

    @Transactional
    @Query("SELECT AR FROM ArtistaEntity AR JOIN AR.obras o WHERE o.idObra=?1")
    public List<ArtistaEntity> findByArtistaIdObra(Long idObra);

    @Transactional
    @Query("SELECT a FROM ArtistaEntity a WHERE " +
            "LOWER(a.nombreArt) LIKE LOWER(CONCAT('%', :nombreOapellido, '%')) " +
            "OR LOWER(a.apellidoArt) LIKE LOWER(CONCAT('%', :nombreOapellido, '%'))")
    List<ArtistaEntity> findByNombreOrApellido(@Param("nombreOapellido") String nombreOapellido);

    @Transactional
    @Query("SELECT a FROM ArtistaEntity a WHERE " +
            "LOWER(a.disciplinaArtistica) LIKE LOWER(CONCAT('%', :disciplina, '%'))")
    List<ArtistaEntity> findByDisciplinaArtistica(@Param("disciplina") String disciplina);

    @Transactional
    @Query("SELECT a FROM ArtistaEntity a WHERE " +
            "LOWER(a.generoArtistico) LIKE LOWER(CONCAT('%', :genero, '%'))")
    List<ArtistaEntity> findByGeneroArtistico(@Param("genero") String genero);

}
