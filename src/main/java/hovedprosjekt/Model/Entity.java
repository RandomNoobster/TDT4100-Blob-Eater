package hovedprosjekt.Model;

public abstract class Entity {
    protected double xPos;
    protected double yPos;

    /**
     * Creates a new {@link Entity} object.
     * @param xPos
     * @param yPos
     */
    public Entity(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * @return the x-position and y-position of the entity
     */
    public double[] getPosition() {
        return new double[] { this.xPos, this.yPos };
    }

    /**
     * Sets the position of the entity
     * @param position
     */
    public void setPosition(double[] position) {
        this.xPos = position[0];
        this.yPos = position[1];
    }
}
