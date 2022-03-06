package assignmenttwo.pmatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * MString.java
 * String as Matching pattern
 * Can have variables - starting with ? - in either p or d
 * Created: Jan 2013
 *
 * @author phil green
 * @version 3
 */
public class MString {

    //forward & backward contexts for bchain
    //used in match_2_way
    private Map<String, String> pCon;
    private Map<String, String> dCon;
    private String baseString; //the string to be matched against
    private Map<String, Object> context;

    public MString(String baseString) {
        this.baseString = baseString;
    }

    public String getBaseString() {
        return baseString;
    }

    public Map<String, Object> getContext() {
        return context;
    } //accessor

    public Map<String, String> getpCon() {
        return pCon;
    }

    public Map<String, String> getdCon() {
        return dCon;
    }

    /**
     * match
     * match against given string
     *
     * @param stringToMatch
     */

    public boolean match(String stringToMatch) {
        String[] baseStringArray = baseString.split("\\s"); //tokenise the MString - separate into words
        String[] stringToMatchArray = stringToMatch.split("\\s"); //& the string it will match against
        boolean result;
        if (baseStringArray.length != stringToMatchArray.length)//unequal number of words
            result = false;
        else {
            result = true;
            context = new HashMap<>();
            int j = 0;
            while (j < baseStringArray.length) {
                String baseStringWord = baseStringArray[j];
                String stringToMatchWord = stringToMatchArray[j];
                j++;

                if (!baseStringWord.equals(stringToMatchWord)) {
                    if (isStringWildcard(baseStringWord))
                        context.put(baseStringWord, stringToMatchWord);
                    else {
                        if (isStringWildcard(stringToMatchWord))
                            context.put(stringToMatchWord, baseStringWord);
                        else return false;
                    }
                }
            }
        }

        return result;
    }

    /**
     * match
     * match against given string in a given context
     *
     * @param stringToMatch
     * @param context - the context
     */

    public boolean match(String stringToMatch, Map<String, Object> context) {
        this.context = new HashMap<>(context); //context for matching is COPY of context passed in
        String[] baseStringArray = baseString.split("\\s"); //tokenise the MString - separate into words
        String[] stringToMatchArray = stringToMatch.split("\\s"); //& the string it will match against
        boolean result;

        if (baseStringArray.length != stringToMatchArray.length)
            result = false;
        else {
            result = true;
            int j = 0;
            while (j < baseStringArray.length && result) {
                String baseStringWord = baseStringArray[j];
                String stringToMatchWord = stringToMatchArray[j];
                j++; // this was missing too

                if (!baseStringWord.equals(stringToMatchWord)) {
                    if (isStringWildcard(baseStringWord)) { //got an mvar in p
                        String res = (String) this.context.get(baseStringWord); //look it up in context
                        if (Objects.isNull(res)) { //no entry, so add new binding
                            this.context.put(baseStringWord, stringToMatchWord);
                        } else {
                            result = res.equals(stringToMatchWord);
                        }
                    } else if (isStringWildcard(stringToMatchWord)) {
                        String res = (String) this.context.get(stringToMatchWord); //look it up in context
                        if (Objects.isNull(res)) {
                            this.context.put(stringToMatchWord, baseStringWord);
                        } else {  //does bound value match nextD?
                            result = res.equals(baseStringWord);
                        }
                    } else  //neither is mvar
                        return false;
                }
            }
        }
        return result;
    }

    /**
     * match_2_way
     * match creating 2 contexts
     * pCon for matching vars in p (pTok)
     * dCon for matching vars in d
     * used in bchain
     *
     * @param d
     */

    public boolean match2Way(String d) {
        String[] pTok = baseString.split("\\s"); //tokenise the MString - separate into words
        String[] dTok = d.split("\\s"); //& the string it will match against
        boolean result;

        if (pTok.length != dTok.length)
            result = false;
        else {
            result = true;
            pCon = new HashMap<String, String>();
            dCon = new HashMap<>();
            int j = 0;
            while (j < pTok.length && result) {
                String nextP = pTok[j];
                String nextD = dTok[j];
                j = j + 1;

                if (!nextP.equals(nextD)) {
                    if (isStringWildcard(nextP)) {
                        pCon.put(nextP, nextD);
                    } //mvar in p
                    else {
                        if (isStringWildcard(nextD)) {
                            dCon.put(nextD, nextP);
                        } //mvar in d
                        else {
                            result = false;
                        }
                    }
                } //end of outer if
            }//end of while
        }//end of else
        return result;
    }


    //is a string a wildcard?

    private boolean isStringWildcard(String word) {
        return (word.startsWith("?"));
    }

    /**
     * mSubst
     * substitute back in patt from given context
     * creates new patt & returns it
     *
     * @param c - the context
     */

    public String mSubst(Map<String, Object> c) {
        context = c;
        String newPatt = "";
        String[] pTok = baseString.split("\\s");
        int j = 0;
        while (j < pTok.length) {
            String np = pTok[j];
            String res = (String) c.get(np);
            if (res == null) newPatt = newPatt.concat(np);
            else newPatt = newPatt.concat(res);
            j = j + 1; // forgot this too
            if (j < pTok.length) {
                newPatt = newPatt.concat(" ");
            } //no final space

        }
        return newPatt;
    }
}




