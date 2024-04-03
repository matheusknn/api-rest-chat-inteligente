package me.dio.sdw24.adapters.out;

import feign.RequestInterceptor;
import me.dio.sdw24.domain.ports.GenerativeAiApi;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.*;
import java.util.List;

@FeignClient(name = "openAiChatApi", url = "${openai.base-url}", configuration = OpenAiChatApi.class)
public interface OpenAiChatApi extends GenerativeAiApi {

    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResponse chatCompletion(OpenAiChatCompletionRequest request);
    @Override
    default String generateContent(String objective, String context) {
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", context)

        );
        OpenAiChatCompletionRequest request = new OpenAiChatCompletionRequest(model, messages);
        OpenAiChatCompletionResponse response = chatCompletion(request);
        return response.choices().getFirst().message().content();
    }

    record OpenAiChatCompletionRequest(String model, List<Message> messages) {
    }
    record Message(String role, String content){}

    record OpenAiChatCompletionResponse(List<Choice> choices) {
    }
    record Choice(Message message) {}
    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}")String apiKey) {
            return requestTemplate -> requestTemplate.header(
                    HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }
    }
}
