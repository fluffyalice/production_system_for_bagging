package assignmenttwo;

import assignmenttwo.logger.LoggerHandler;
import assignmenttwo.trolley.TrolleyGen;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TestTrolleyGen
 * testing pmatch.trolley.TrolleyGen
 * <p>
 * 2013 Phil Green
 * <p>
 * 2020 Heidi Christensen (heidi.christensen@sheffield.ac.uk)
 */
public class RunTrolleyGen {
    private static final Logger LOGGER = LoggerHandler.getLogger();

    public static void main(String[] arg) {

        final int seed = 12345;
        final int numberOfItems = 10;

        TrolleyGen trolley = new TrolleyGen(seed, numberOfItems);
        String[] trolleyContent = trolley.getTrolleyContent();

        LOGGER.log(Level.INFO, "{0}", trolleyContent);
    }
}
