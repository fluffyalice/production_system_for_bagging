package assignmenttwo;

import assignmenttwo.logger.LoggerHandler;
import assignmenttwo.production.ProductionSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TestProductionSystem
 * testing production systems
 * 2013 Version Phil Green
 * <p>
 * 2020 Heidi Christensen (heidi.christensen@sheffield.ac.uk)
 */
public class RunProductionSystem {
    private static final Logger LOGGER = LoggerHandler.getLogger();

    public static void main(String[] arg) {

        // initial facts; feel free to amend them however you want
        List<String> shortTermMemory = new ArrayList<>();
        shortTermMemory.add("current step is: start bagging");
        shortTermMemory.add("the trolley contains cherry of space: 50");
        shortTermMemory.add("the trolley contains filbert of space: 65");
        shortTermMemory.add("the trolley contains mushroom of space: 40");
        shortTermMemory.add("the trolley contains corn of space: 20");
        shortTermMemory.add("the trolley contains peach of space: 55");
        shortTermMemory.add("the trolley contains mango of space: 45");
        shortTermMemory.add("the trolley contains milk of space: 40");
        shortTermMemory.add("the trolley contains pitaya of space: 75");
        shortTermMemory.add("the trolley contains apple of space: 95");
        shortTermMemory.add("the trolley contains cabbage of space: 15");
        shortTermMemory.add("the trolley contains kiwi of space: 100");
        shortTermMemory.add("the trolley contains grape of space: 25");
        shortTermMemory.add("the trolley contains blueberry of space: 60");
        shortTermMemory.add("the trolley contains tomato of space: 5");
        shortTermMemory.add("the trolley contains lemon of space: 70");
        shortTermMemory.add("the trolley contains eggplant of space: 90");
        shortTermMemory.add("the trolley contains carrot of space: 35");
        shortTermMemory.add("the trolley contains cucumber of space: 30");
        shortTermMemory.add("the trolley contains kumquat of space: 80");
        shortTermMemory.add("the trolley contains potato of space: 85");

        ProductionSystem productionSystem = new ProductionSystem();
        List<String> result = productionSystem.runProductionSystem(shortTermMemory);
        LOGGER.log(Level.INFO, "Final Short Term Memory: {0}", result);
    }
}
