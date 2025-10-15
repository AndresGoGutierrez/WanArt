package com.usta.sistemagaleria.models.services;

import com.usta.sistemagaleria.entities.RolEntity;

import java.util.List;

public interface RolService {
    public List<RolEntity> findAll();
    public void save(RolEntity rol);
    public RolEntity findById(Long id);
    public void deleteById(Long rol);
    public RolEntity actualizarRolEntity(RolEntity rol);
}
