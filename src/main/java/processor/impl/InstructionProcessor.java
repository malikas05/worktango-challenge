package processor.impl;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.Processor;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * This class is the implementation of Instructions Processor.
 * It is used to run the commands for each Rover on Plateau.
 */
public class InstructionProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(InstructionProcessor.class);

    private Plateau plateau;

    @Inject
    public InstructionProcessor(@Nonnull Plateau plateau) {
        this.plateau = Preconditions.checkNotNull(plateau, "Plateau cannot be null!");
    }

    @Override
    public void process() {
        logger.info("Starting InstructionProcessor...");
        startProcessingInstructions();
        plateau.printRoversPosition();
        logger.info("InstructionProcessor completed.");
    }

    private void startProcessingInstructions() {
        plateau.getRoverList().forEach(rover -> {
            List<Instruction> instructionList = rover.getInstructions();
            instructionList.forEach(instruction -> {
                switch (instruction) {
                    case LEFT:
                        makeLeftTurn(rover);
                        break;
                    case RIGHT:
                        makeRightTurn(rover);
                        break;
                    case MOVE:
                        makeMoveForward(rover);
                        break;
                }
            });
        });
    }

    private void makeLeftTurn(Rover rover) {
        Direction currentDirection = rover.getDirection();

        if (currentDirection == Direction.NORTH) {
            rover.setDirection(Direction.WEST);
            return;
        }

        if (currentDirection == Direction.SOUTH) {
            rover.setDirection(Direction.EAST);
            return;
        }

        if (currentDirection == Direction.WEST) {
            rover.setDirection(Direction.SOUTH);
            return;
        }

        if (currentDirection == Direction.EAST) {
            rover.setDirection(Direction.NORTH);
            return;
        }
    }

    private void makeRightTurn(Rover rover) {
        Direction currentDirection = rover.getDirection();

        if (currentDirection == Direction.NORTH) {
            rover.setDirection(Direction.EAST);
            return;
        }

        if (currentDirection == Direction.SOUTH) {
            rover.setDirection(Direction.WEST);
            return;
        }

        if (currentDirection == Direction.WEST) {
            rover.setDirection(Direction.NORTH);
            return;
        }

        if (currentDirection == Direction.EAST) {
            rover.setDirection(Direction.SOUTH);
            return;
        }
    }

    private void makeMoveForward(Rover rover) {
        Position oldPosition = rover.getPosition();
        Position newPosition = null;
        Position.Builder newPositionBuilder = Position.build();

        Direction currentDirection = rover.getDirection();
        if (currentDirection == Direction.NORTH) {
            newPositionBuilder
                    .x(oldPosition.getX())
                    .y(oldPosition.getY() + 1);
        }

        if (currentDirection == Direction.SOUTH) {
            newPositionBuilder
                    .x(oldPosition.getX())
                    .y(oldPosition.getY() - 1);
        }

        if (currentDirection == Direction.WEST) {
            newPositionBuilder
                    .x(oldPosition.getX() - 1)
                    .y(oldPosition.getY());
        }

        if (currentDirection == Direction.EAST) {
            newPositionBuilder
                    .x(oldPosition.getX() + 1)
                    .y(oldPosition.getY());
        }

        newPosition = newPositionBuilder.builder();

        if (!newPosition.checkIfOnPlateau(plateau)) {
            throw new RuntimeException(String.format("Current coordinates: X - %d, Y - %d are outside of plateau dimensions.", newPosition.getX(), newPosition.getY()));
        }

        if (plateau.isVacantSpot(newPosition)) {
            plateau.updatePosition(rover, newPosition, oldPosition);
        } else {
            throw new RuntimeException(String.format("Current coordinates: X - %d, Y - %d are occupied already.", newPosition.getX(), newPosition.getY()));
        }
    }
}
