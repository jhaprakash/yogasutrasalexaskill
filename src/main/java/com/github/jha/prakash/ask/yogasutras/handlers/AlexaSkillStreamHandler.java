package com.github.jha.prakash.ask.yogasutras.handlers;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.amazon.ask.builder.StandardSkillBuilder;

public class AlexaSkillStreamHandler extends SkillStreamHandler {
    private static final String SKILL_ID = "amzn1.ask.skill.d69abc94-207a-4e7d-a6eb-d5e7e9159ccc";

    public AlexaSkillStreamHandler() {
        super(getSkill());
    }

    private static Skill getSkill()
    {
        StandardSkillBuilder builder = Skills.standard();
        builder.withSkillId(SKILL_ID);
        builder.addRequestHandlers(new YogaSutraIntentHandler()
                , new RepeatSutraIntentHandler()
                , new NextSutraIntentHandler()
                , new PreviousSutraIntentHandler()
                , new DefaultLaunchRequestHandler());

        return builder.build();
    }
}
