package hovedprosjekt.Model;

import hovedprosjekt.Utils.Vars.Movement;

public class Player extends Entity {
    private Movement movement = Movement.NONE;
    private int speed = 8;

    /**
     * Creates a new {@link Player} object.
     */
    public Player() {
        super(0, 0);
    }

    /**
     * Creates a new {@link Player} object.
     * @param xPos
     * @param yPos
     */
    public Player(double xPos, double yPos) {
        super(xPos, yPos);
    }

    /**
     * Sets the movement of the player
     */
    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    /**
     * @return the movement of the player
     */
    public Movement getMovement() {
        return this.movement;
    }

    /**
     * Gets the speed of the player
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Moves the player to the left by the speed
     */
    public void moveLeft() {
        if (xPos - speed < 0) {
            this.xPos = 0;
        } else {
            this.xPos -= speed;
        }
    }

    /**
     * Moves the player to the right by the speed
     */
    public void moveRight() {
        if (xPos + speed >= 500 - 30) {
            this.xPos = 500 - 30;
        } else {
            this.xPos += speed;
        }
    }
}
