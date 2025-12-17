package edu.musc.bi.humanNameParser;

public class NamePartsConfig
{
    private static String[] conjuctionList =
    {
        "&","and","et","e","of","the","und","y"
    };
    private static String[] suffixList =
    {
        "esq","esquire","jr","jnr","sr","snr","2","ii","iii","iv",
        "md","phd","j.d.","ll.m.","m.d.","d.o.","d.c.","p.c.","ph.d."
    };
    private static String[] prefixList =
    {
        "ab","bar","bin","da","dal","de","de la","del","della","der",
        "di","du","ibn","l\"","la","le","san","st","st.","ste","ter","van",
        "van de","van der","van den","vel","ver","vere","von"
    };
    private static String[] titleList =
    {
        "dr","miss","mr","mrs","ms","prof","sir","frau","herr","hr",
        "monsieur","captain","doctor","judge","officer","professor"
    };

    public static String[] getConjuctionList()
    {
        return conjuctionList;
    }

    public static void setConjuctionList(String[] conjuctionList)
    {
        NamePartsConfig.conjuctionList = conjuctionList;
    }

    public static String[] getSuffixList()
    {
        return suffixList;
    }

    public static void setSuffixList(String[] suffixList)
    {
        NamePartsConfig.suffixList = suffixList;
    }

    public static String[] getPrefixList()
    {
        return prefixList;
    }

    public static void setPrefixList(String[] prefixList)
    {
        NamePartsConfig.prefixList = prefixList;
    }

    public static String[] getTitleList()
    {
        return titleList;
    }

    public static void setTitleList(String[] titleList)
    {
        NamePartsConfig.titleList = titleList;
    }
}
