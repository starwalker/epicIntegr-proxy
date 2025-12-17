package edu.musc.bi.humanNameParser;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;

public class ParsedName {
    private List<String> titles = new ArrayList<>();
    private String firstName = "";
    private List<String> middleNames = new ArrayList<>();
    private List<String> nicknames = new ArrayList<>();
    private String lastName = "";
    private List<String> suffixes = new ArrayList<>();
    private List<String> prefixes = new ArrayList<>();

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<String> getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(List<String> middleNames) {
        this.middleNames = middleNames;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(List<String> suffixes) {
        this.suffixes = suffixes;
    }

    public List<String> getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(List<String> prefixes) {
        this.prefixes = prefixes;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        return String.format(
                        "%s %s %s %s %s %s",
                        getTitles().stream().collect(joining(" ")),
                        getFirstName(),
                        getMiddleNames().stream().collect(joining(" ")),
                        getNicknames().stream().collect(joining("\" \"", "\"", "\"")),
                        getLastName(),
                        getSuffixes().stream().collect(joining(" ")))
                .replaceAll("\"\"", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
