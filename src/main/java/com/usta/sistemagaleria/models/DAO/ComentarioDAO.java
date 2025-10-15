package com.usta.sistemagaleria.models.DAO;

import com.usta.sistemagaleria.entities.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioDAO extends JpaRepository<ComentarioEntity, Long> {
    List<ComentarioEntity> findByIdObra_TituloObraContainingIgnoreCase(String titulo);
    List<ComentarioEntity> findByIdUsuario_NombreUsrContainingIgnoreCase(String nombre);
}
