package model;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * This class is used to store coordinates
 */
public class Position {
    private Integer x;
    private Integer y;

    private Position(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public boolean checkIfOnPlateau(@Nonnull Plateau plateau) {
        Objects.requireNonNull(plateau);
        return x >= 0 && x <= plateau.getX() && y >= 0 && y <= plateau.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != null ? !x.equals(position.x) : position.x != null) return false;
        return y != null ? y.equals(position.y) : position.y == null;
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Position{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }

    public static Builder build() {
        return new Builder();
    }

    public static final class Builder {
        private Integer x;
        private Integer y;

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

        public Position builder() {
            return new Position(this);
        }
    }
}
