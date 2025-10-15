package com.usta.sistemagaleria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "ARTISTAS")

public class ArtistaEntity implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artista")
    private Long idArtista;

    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "nombre_art", length = 70, nullable = false)
    private String nombreArt;

    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "apellido_art", length = 70, nullable = false)
    private String apellidoArt;

    @Size(min = 1, max = 50)
    @Column(name = "nacionalidad_art", length = 50)
    private String nacionalidadArt;

    @DateTimeFormat(pattern = "yyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "disciplina_artistica", length = 50, nullable = false)
    private String disciplinaArtistica;

    @Size(min = 1, max = 50)
    @Column(name = "genero_artistico", length = 50)
    private String generoArtistico;

    @Size(min = 1, max = 50)
    @Column(name = "red_social", length = 50)
    private String redSocial;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "foto_artista", length = 200, nullable = false)
    private String fotoArtista;

    @OneToMany(mappedBy = "idArtista")
    private Set<ObraEntity> obras;

}
