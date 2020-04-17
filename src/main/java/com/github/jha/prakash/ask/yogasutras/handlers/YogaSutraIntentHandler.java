package com.github.jha.prakash.ask.yogasutras.handlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.github.jha.prakash.ask.yogasutras.parser.SutraRepo;
import com.github.jha.prakash.ask.yogasutras.parser.YogaSutra;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class YogaSutraIntentHandler implements IntentRequestHandler {
    public static final String KEY_SUTRA_INDEX = "SutraIndex";
    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("YogaSutraIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
//        RequestHelper requestHelper = RequestHelper.forHandlerInput(input);

        AttributesManager attributesManager = input.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();

        YogaSutra sutra = SutraRepo.getInstance().getRandomYogaSutra();

        // Report if the sutra repo is empty
        if (sutra == null)
        {
            return input.getResponseBuilder()
                    .withSpeech("Something went wrong, please try again in sometime")
                    .withSimpleCard("Something went wrong", "Please try again in sometime")
                    .build();
        }

        attributes.put(KEY_SUTRA_INDEX, sutra.getCount());
        attributesManager.setSessionAttributes(attributes);
        return input.getResponseBuilder()
                .withSpeech(sutra.getShortDescription())
                .withSimpleCard(sutra.getSanskrit() + " " + sutra.getPronunciation(), sutra.getShortDescription())
                .withReprompt("Say repeat to hear it again, next or previous to hear next or previous sutra")
                .build();
    }
}
