package com.github.jha.prakash.ask.yogasutras.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The class parses a pre-formatted sutra resource file and converts each sutra into a YogaSutra object.
 * A formatted sutra file may contain more than one sutra.
 */
public class SutraFileParser
{
    private static Logger logger = Logger.getLogger(SutraFileParser.class.getSimpleName());

    static final String INDEX = "<<index>>";
    static final String SANSKRIT = "<<sanskrit>>";
    static final String PRONUNCIATION = "<<pronunciation>>";
    static final String SHORT_DESCRIPTION = "<<short>>";
    static final String LONG_DESCRIPTION = "<<long>>";
    static final String END_TAG = "<<";

    public static List<YogaSutra> parseFile(File file)
    {
        return parseFileContents(readFileContent(file));
    }

    private static List<YogaSutra> parseFileContents(String content)
    {
        int startIndex = 0;
        int endIndex = 0;
        List<YogaSutra> sutrasList = new ArrayList<>();

        while (startIndex >= 0)
        {
            YogaSutra sutra = new YogaSutra();
            startIndex = content.indexOf(INDEX, startIndex) + INDEX.length();
            endIndex = content.indexOf(END_TAG, startIndex);
            sutra.setIndex(content.substring(startIndex, endIndex).trim());

            startIndex = content.indexOf(SANSKRIT, startIndex) + SANSKRIT.length();
            endIndex = content.indexOf(END_TAG, startIndex);
            sutra.setSanskrit(content.substring(startIndex, endIndex).trim());

            startIndex = content.indexOf(PRONUNCIATION, startIndex) + PRONUNCIATION.length();
            endIndex = content.indexOf(END_TAG, startIndex);
            sutra.setPronunciation(content.substring(startIndex, endIndex).trim());

            startIndex = content.indexOf(SHORT_DESCRIPTION, startIndex) + SHORT_DESCRIPTION.length();
            endIndex = content.indexOf(END_TAG, startIndex);
            sutra.setShortDescription(content.substring(startIndex, endIndex).trim());

            startIndex = content.indexOf(LONG_DESCRIPTION, startIndex) + LONG_DESCRIPTION.length();
            endIndex = content.indexOf(END_TAG, startIndex);
            sutra.setLongDescription(content.substring(startIndex, endIndex).trim());

            sutrasList.add(sutra);

            startIndex = content.indexOf(INDEX, startIndex);
        }

        return sutrasList;
    }

    /**
     * Helper method to read the entire file into a string object.
     * @param file  File contents to be read
     * @return  String type object containing contents of the file
     */
    private static String readFileContent(File file)
    {
        String content = "";

        try
        {
            logger.info("Reading contents of file: " + file.getPath());
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e)
        {
            logger.info("Exception occurred while reading from resource file: " + file.getPath());
            logger.info(e.toString());
        }
        return content;
    }
}
