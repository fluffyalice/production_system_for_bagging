package assignmenttwo.trolley;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author pdg
 * @version 1.00 2017/3/22
 * <p>
 * 2020 Heidi Christensen (heidi.christensen@sheffield.ac.uk)
 * @(#)TrolleyGen.java Generate shopping trolleys for Bagger problems
 * Usage - supply random seed, number of shopping items
 * max space for an item is 100
 * pmatch.trolley.TrolleyGen tg = new pmatch.trolley.TrolleyGen(12345,10);
 * String[]trolley= tg.fillTrolley();
 * Returns a String Array of 10 items
 * trolley contains item1 space 51
 * trolley contains item2 space 80
 * trolley contains item3 space 41
 * trolley contains item4 space 28
 * trolley contains item5 space 55
 * trolley contains item6 space 84
 * trolley contains item7 space 75
 * trolley contains item8 space 2
 * trolley contains item9 space 1
 * trolley contains item10 space 89
 */
public class TrolleyGen {

    private static final Logger LOGGER = Logger.getLogger(TrolleyGen.class.getName());
    private Random randomNumberGenerator;
    private int numberOfItems;
    private String[] contents;

    public TrolleyGen(int seed, int numberOfItems) {
        randomNumberGenerator = new Random(seed);
        this.numberOfItems = numberOfItems;
        contents = new String[this.numberOfItems];

        fillTrolley();
    }

    private void fillTrolley() {

        LOGGER.log(Level.INFO, "{0}", numberOfItems);
        for (int i = 1; i <= numberOfItems; i++) {
            contents[i - 1] = "the trolley contains " + i + " of space: " + randomNumberGenerator.nextInt(100);
        }
    }

    public String[] getTrolleyContent() {
        return contents;
    }
}
