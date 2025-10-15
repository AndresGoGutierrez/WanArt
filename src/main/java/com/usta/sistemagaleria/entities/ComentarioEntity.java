package com.usta.sistemagaleria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "COMENTARIOS")
public class ComentarioEntity {

    private static long serialversionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long idComentario;

    @DateTimeFormat(pattern = "yyy-MM-dd")
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_comentario", nullable = false)
    private Date fechaComentario;

    @Size(min = 1, max = 1200)
    @Column(name = "descripcion_com", length = 1200)
    private String descripcionCom;

    @Column(name="favorito", columnDefinition = "boolean")
    private boolean favorito;

    @NotNull
    @JoinColumn (name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsuarioEntity idUsuario;

    @NotNull
    @JoinColumn (name = "id_obra", referencedColumnName = "id_obra")
    @ManyToOne (fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ObraEntity idObra;
}
