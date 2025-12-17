package edu.musc.bi.humanNameParser;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullNameParser
{
    private static String NICKNAME_STRING = "\\\"[\\w+ ]+\\\"";
    private static Pattern NICKNAME_PATTERN = Pattern.compile(NICKNAME_STRING);

    public ParsedName parse(String unparsedName, NamePartsConfig namePartsConfig)
    {
        ParsedName parsedName = new ParsedName();
        unparsedName = unparsedName.trim();

        unparsedName = parseNickname(unparsedName, parsedName);
        unparsedName = parseTitle(unparsedName, parsedName);
        unparsedName = parseSuffixes(unparsedName, parsedName);
        unparsedName = parsePrefixes(unparsedName, parsedName);
        unparsedName = parseFirstName(unparsedName, parsedName);
        unparsedName = parseLastName(unparsedName, parsedName);

        parseMiddleName(unparsedName, parsedName);

        return parsedName;
    }

    public ParsedName parse(String fullName)
    {
        return parse(fullName, new NamePartsConfig());
    }

    private String parseNickname(String fullName, ParsedName parsedName)
    {
        List<String> nicknames = new ArrayList<>();
        Matcher nicknameMatcher = NICKNAME_PATTERN.matcher(fullName);

        while(nicknameMatcher.find())
        {
            nicknames.add(nicknameMatcher.group().replaceAll("\"", ""));
        }

        fullName = fullName.replaceAll(NICKNAME_STRING, "");
        parsedName.setNicknames(nicknames);

        return fullName;
    }

    private String parseTitle(String fullName, ParsedName parsedName)
    {
        List<String> parsedTitles = new ArrayList<>();
        if(Arrays.stream(NamePartsConfig.getTitleList()).parallel().anyMatch(fullName.toLowerCase()::contains))
        {
            for(String title : NamePartsConfig.getTitleList())
            {
                TempNamePartListMatching tempNamePartListMatching = findMatchingString(fullName, title);
                if(tempNamePartListMatching.matchString != null)
                {
                    parsedTitles.add(tempNamePartListMatching.matchString);
                    fullName = tempNamePartListMatching.fullName;
                }
            }
        }

        parsedName.setTitles(parsedTitles);
        return fullName;
    }

    private String parseSuffixes(String fullName, ParsedName parsedName)
    {
        List<String> parsedSuffixes = new ArrayList<>();
        if(Arrays.stream(NamePartsConfig.getSuffixList()).parallel().anyMatch(fullName.toLowerCase()::contains))
        {
            for(String suffix : NamePartsConfig.getSuffixList())
            {
                TempNamePartListMatching tempNamePartListMatching = findMatchingString(fullName, suffix);
                if(tempNamePartListMatching.matchString != null)
                {
                    parsedSuffixes.add(tempNamePartListMatching.matchString);
                    fullName = tempNamePartListMatching.fullName;
                }
            }
        }

        parsedName.setSuffixes(parsedSuffixes);
        return fullName;
    }

    private String parsePrefixes(String fullName, ParsedName parsedName)
    {
        List<String> parsedPrefixes = new ArrayList<>();
        if(Arrays.stream(NamePartsConfig.getPrefixList()).parallel().anyMatch(fullName.toLowerCase()::contains))
        {
            for(String prefix : NamePartsConfig.getPrefixList())
            {
                TempNamePartListMatching tempNamePartListMatching = findMatchingString(fullName, prefix);
                if(tempNamePartListMatching.matchString != null)
                {
                    parsedPrefixes.add(tempNamePartListMatching.matchString);
                    fullName = tempNamePartListMatching.fullName;
                }
            }
        }

        parsedName.setPrefixes(parsedPrefixes);
        return fullName;
    }

    private String parseLastName(String fullName, ParsedName parsedName)
    {
        String[] nameParts = fullName.split(" +");
        String lastName = nameParts[nameParts.length - 1];

        fullName = fullName.substring(0, fullName.length() - lastName.length());

        if(!parsedName.getPrefixes().isEmpty())
        {
            parsedName.setLastName(String.join(" ", parsedName.getPrefixes()) + " " + lastName.replaceAll(" +", ""));
        }
        else
        {
            parsedName.setLastName(lastName.replaceAll(" +", ""));
        }

        return fullName;
    }

    private String parseFirstName(String fullName, ParsedName parsedName)
    {
        String[] nameParts = fullName.split(" +");
        String firstName = nameParts[0];

        fullName = fullName.substring(firstName.length(), fullName.length());
        parsedName.setFirstName(firstName.replaceAll(" +", ""));

        return fullName;
    }

    private void parseMiddleName(String fullName, ParsedName parsedName)
    {
        List<String> middleNames = new ArrayList<>();

        for(String name : fullName.trim().split(" "))
        {
            middleNames.add(name);
        }

        parsedName.setMiddleNames(middleNames);
    }

    private TempNamePartListMatching findMatchingString(String fullName, String toMatch)
    {
        TempNamePartListMatching tempNamePartListMatching = new TempNamePartListMatching();
        if(fullName.matches("(?i:.*" + toMatch + ".*)"))
        {
            String[] nameParts = fullName.split(" +");
            for(String name : nameParts)
            {
                if(name.toLowerCase().matches(String.format("\\b%s\\b\\.?", toMatch)))
                {
                    tempNamePartListMatching.matchString = name;

                    String regex;
                    if(name.contains("."))
                    {
                        regex = String.format("\\b %s", name);
                    }
                    else
                    {
                        regex = String.format("\\b %s\\b|\\b%s \\b", name, name);
                    }
                    tempNamePartListMatching.fullName = fullName.replaceAll(regex, "");
                    break;
                }
            }
        }

        return tempNamePartListMatching;
    }
}

class TempNamePartListMatching
{
    String matchString = null;
    String fullName;
}
