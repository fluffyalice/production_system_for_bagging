package assignmenttwo.production;

import assignmenttwo.logger.LoggerHandler;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ImprovedChooseCorrectBag.java
 * 2020.5.11
 * @author Ruiqing Xu
 */
public class ImprovedChooseCorrectBag implements Production {
    private static final Logger LOGGER = LoggerHandler.getLogger();

    private static final String NAME = "Start New Bag";
    private static final String[] ANTECEDENTS = {"current step is: bag an item", "item to bag: ?I of space: ?S", "current bag is number ?N with space: ?BS", "remaining bag space list: ?RBSL"};
    private static final String[] ADDITIONS = {"current bag is number ?NB with space: 100",  "remaining bag space list: ?NEW_RBSL"};
    private static final String[] DELETIONS = {"current bag is number ?N with space: ?BS", "item to bag: ?I of space: ?S",  "remaining bag space list: ?RBSL"};
    private static final String[] REMARKS = {"Starting to use bag number ?NB"};

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
     * compare itemSpace with each remaining bag space(RBS), use for loop
     * if bag RS > itemSpace, then choose correct bag to put in, ?NB = BagNO;
     * if item cannot put in all the bag, then start new bag, ?NB = ?NB + 1
     * @param context
     * @return
     */
    public Map<String, Object> modifyContext(Map<String, Object> context) {
        int bagNumber = Integer.parseInt((String) context.get("?N")); // bag number = ?N

        String itemName = (String) context.get("?I"); // item name = ?I
        int itemSpace = Integer.parseInt((String) context.get("?S")); // item space = ?S

        String gotRBSL = (String) context.get("?RBSL"); // got remaining bag space list
        String[] splitedRBSL = gotRBSL.split("_"); // splited RBSL with "_" in the array
        int lengthOfRBSL = splitedRBSL.length;

        int assignedBN = bagNumber + 1; // assigned bag number = bag number + 1 by default
        String NEWRBSL = gotRBSL.concat("100_"); // concat newRBSL with "100_"
        for(int i = 0; i<lengthOfRBSL; i++){
            int tempRS = Integer.parseInt(splitedRBSL[i]);
            if(tempRS >= itemSpace){
                assignedBN = i+1;
                NEWRBSL = gotRBSL;
                break;
            }
        }

        LOGGER.log(Level.INFO, "Step4: gotRBSL = " + gotRBSL+",  NEWRBSL = "+NEWRBSL);

        context.put("?NEW_RBSL", String.valueOf(NEWRBSL));

        LOGGER.log(Level.INFO, "Step4: oldBN = "+bagNumber+", item = "+itemName+", itemSpace = "+itemSpace+", assignedBN = "+assignedBN);
        context.put("?NB", String.valueOf(assignedBN));

        return context;
    }
}
