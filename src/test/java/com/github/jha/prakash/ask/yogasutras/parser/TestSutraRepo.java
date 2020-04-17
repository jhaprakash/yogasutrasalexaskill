package com.github.jha.prakash.ask.yogasutras.parser;

import org.junit.Assert;
import org.junit.Test;

public class TestSutraRepo
{
    @Test
    public void testGetRandomSutraShortDescription()
    {
        SutraRepo repo = SutraRepo.getInstance();
        YogaSutra randomSutra1 = repo.getRandomYogaSutra();
        YogaSutra randomSutra2 = repo.getRandomYogaSutra();

        Assert.assertNotEquals(randomSutra1.getCount(), randomSutra2.getCount());
    }
}
