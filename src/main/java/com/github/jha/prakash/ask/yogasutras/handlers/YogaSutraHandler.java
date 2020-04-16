package com.github.jha.prakash.ask.yogasutras.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.github.jha.prakash.ask.yogasutras.parser.SutraRepo;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class YogaSutraHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("YogaSutraIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        return handlerInput.getResponseBuilder()
                .withSpeech(SutraRepo.getInstance().getRandomSutraShortDescription())
                .build();
    }
}
