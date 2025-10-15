package com.usta.sistemagaleria.models.DAO;

import com.usta.sistemagaleria.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioDAO extends CrudRepository<UsuarioEntity, Long> {
    @Transactional
    @Query("SELECT US FROM UsuarioEntity US WHERE US.emailUsr=?1")
    public UsuarioEntity findByEmail(String email);
}
