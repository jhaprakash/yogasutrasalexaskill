package com.github.jha.prakash.ask.yogasutras.parser;

/**
 * A simple data object to hold contents associated with a given sutra.
 * A sutra contains an index, sutra in Sanskrit,
 * english pronunciation of the sutra,
 * a short description (literal translation of the sutra)
 * and an option long description.
 */
public class YogaSutra
{
    private int count;
    private String index;
    private String sanskrit;
    private String pronunciation;
    private String shortDescription;
    private String longDescription;

    @Override
    public String toString() {
        return "YogaSutra{" +
                "count=" + count +
                ", index='" + index + '\'' +
                ", sanskrit='" + sanskrit + '\'' +
                ", pronunciation='" + pronunciation + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSanskrit() {
        return sanskrit;
    }

    public void setSanskrit(String sanskrit) {
        this.sanskrit = sanskrit;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
