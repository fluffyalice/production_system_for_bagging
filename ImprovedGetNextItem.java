package assignmenttwo.production;

import java.util.Map;

/**
 * ImprovedGetNextItem.java
 * 2020.5.11
 *  @author Ruiqing Xu
 */
public class ImprovedGetNextItem implements Production {
    private static final String NAME = "Get Next Item";
    private static final String[] ANTECEDENTS = {"current step is: get the next item", "the trolley contains ?I of space: ?S", "remaining bag space list: ?RBSL"};
    private static final String[] ADDITIONS = {"current step is: bag an item", "item to bag: ?I of space: ?S", "remaining bag space list: ?NEW_RBSL"};
    private static final String[] DELETIONS = {"current step is: get the next item", "the trolley contains ?I of space: ?S", "remaining bag space list: ?RBSL"};
    private static final String[] REMARKS = {"Bagging item: ?I"};

    public String getName() {
        return NAME;
    }

    public String[] getAntecedents() {
        return ANTECEDENTS;
    }

    public String[] getAdditions() {
        return ADDITIONS;
    }

    public String[] getDeletions() {
        return DELETIONS;
    }

    public String[] getRemarks() {
        return REMARKS;
    }

    public boolean canRunInContext(Map<String, Object> c) {
        return true;
    }

    /**
     * ?RBSL means remaining bag space list
     * ?NEW_RBSL means new remaining bag space list
     *
     * get ?RBSL and then put it into the ?NEW_RBSL
     * @param c
     * @return
     */
    public Map<String, Object> modifyContext(Map<String, Object> c) {
        String gotRBSL = (String) c.get("?RBSL"); // got ?RBSL(remaining bag space list)
        c.put("?NEW_RBSL", String.valueOf(gotRBSL));// put ?NEW_RBSL (new remaining bag space list) into the ?NEW_RBSL
        return c;
    }

}
