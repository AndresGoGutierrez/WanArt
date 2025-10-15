package com.usta.sistemagaleria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@Table(name = "OBRAS")
public class ObraEntity implements Serializable {

    private static long serialversionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obra")
    private long idObra;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "titulo_ob", length = 200, unique = true, nullable = false)
    private String tituloObra;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "clasificacion", length = 50, nullable = false)
    private String clasificacionObra;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcionObra;

    @NotNull
    @Column(name = "ano_creacion", nullable = false)
    private long anoCreacion;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "foto_obra", length = 200, nullable = false)
    private String fotoObra;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "dimensiones", length = 30, nullable = false)
    private String dimensionesObra;

    @NotNull
    @JoinColumn (name = "id_artista", referencedColumnName = "id_artista")
    @ManyToOne (fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ArtistaEntity idArtista;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "exposiciones_obras",
            joinColumns = @JoinColumn(name = "id_obra", referencedColumnName = "id_obra"),
            inverseJoinColumns = @JoinColumn(name = "id_expo", referencedColumnName = "id_expo"))
    private Collection<ExposicionEntity> exposiciones;
}
