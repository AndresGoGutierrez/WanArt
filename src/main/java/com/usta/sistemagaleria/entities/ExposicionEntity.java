package com.usta.sistemagaleria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "EXPOSICIONES")
public class ExposicionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expo")
    private long idExpo;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "titulo_expo", length = 50, unique = true, nullable = false)
    private String tituloExpo;

    @NotNull
    @Size(min = 1, max = 100000)
    @Column(name = "descripcion_expo", length = 100000, nullable = false)
    private String descripcionExpo;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;

    @Column (name = "aforo")
    private Long aforo;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "ubicacion", length = 150, nullable = false)
    private String ubicacion;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "foto_expo", length = 200, nullable = false)
    private String fotoExpo;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "Exposiciones_Obras",
            joinColumns = @JoinColumn(name = "id_expo", referencedColumnName = "id_expo"),
            inverseJoinColumns = @JoinColumn(name = "id_obra", referencedColumnName = "id_obra"))
    private Collection<ObraEntity> obras;

    @Override
    public String toString() {
        return "exposicion" + tituloExpo;
    }
}
