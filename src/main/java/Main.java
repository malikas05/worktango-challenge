import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import guice.MainGuiceModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.Processor;

/**
 * Main class to start application
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static Injector injector;

    public static void main(String[] args) {
        logger.info("_STARTING_");
        initGuiceModules();
        logger.info("_STARTED_");

        logger.info("_PROCESSING_");
        Processor mainProcessor = injector.getInstance(Key.get(Processor.class, Names.named("MainProcessor")));
        mainProcessor.process();
        logger.info("_PROCESSED_");
    }

    private static void initGuiceModules() {
        logger.info("Initializing Guice injector");
        injector = Guice.createInjector(
                new MainGuiceModule());
    }
}
