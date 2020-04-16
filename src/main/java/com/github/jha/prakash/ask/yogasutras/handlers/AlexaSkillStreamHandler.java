package com.github.jha.prakash.ask.yogasutras.handlers;

import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class AlexaSkillStreamHandler extends SkillStreamHandler {
    public AlexaSkillStreamHandler() {
        super(Skills.standard()
        .addRequestHandler(new YogaSutraHandler())
        .addRequestHandler(new DefaultLaunchRequestHandler())
        .build());
    }
}
