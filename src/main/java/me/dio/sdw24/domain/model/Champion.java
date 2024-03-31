package me.dio.sdw24.domain.model;

public record Champion(
        Long id,
        String nome,
        String role,
        String lore,
        String imageUrl
) {

}
