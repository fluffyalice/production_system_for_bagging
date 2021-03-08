package assignmenttwo.production;

import java.util.Map;

/**
 * ImprovedStartBagging.java
 * 2020.5.11
 * @author Ruiqing Xu
 */
public class ImprovedStartBagging implements Production {
    private static final String NAME = "Start Bagging";
    private static final String[] ANTECEDENTS = {"current step is: start bagging"};
    private static final String[] ADDITIONS = {"current step is: get the next item", "current bag is number 1 with space: 100", "remaining bag space list: ?RBSL"};
    private static final String[] DELETIONS = {"current step is: start bagging"};
    private static final String[] REMARKS = {"Starting to bag"};

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

    public boolean canRunInContext(Map<String, Object> context) {
        return true;
    }

    /**
     * ?RBSL means remaining bag space list
     * "100_" means the remain Bag space, it will change like: 100_60_30, depends on the remaining space of each bag.
     * "_" the symbol means compartments the remaining space of each bag，this is because I want to put all remaining space together and it is also easy to splited each part with the "_"
     * and "100" means the original bag space
     * @param context
     * @return
     */
    public Map<String, Object> modifyContext(Map<String, Object> context) {
        String remainBagSpace = "100_";
        context.put("?RBSL", String.valueOf(remainBagSpace)); //put ?RBSL into remainBagSpace

        return context;
    }

}
