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
     * Method returns a random sutra from the repo
     * @return  A random YogaSutra from the repo. If no Yoga Sutras in the repo, return null.
     */
    public YogaSutra getRandomYogaSutra()
    {
        return getYogaSutra(new Random().nextInt(sutraArrayList.size()));
    }

    /**
     * @param currentIndex  Next sutra after the current index
     * @return  YogaSutra next in the repo. If currentIndex was last, method returns first sutra
     */
    public YogaSutra getNextYogaSutra(int currentIndex)
    {
        return getYogaSutra(currentIndex + 1);
    }

    /**
     * @param currentIndex Previous sutra to current index
     * @return  YogaSutra prior to current index. If currentIndex is 0, last YogaSutra will be returned.
     */
    public YogaSutra getPreviousYogaSutra(int currentIndex)
    {
        return getYogaSutra(currentIndex - 1);
    }

    /**
     * @param index index of the sutra who's description is needed.
     * @return YogaSutra at given index. If no Yoga Sutras in the repo, return null.
     */
    public YogaSutra getYogaSutra(int index)
    {
        if (sutraArrayList.size() == 0) return null;
        if (index < 0)  return sutraArrayList.get(sutraArrayList.size() - 1);   // Last sutra in the repo
        return sutraArrayList.get(index % sutraArrayList.size());
    }
}