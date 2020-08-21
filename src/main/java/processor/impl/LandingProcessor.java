package processor.impl;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import model.Plateau;
import model.Rover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.Processor;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * This class is the implementation of Landing Processor.
 * It is used to land each Rover on Plateau.
 */
public class LandingProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(LandingProcessor.class);

    private Plateau plateau;
    private List<Rover> roverList;

    @Inject
    public LandingProcessor(@Nonnull Plateau plateau, @Nonnull List<Rover> roverList) {
        this.plateau = Preconditions.checkNotNull(plateau, "Plateau cannot be null!");
        this.roverList = Preconditions.checkNotNull(roverList, "RoverList cannot be null!");
    }

    @Override
    public void process() {
        logger.info("Starting LandingProcessor...");
        landRoversOnPlateau();
        logger.info("{} Rovers have been successfully landed out of {}.", plateau.getRoverList().size(), roverList.size());
        plateau.printRoversPosition();
        logger.info("LandingProcessor completed.");
    }

    private void landRoversOnPlateau() {
        for (Rover rover : roverList) {
            if (!rover.getPosition().checkIfOnPlateau(plateau)) {
                logger.warn("The landing coordinates should be greater than 0 and in the range of Plateau dimensions. We won't be landing this Rover: {}", rover.getId());
                continue;
            }
            if (!plateau.isVacantSpot(rover.getPosition())) {
                logger.warn("The landing coordinates are already occupied. We won't be landing this Rover: {}", rover.getId());
                continue;
            }

            plateau.addRoverToPlateau(rover);
        }
    }
}
