package assignmenttwo.logger;

import java.text.MessageFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerHandler {
    private static final Logger LOGGER = Logger.getGlobal();

    private LoggerHandler() {
    }

    static {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        SimpleFormatter newFormatter = new SimpleFormatter() {
            private static final String FORMAT = "%1$s %n";

            @Override
            public synchronized String format(LogRecord record) {
                return String.format(FORMAT, MessageFormat.format(record.getMessage(), record.getParameters()));
            }
        };
        consoleHandler.setFormatter(newFormatter);
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(consoleHandler);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}