package assignmenttwo.pmatch;

import java.util.*;

/**
 * MStringVector.java
 * a vector of Mstring
 * with facilities for pattern matching
 * Created: Jan 2013
 *
 * @author phil green
 * @version 4
 */
public class MStringVector {
    private List<MString> vector; //the vector of MStrings
    private Map<String, Object> context;  //context for matching
    private List<MString> vectorRemainder;  //remainder of vector after match
    private List<MatchDetails> matchDetailsList = new ArrayList<>(); //for multiple matches - list of MatchDetails

    //constructor given a vector
    public MStringVector() {
        vector = new ArrayList<>();
    }

    //constructor given a string with '|' as separator
    public MStringVector(List<MString> vin) {
        vector = vin;
    }

    public List<MString> getVector() {
        return vector;
    }

    public void setVector(List<MString> vector) {
        this.vector = vector;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public List<MString> getVectorRemainder() {
        return vectorRemainder;
    }

    public List<MatchDetails> getMatchDetailsList() {
        return matchDetailsList;
    }

    public MStringVector(String s) {
        vector = new ArrayList<>();
        //the input string - | is separator
        int i = 0;
        int j = s.indexOf('|');

        while (j != -1) {
            vector.add(new MString(s.substring(i, j)));
            i = j + 1;
            j = s.indexOf('|', j + 1);
        }
        vector.add(new MString(s.substring(i)));
    }

    //constructor given a string array
    public MStringVector(String[] stringArray) {
        vector = new ArrayList<>();
        for (String string : stringArray) {
            vector.add(new MString(string));
        }
    }

    /**
     * match
     * match against a string
     * until a match is found, giving a context, or vector runs out
     * sets remVec to remainder of vector after matching item
     *
     * @param stringToMatch
     */


    public boolean match(String stringToMatch) {
        boolean result = false;
        Iterator<MString> vit = vector.iterator();
        while (vit.hasNext() && !result) {
            MString nextVector = vit.next();
            result = nextVector.match(stringToMatch);
            if (result) {
                context = nextVector.getContext();
                vectorRemainder = new ArrayList<>();
                while (vit.hasNext()) {
                    vectorRemainder.add(vit.next());
                }
            }

        }
        return result;
    }

    /**
     * match against a string
     * given an initial context
     * until a match is found, giving a context, or vector runs out
     * sets remVec to remainder of vector after matching item
     *
     * @param stringToMatch
     */

    public boolean match(String stringToMatch, Map<String, Object> context) {
        boolean result = false;
        Iterator<MString> vectorIterator = vector.iterator();
        while (vectorIterator.hasNext() && !result) {
            MString nextVector = vectorIterator.next();
            result = nextVector.match(stringToMatch, context);
            if (result) {
                this.context = nextVector.getContext();
                vectorRemainder = new ArrayList<>();
                while (vectorIterator.hasNext()) {
                    vectorRemainder.add(vectorIterator.next());
                }
            }
        }
        return result;
    }

    /**
     * matchAll
     * find all the matches
     * for each, remember context, what matched & what didn't - in MatchDetails instance
     *
     * @param s
     */

    public boolean matchAll(String s) {
        matchDetailsList.clear(); //initialmatchdetails empty
        boolean result = false; // becomes true if at least 1 match found

        for (MString nextVector : vector) {
            boolean result1 = nextVector.match(s);
            if (result1) {
                result = true;
                List<MString> vectorCopy = new ArrayList<>(vector);
                vectorCopy.remove(nextVector);
                MatchDetails matchDetails = new MatchDetails(
                        nextVector.getContext(),
                        nextVector.getBaseString(),
                        new MStringVector(vectorCopy));
                this.matchDetailsList.add(matchDetails);
            }
        }
        return result;
    }

    /**
     * matchAll
     * find all the matches
     * given an initial context
     * for each, remember context, what matched & what didn't - in MatchDetails instance
     *
     * @param s
     * @param context
     */

    public boolean matchAll(String s, Map<String, Object> context) {
        matchDetailsList.clear(); //initialmatchdetails empty
        boolean result = false; // becomes true if at least 1 match found

        for (MString nextVector : vector) {
            boolean result1 = nextVector.match(s, context);
            if (result1) {
                result = true;
                ArrayList<MString> cloneV = new ArrayList<>(vector);
                cloneV.remove(nextVector);
                MatchDetails matchDetails = new MatchDetails(
                        nextVector.getContext(),
                        nextVector.getBaseString(),
                        new MStringVector(cloneV));
                this.matchDetailsList.add(matchDetails);
            }
        }
        return result;
    }

    /**
     * mSubst
     * substitute for given context
     *
     * @param c
     */

    public List<String> mSubst(Map<String, Object> c) {
        List<String> ans = new ArrayList<>();
        for (MString vit : vector) {
            ans.add(vit.mSubst(c));
        }
        return ans;
    }

    /**
     * toString
     * return v as a String
     */

    public String toString() {
        StringBuilder res = new StringBuilder();
        for (MString ms : vector) {
            res.append("\n").append(ms.getBaseString());
        }
        return res.toString();
    }

    /**
     * isEmpty
     * is v empty?
     */

    public Boolean isEmpty() {
        return vector.isEmpty();
    }

    /**
     * pop
     * pops v
     */

    public MString pop() {
        MString tms = vector.get(0);
        vector.remove(0);
        return tms;

    }

    /**
     * add
     * add a string to v
     */

    public void add(String s) {
        vector.add(new MString(s));
    }
}


