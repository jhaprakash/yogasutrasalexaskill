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

public class NextSutraIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest)
    {
        return input.matches(intentName("NextSutraIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest)
    {
        AttributesManager attributesManager = input.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        YogaSutra nextSutra = null;

        if (attributes.containsKey(YogaSutraIntentHandler.KEY_SUTRA_INDEX))
        {
            // Play next in sequence
            nextSutra = SutraRepo.getInstance().getNextYogaSutra(
                    (Integer)attributes.get(YogaSutraIntentHandler.KEY_SUTRA_INDEX));
        }
        else
        {
            // Start from beginning
            nextSutra = SutraRepo.getInstance().getNextYogaSutra(-1);
        }

        // Report error if sutra repo is not populated
        if (nextSutra == null)
        {
            return input.getResponseBuilder()
                    .withSpeech("Something went wrong, please try again in sometime")
                    .withSimpleCard("Something went wrong", "Please try again in sometime")
                    .build();
        }

        attributes.put(YogaSutraIntentHandler.KEY_SUTRA_INDEX, nextSutra.getCount());
        attributesManager.setSessionAttributes(attributes);

        return input.getResponseBuilder()
                .withSpeech(nextSutra.getShortDescription())
                .withSimpleCard(nextSutra.getSanskrit() + " "
                        + nextSutra.getPronunciation(), nextSutra.getShortDescription())
                .withReprompt("Say repeat to hear it again, next or previous to hear next or previous sutra")
                .build();
    }
}
