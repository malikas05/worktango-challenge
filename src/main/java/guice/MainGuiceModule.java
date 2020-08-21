package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.MainProcessor;
import processor.Processor;
import processor.impl.InstructionProcessor;
import processor.impl.LandingProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Guice Module to bind dependencies
 */
public class MainGuiceModule extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(MainGuiceModule.class);

    @Override
    protected void configure() {
        logger.info("Binding Scanner");
        bind(Scanner.class).toInstance(new Scanner(System.in));

        logger.info("Binding LandingProcessor");
        bind(Processor.class)
                .annotatedWith(Names.named("LandingProcessor"))
                .to(LandingProcessor.class);
        logger.info("Binding InstructionProcessor");
        bind(Processor.class)
                .annotatedWith(Names.named("InstructionProcessor"))
                .to(InstructionProcessor.class);

        logger.info("Binding MainProcessor");
        bind(Processor.class)
                .annotatedWith(Names.named("MainProcessor"))
                .to(MainProcessor.class)
                .asEagerSingleton();
    }

    @Provides
    @Singleton
    public Plateau createPlateau(Scanner scanner) {
        System.out.print("Please enter the plateau dimensions in the following format: (X, Y) where X and Y should be greater than 0: ");
        String plateauCoorStr = scanner.nextLine().trim();
        return Plateau.createPlateauInstance(plateauCoorStr);
    }

    @Provides
    @Singleton
    public List<Rover> createRovers(Scanner scanner, Plateau plateau) {
        System.out.print("Please enter the number of Rovers you are planning to land: ");
        String numberOfRoversStr = scanner.nextLine().trim();
        int numberOfRovers = Integer.parseInt(numberOfRoversStr);
        if (numberOfRovers > plateau.getX() * plateau.getY()) {
            throw new IllegalArgumentException("The number of Rovers you entered is greater than the available plateau grid size.");
        } else if (numberOfRovers <= 0) {
            throw new IllegalArgumentException("The number of Rovers you entered cannot be less than 0.");
        }

        List<Rover> roverList = new ArrayList<>();
        System.out.println(String.format("Please enter the landing coordinates for %d Rovers in the following format: " +
                "X Y D where X - x coordinate greater than 0, Y - y coordinate greater than 0 and D - is the direction(N - north, S - south, W - west or E - east. " +
                "Once you are done with the coordinates for single Rover, please enter 'Enter' key. " +
                "After that please enter navigation instructions for %d Rovers in the following format: LMLMLMLMM where L - turn left, R - turn right and M - move forward. " +
                "Once you are done with the instructions for single Rover, please enter 'Enter' key.", numberOfRovers, numberOfRovers));
        for (int i = 0; i < numberOfRovers; i++) {
            System.out.print(String.format("Rover %d. Landing Coordinates: ", i));
            String roverLandingCoorStr = scanner.nextLine().trim();
            String[] roverLandingCoorArr = roverLandingCoorStr.split(" ");
            if (roverLandingCoorArr.length != 3) {
                throw new IllegalArgumentException("The landing coordinates should be entered in the following format: (X Y D).");
            }
            int xRoverCoor = Integer.parseInt(roverLandingCoorArr[0]);
            int yRoverCoor = Integer.parseInt(roverLandingCoorArr[1]);

            Direction direction = Direction.fromDirection(roverLandingCoorArr[2]);
            if (direction == null) {
                throw new IllegalArgumentException("The direction must be one of the following choices: N - north, S - south, W - west and E - east.");
            }

            System.out.print(String.format("Rover %d. Navigation Instructions: ", i));
            String navInstructionsStr = scanner.nextLine().trim();
            if (navInstructionsStr.length() <= 0) {
                throw new IllegalArgumentException("Navigation instructions cannot be empty.");
            }

            roverList.add(Rover.build()
                    .id(String.format("Rover%d", i))
                    .position(Position.build()
                            .x(xRoverCoor)
                            .y(yRoverCoor)
                            .builder())
                    .direction(direction)
                    .instructions(Instructions.retrieveInstructionsList(navInstructionsStr))
                    .builder());
        }

        return roverList;
    }
}
