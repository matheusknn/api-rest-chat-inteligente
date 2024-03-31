package me.dio.sdw24.application;

import me.dio.sdw24.domain.exception.ChampionNotFoundException;
import me.dio.sdw24.domain.model.Champion;
import me.dio.sdw24.domain.ports.ChampionRepository;

public record AskChampionUseCase(ChampionRepository repository) {
    public String askChampion(Long championId, String question) {
        Champion champion = repository.findByd(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String championsContext = champion.generateContextByQuestion(question);

        return championsContext;
    }
}
