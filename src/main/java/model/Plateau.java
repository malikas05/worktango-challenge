package model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * This class is used to store the dimensions of Plateau as well as the list of landed Rovers and their positions.
 */
public class Plateau {
    private static final Logger logger = LoggerFactory.getLogger(Plateau.class);

    private Integer x;
    private Integer y;
    private List<Rover> roverList;
    private Set<Position> positions;

    private Plateau(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.roverList = builder.roverList;
        this.positions = builder.positions;
    }

    public void printRoversPosition() {
        logger.info("Printing positions for Rovers...");
        for (Rover rover : roverList) {
            System.out.println(String.format("- Rover ID: %s \n- Position: X - %d, Y - %d, D - %s \n", rover.getId(), rover.getPosition().getX(), rover.getPosition().getY(), rover.getDirection().getDirection()));
        }
    }

    public void addRoverToPlateau(@Nonnull Rover rover) {
        Objects.requireNonNull(rover);
        if (isVacantSpot(rover.getPosition())) {
            roverList.add(rover);
            positions.add(rover.getPosition());
        }
    }

    public boolean isVacantSpot(@Nonnull Position position) {
        Objects.requireNonNull(position);
        return !positions.contains(position);
    }

    public void updatePosition(@Nonnull Rover rover, @Nonnull Position newPosition, @Nonnull Position oldPosition) {
        Objects.requireNonNull(rover);
        Objects.requireNonNull(newPosition);
        Objects.requireNonNull(oldPosition);

        rover.setPosition(newPosition);
        removeOldPosition(oldPosition);
        if (isVacantSpot(newPosition))
            positions.add(newPosition);
    }

    public boolean removeOldPosition(@Nonnull Position position) {
        Objects.requireNonNull(position);
        return positions.remove(position);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public List<Rover> getRoverList() {
        return List.copyOf(roverList);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Plateau{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", roverList=").append(roverList);
        sb.append(", positions=").append(positions);
        sb.append('}');
        return sb.toString();
    }

    public static Plateau createPlateauInstance(String plateauDimsStr) {
        String[] plateauDimsArr = plateauDimsStr.split(" ");
        if (plateauDimsArr.length != 2) {
            throw new IllegalArgumentException("The plateau dimensions should be entered in the following format: (X Y).");
        }
        int xPlateauDim = Integer.parseInt(plateauDimsArr[0]);
        int yPlateauDim = Integer.parseInt(plateauDimsArr[1]);
        if (xPlateauDim < 0 || yPlateauDim < 0) {
            throw new IllegalArgumentException("The plateau coordinates should be greater than 0.");
        }
        Plateau plateau = Plateau.build()
                .x(xPlateauDim)
                .y(yPlateauDim)
                .roverList(Lists.newArrayList())
                .positions(Sets.newHashSet())
                .builder();

        return plateau;
    }

    public static Builder build() {
        return new Builder();
    }

    public static final class Builder {
        private Integer x;
        private Integer y;
        private List<Rover> roverList;
        private Set<Position> positions;

        private Builder() {
        }

        public Builder x(Integer x) {
            this.x = x;
            return this;
        }

        public Builder y(Integer y) {
            this.y = y;
            return this;
        }

        public Builder roverList(List<Rover> roverList) {
            this.roverList = roverList;
            return this;
        }

        public Builder positions(Set<Position> positions) {
            this.positions = positions;
            return this;
        }

        public Plateau builder() {
            return new Plateau(this);
        }
    }
}
