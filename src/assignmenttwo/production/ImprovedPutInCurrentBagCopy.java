package assignmenttwo.production;

import assignmenttwo.logger.LoggerHandler;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * bagger 1 prodns
 * put an item in the current bag
 * 2013 Version Phil Green
 * <p>
 * 2020 Heidi Christensen (heidi.christensen@sheffield.ac.uk)
 */
public class ImprovedPutInCurrentBagCopy implements Production {
    private static final Logger LOGGER = LoggerHandler.getLogger();

    private static final String NAME = "Put In Current Bag";
    private static final String[] ANTECEDENTS = {"current step is: bag an item", "item to bag: ?I of space: ?S", "current bag is number ?N with space: ?BS", "remaining bag space list: ?RBSL"};
    private static final String[] ADDITIONS = {"current step is: get the next item", "bag number ?N contains ?I", "current bag is number ?N with space: ?RS", "remaining bag space list: ?NEW_RBSL"};
    private static final String[] DELETIONS = {"current step is: bag an item", "item to bag: ?I of space: ?S", "current bag is number ?N with space: ?BS", "remaining bag space list: ?RBSL"};
    private static final String[] REMARKS = {"Item ?I is now in bag number ?N"};

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
        int spaceLeft = Integer.parseInt((String) context.get("?BS"));
        int spaceNeeded = Integer.parseInt((String) context.get("?S"));
        return (spaceLeft >= spaceNeeded);
    }

    public Map<String, Object> modifyContext(Map<String, Object> context) {
        int spaceLeft = Integer.parseInt((String) context.get("?BS"));
        int spaceNeeded = Integer.parseInt((String) context.get("?S"));
        context.put("?RS", String.valueOf(spaceLeft - spaceNeeded));


        String gotRBSL = (String) context.get("?RBSL");
        String[] splitedRBSL = gotRBSL.split("_");
        int lengthOfRBSL = splitedRBSL.length;
        //String[] RBSList = new String[lengthOfRBSL];
        for(int i = 0; i<lengthOfRBSL; i++){
            int tempRBS = Integer.parseInt(splitedRBSL[i]);
            splitedRBSL[i] = String.valueOf(tempRBS-spaceNeeded);
            LOGGER.log(Level.INFO, "Step3: splitedRBSL["+i+"] = "+splitedRBSL[i]);
        }

        String newRBSL = "";
        int lengthOfnewRBSL = newRBSL.length();
        for(int i = 0; i<lengthOfnewRBSL; i++){
            newRBSL = newRBSL.concat(splitedRBSL[i]).concat("_");
        }
        context.put("?NEW_RBSL", String.valueOf(newRBSL));


        //System.out.println("getRemainBagSpace = "+getRemainBagSpace);
        LOGGER.log(Level.INFO, "Step3: gotRBSL = "+gotRBSL);
        return context;
    }

}
