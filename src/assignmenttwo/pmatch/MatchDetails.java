package assignmenttwo.pmatch;

import java.util.HashMap;
import java.util.Map;

/**
 * MatchDetails.java
 * result from a successful match against an element of an MStringVector
 * context, element that matched, rest of the vector
 */
public class MatchDetails {
    private Map<String, Object> context;
    private String matcher;
    private MStringVector remainingAntecedents;

    public MatchDetails(Map<String, Object> context, String matcher, MStringVector r) {
        this.context = new HashMap<>(context);
        this.matcher = matcher;
        remainingAntecedents = new MStringVector(r.getVector());
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public String getMatcher() {
        return matcher;
    }

    public MStringVector getRemainingAntecedents() {
        return remainingAntecedents;
    }

    /**
     * setRest
     * reset the rest
     *
     * @param remainingAntecedents
     */

    public void setRemainingAntecedents(MStringVector remainingAntecedents) {
        this.remainingAntecedents = new MStringVector(remainingAntecedents.getVector());
    }
}
