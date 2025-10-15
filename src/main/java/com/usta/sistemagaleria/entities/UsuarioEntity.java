package com.usta.sistemagaleria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "USUARIOS")
public class UsuarioEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_usuario")
    private Long idUsuario;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nombre_usu", length = 40, nullable = false)
    private String nombreUsr;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email_usr", length = 100, unique = true, nullable = false)
    private String emailUsr;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "contrasena_usr", length = 100, nullable = false)
    private String contrasenaUsr;

    @Column(name = "telefono_usr", length = 40)
    private String telefonoUsr;

    @NotNull
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne (fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RolEntity rol;
}
