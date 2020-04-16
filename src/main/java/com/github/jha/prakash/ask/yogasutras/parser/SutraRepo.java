package com.github.jha.prakash.ask.yogasutras.parser;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

/**
 * Class to parse all the yoga sutras from resources directory, convert it into YogaSutra object and store it in a list.
 * The class also provides methods to access long and short description of yoga sutra randomly picked from the list.
 * If long description is absent, short description is returned instead.
 */
public class SutraRepo
{
    // Eager singleton instance
    private static SutraRepo repo = new SutraRepo();

    // ArrayList to store all available sutras in the resources directory.
    private ArrayList<YogaSutra> sutraArrayList;

    // Name of directory inside resources, containing all sutras.
    private static final String SOURCE_DIR = "sutras";

    // Instance of logger
    private static Logger logger = Logger.getLogger(SutraRepo.class.getSimpleName());

    // Private constructor
    private SutraRepo()
    {
        sutraArrayList = new ArrayList<>();
        processSutrasFromResources();
    }

    /**
     * Helper method to parse all files containing formatted sutras
     */
    private void processSutrasFromResources()
    {
        int sutraCount = 1;
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(SOURCE_DIR);
        if (resource != null)
        {
            File folder = new File(resource.getFile());
            File[] files = folder.listFiles();
            if (files == null)  return;
            ArrayList<File> filesList = new ArrayList<>(Arrays.asList(files));
            Collections.sort(filesList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            for (File file : filesList)
            {
                for (YogaSutra sutra : SutraFileParser.parseFile(file))
                {
                    sutra.setCount(sutraCount++);
                    sutraArrayList.add(sutra);
                }
                sutraArrayList.addAll(SutraFileParser.parseFile(file));
            }
        }
    }

    /**
     * @return Singleton instance of SutraRepo object
     */
    public static SutraRepo getInstance()
    {
        return repo;
    }

    /**
     * @return String containing long description of a sutra. If long description is not available, short description is returned instead.
     */
    public String getRandomSutraLongDescription()
    {
        String longDesc = getRandomSutra(true);

        if (!longDesc.isEmpty())    return longDesc;
        return getRandomSutra(false);
    }

    /**
     * @return String containing short description of a sutra.
     */
    public String getRandomSutraShortDescription()
    {
        return getRandomSutra(false);
    }

    /**
     * @param getLong If set to true, returns long description, else short description is returned
     * @return  description associated with the sutra
     */
    private String getRandomSutra(boolean getLong)
    {
        if (sutraArrayList.size() == 0)     return "Sorry, no sutras in the repo";
        int randomIndex = new Random().nextInt(sutraArrayList.size());
        if (getLong)    return getLongDescription(randomIndex);
        return getShortDescription(randomIndex);
    }

    /**
     * @param index index of the sutra who's description is needed. Long description can be an empty string
     * @return Long description associated with the sutra.
     */
    private String getLongDescription(int index)
    {
        return getSutraDescription(index, true);
    }

    /**
     * @param index index of the sutra who's description is needed.
     * @return short description associated with the sutra.
     */
    private String getShortDescription(int index)
    {
        return getSutraDescription(index, false);
    }

    /**
     * @param index ndex of the sutra who's description is needed.
     * @param getLong if set to true, returns long description, else returns false
     * @return Description associated with the sutra.
     */
    private String getSutraDescription(int index, boolean getLong)
    {
        if (index >= sutraArrayList.size())
        {
            logger.info("No sutra exists at index: " + index);
            return "Sorry, no sutra here";
        }
        if (getLong)    return sutraArrayList.get(index).getLongDescription();

        return sutraArrayList.get(index).getShortDescription();
    }
}