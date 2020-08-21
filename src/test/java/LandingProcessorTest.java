import com.google.inject.*;
import com.google.inject.name.Names;
import model.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.Processor;
import processor.impl.LandingProcessor;

import java.util.ArrayList;
import java.util.List;

public class LandingProcessorTest {
    private static final Logger logger = LoggerFactory.getLogger(LandingProcessorTest.class);

    private static Injector injector;

    @BeforeClass
    public static void setUp() {
        logger.info("Initializing Guice injector");
        injector = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        logger.info("Binding LandingProcessor");
                        bind(Processor.class)
                                .annotatedWith(Names.named("LandingProcessor"))
                                .to(LandingProcessor.class);
                    }

                    @Provides
                    @Singleton
                    public Plateau createPlateau() {
                        String plateauCoorStr = "7 5";
                        return Plateau.createPlateauInstance(plateauCoorStr);
                    }

                    @Provides
                    @Singleton
                    public List<Rover> createRovers() {
                        List<Rover> roverList = new ArrayList<>();

                        roverList.add(Rover.build()
                                .id(String.format("Rover%d", 1))
                                .position(Position.build()
                                        .x(5)
                                        .y(4)
                                        .builder())
                                .direction(Direction.fromDirection("N"))
                                .instructions(Instructions.retrieveInstructionsList("LMLMLMLMM"))
                                .builder());

                        roverList.add(Rover.build()
                                .id(String.format("Rover%d", 2))
                                .position(Position.build()
                                        .x(8)
                                        .y(4)
                                        .builder())
                                .direction(Direction.fromDirection("W"))
                                .instructions(Instructions.retrieveInstructionsList("MMRMMRMRRM"))
                                .builder());

                        return roverList;
                    }
                });
    }

    @Test
    public void test() {
        Processor landingProcessor = injector.getInstance(Key.get(Processor.class, Names.named("LandingProcessor")));
        landingProcessor.process();
    }
}
