package assignmenttwo.production;

import assignmenttwo.logger.LoggerHandler;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ImprovedPutInCurrentBag.java
 * 2020.5.11
 * @author Ruiqing Xu
 */
public class ImprovedPutInCurrentBag implements Production {
    private static final Logger LOGGER = LoggerHandler.getLogger();

    private static final String NAME = "Put In Current Bag";
    private static final String[] ANTECEDENTS = {"current step is: bag an item", "item to bag: ?I of space: ?S", "current bag is number ?N with space: ?BS", "remaining bag space list: ?RBSL"};
    private static final String[] ADDITIONS = {"current step is: get the next item", "item to bag: ?I of space: ?S", "bag number ?N contains ?I", "current bag is number ?N with space: ?RS", "remaining bag space list: ?NEW_RBSL"};
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

    /**
     * splitedRBSL means splited remaining bag space list
     * first, put the gotRBSL(got remaining bag space list) into String,  and splited each bag space with "_", and put the splited thing into array
     * second, use for loop to traverse each splited remaining bag space list
     * third, if i = current bag number, splited remaining bag space[i] value which in the array will change to the value of(splitedRBSL[i]-spaceNEeded)
     * finally, use concat method to concat all the RBSL together into string
     * @param context
     * @return
     */
    public Map<String, Object> modifyContext(Map<String, Object> context) {
        int spaceLeft = Integer.parseInt((String) context.get("?BS"));
        int spaceNeeded = Integer.parseInt((String) context.get("?S"));
        context.put("?RS", String.valueOf(spaceLeft - spaceNeeded));

        int currBagNum = Integer.parseInt((String) context.get("?N")); // current bag number = ?N
        int itemSpace = Integer.parseInt((String) context.get("?S"));  // item space = ?S

        String gotRBSL = (String) context.get("?RBSL"); // got ?RBSL(remaining bag space list)
        LOGGER.log(Level.INFO, "Step3: gotRBSL = "+gotRBSL);
        String[] splitedRBSL = gotRBSL.split("_"); // splited RBSL with "_" in the array

        int lengthOfRBSL = splitedRBSL.length;

        for(int i = 0; i<lengthOfRBSL; i++){
            int tempRBS = Integer.parseInt(splitedRBSL[i]);
            if(i == currBagNum-1){
                splitedRBSL[i] = String.valueOf(tempRBS - spaceNeeded); // if i = current bag number, splited remaining bag space[i] value
                                                                        // which in the array will change to the value of(splitedRBSL[i]-spaceNEeded)
            }
        }

        LOGGER.log(Level.INFO, "Step3: currBagNum = " + currBagNum + ", itemSpace = " + itemSpace);

        String newRBSL = "";
        for(int i = 0; i<lengthOfRBSL; i++){
             newRBSL= newRBSL.concat(splitedRBSL[i]).concat("_");  // use concat method to concat all the RBSL together into string
        }
        LOGGER.log(Level.INFO, "Step3: newRBSL = "+newRBSL);
        context.put("?NEW_RBSL", String.valueOf(newRBSL));

        return context;
    }

}
