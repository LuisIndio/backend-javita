package com.example.api.web1.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "comprador_id", referencedColumnName = "id")
    private User comprador;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
    private User vendedor;

    @ManyToOne
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    private Inmueble inmueble;

}
