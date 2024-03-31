package me.dio.sdw24.domain.ports;

import me.dio.sdw24.domain.model.Champion;

import java.util.List;

public interface ChampionRepository {

    List<Champion> findAll();
}
