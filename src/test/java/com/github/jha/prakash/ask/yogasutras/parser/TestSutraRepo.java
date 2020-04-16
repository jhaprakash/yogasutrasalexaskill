package com.github.jha.prakash.ask.yogasutras.parser;

import org.junit.Assert;
import org.junit.Test;

public class TestSutraRepo
{
    @Test
    public void testGetRandomSutraShortDescription()
    {
        SutraRepo repo = SutraRepo.getInstance();
        String randomSutra1 = repo.getRandomSutraShortDescription();
        String randomSutra2 = repo.getRandomSutraShortDescription();

        Assert.assertNotEquals(randomSutra1, randomSutra2);
    }
}
