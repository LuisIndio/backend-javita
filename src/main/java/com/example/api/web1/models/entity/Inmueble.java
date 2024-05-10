package com.example.api.web1.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inmuebles")
public class Inmueble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String tipo;

    @Column(length = 100)
    private String direccion;

    @Column(length = 100)
    private String Tamano_m2;

    @Column(length = 100)
    private int habitaciones;

    @Column(length = 100)
    private int baños;

    @Column(length = 100)
    private String descripcion;

    @Column(length = 100)
    private int precio;

    @Column(length = 100)
    private int año_construccion;

    @Column(length = 100)
    private String adquisicion;

    private String imagen;

    @OneToMany(mappedBy = "inmueble", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Favorito> favoritos;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User vendedor;

    @Transient
    private Long vendedorId; // ID del vendedor, no se mapea a la base de datos

}
