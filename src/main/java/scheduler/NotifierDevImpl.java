package scheduler;

import org.apache.log4j.Logger;

/** Реалізація інтерфейса сповіщувача профіля тест. */
public class NotifierDevImpl implements Notifier {

    /** Логер. */
    private static Logger logger = Logger.getLogger(NotifierDevImpl.class);

    public void execute() {
        logger.info("Executing notifications...");
    }
}
