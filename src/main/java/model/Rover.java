package model;

import java.util.List;

/**
 * This class is used to store the data of single Rover
 */
public class Rover {
    private String id;
    private Position position;
    private Direction direction;
    private List<Instruction> instructions;

    private Rover(Builder builder) {
        this.id = builder.id;
        this.position = builder.position;
        this.direction = builder.direction;
        this.instructions = builder.instructions;
    }

    public String getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rover{");
        sb.append("id='").append(id).append('\'');
        sb.append(", position=").append(position);
        sb.append(", direction=").append(direction);
        sb.append(", instructions=").append(instructions);
        sb.append('}');
        return sb.toString();
    }

    public static Builder build() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private Position position;
        private Direction direction;
        private List<Instruction> instructions;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder position(Position position) {
            this.position = position;
            return this;
        }

        public Builder direction(Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder instructions(List<Instruction> instructions) {
            this.instructions = instructions;
            return this;
        }

        public Rover builder() {
            return new Rover(this);
        }
    }
}
