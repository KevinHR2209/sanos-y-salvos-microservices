package com.sanosysalvos.msreportes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    @Column(nullable = false)
    private String especie; // PERRO, GATO, OTRO

    private String raza;
    private String color;
    private String tamanio;
    private String sexo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tiene_microchip")
    private Boolean tieneMicrochip = false;

    @Column(name = "numero_microchip")
    private String numeroMicrochip;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reporte> reportes;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
        actualizadoEn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        actualizadoEn = LocalDateTime.now();
    }
}