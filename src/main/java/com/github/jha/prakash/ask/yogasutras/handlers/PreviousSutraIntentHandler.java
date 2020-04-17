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

public class PreviousSutraIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest)
    {
        return input.matches(intentName("PreviousSutraIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest)
    {
        AttributesManager attributesManager = input.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        YogaSutra previousSutra = null;

        if (attributes.containsKey(YogaSutraIntentHandler.KEY_SUTRA_INDEX))
        {
            // Play next in sequence
            previousSutra = SutraRepo.getInstance().getPreviousYogaSutra(
                    (Integer)attributes.get(YogaSutraIntentHandler.KEY_SUTRA_INDEX));
        }
        else
        {
            // Start from beginning
            previousSutra = SutraRepo.getInstance().getNextYogaSutra(0);
        }

        // Report error if sutra repo is not populated
        if (previousSutra == null)
        {
            return input.getResponseBuilder()
                    .withSpeech("Something went wrong, please try again in sometime")
                    .withSimpleCard("Something went wrong", "Please try again in sometime")
                    .build();
        }

        attributes.put(YogaSutraIntentHandler.KEY_SUTRA_INDEX, previousSutra.getCount());
        attributesManager.setSessionAttributes(attributes);

        return input.getResponseBuilder()
                .withSpeech(previousSutra.getShortDescription())
                .withSimpleCard(previousSutra.getSanskrit() + " "
                        + previousSutra.getPronunciation(), previousSutra.getShortDescription())
                .withReprompt("Say repeat to hear it again, next or previous to hear next or previous sutra")
                .build();
    }
}
