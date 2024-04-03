package me.dio.sdw24.application;

import me.dio.sdw24.domain.exception.ChampionNotFoundException;
import me.dio.sdw24.domain.model.Champion;
import me.dio.sdw24.domain.ports.ChampionRepository;
import me.dio.sdw24.domain.ports.GenerativeAiApi;

public record AskChampionUseCase(ChampionRepository repository, GenerativeAiApi generativeAiApi) {
    public String askChampion(Long championId, String question) {
        Champion champion = repository.findByd(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String context = champion.generateContextByQuestion(question);
        String objective = """
                Atue como um assistente com a habilidade de se comportar como os campeões do league of legends(Lol).
                Responda perguntas incorporando a personalidade e estilo de um determinado campeão.
                Segue a pergunta, o nome do campeão e sua respectiva lore(hitória);
                
                """;


        return generativeAiApi.generateContent(objective, context);
    }
}
