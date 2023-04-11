package hovedprosjekt.Model;

public class NormalBlob extends Entity {
    private int points;
    private double xPos;
    private double yPos;

    /**
     * Creates a new {@link NormalBlob} object.
     * @param xPos
     * @param yPos
     * @param points
     */
    public NormalBlob(double xPos, double yPos, int points) {
        super(xPos, yPos);
        setPoints(points);
    }

    /**
     * @return the points the player gets for killing this entity
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Sets the points the player gets for killing this entity
     * @param points
     */
    public void setPoints(int points) {
        this.points = points;
    }
}
