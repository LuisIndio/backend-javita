package com.example.api.web1.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoDTO {
    private Long favoritoId;
    private Long userId;
    private Long inmuebleId;

    // Getters y setters
}
