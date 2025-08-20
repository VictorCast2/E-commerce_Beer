package com.application.service.http;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatOpenAISevices {

    // Este es un cliente de chat que se conecta a OpenAI
    private ChatClient chatClient;

    // Método para responder preguntas utilizando el cliente de chat
    public String responder(String pregunta) {
        return "Respuesta a la pregunta: " + pregunta;
    }

}