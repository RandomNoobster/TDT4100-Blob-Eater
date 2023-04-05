package hovedprosjekt.Model;

import java.util.ArrayList;
import java.util.List;

import hovedprosjekt.Utils.Value;
import hovedprosjekt.Utils.Vars.Movement;

public class Computer {
    private static Player player = new Player();
    private static List<Entity> entities = new ArrayList<Entity>();
    private static Value score = new Value(0, 0, null);
    private static Value lives = new Value(5, 0, null);
    private static double width = 500.0;
    private static double height = 500.0;

    /**
     * Creates a new {@link Computer} object and runs {@link #reset()}
     */
    public Computer() {
        reset();
    }

    /**
     * Resets the game; deletes all entities, sets lives to 5 and score to 0, and
     * sets player location to the middle of the screen.
     */
    public void reset() {
        entities.clear();
        lives.setValue(5);
        score.setValue(0);
        player.setPosition(new double[] { width / 2 - 15, height - 30 });
    }

    /**
     * Updates the game state; Spawns new blobs, updates positions for blobs and
     * player, and checks for collisions in order to update score and lives.
     */
    public void tick() {
        // spawn new blobs
        if (Math.random() < 0.010) {
            spawnBlob();
        }

        // update positions for blobs
        EntityListIterator iterator = new EntityListIterator(entities);
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            double[] newPos = newPosition(entity.getPosition());
            entity.setPosition(newPos);

            // check for collisions
            if (isColliding(player, entity)) {
                entities.remove(entity);
                score.incrementValue(1);
                System.out.println(score);
            } else if (entity.getPosition()[1] >= 500) {
                entities.remove(entity);
                lives.incrementValue(-1);
            }
        }

        // update position for player
        Movement playerMovement = player.getMovement();
        if (playerMovement == Movement.LEFT) {
            player.moveLeft();
        } else if (playerMovement == Movement.RIGHT) {
            player.moveRight();
        }
    }

    /**
     * @return true if the game is over (lives = 0), false otherwise
     */
    public boolean gameOver() {
        if (lives.getValue() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the highscore from the highscore.txt file
     */
    public int getHighScore() {
        return new HighScore().getHighScore();
    }

    /**
     * Sets the highscore in the highscore.txt file
     * @param number
     */
    public void setHighScore(int number) {
        new HighScore().setHighScore(number);
    }

    /**
     * @return the current amount of lives
     */
    public int getLivesCount() {
        return lives.getValue();
    }

    /**
     * @return the object containing the current lives
     */
    public Value getLives() {
        return lives;
    }

    /**
     * @return the object containing the current score
     */
    public Value getScore() {
        return score;
    }

    /**
     * @return the player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the list of entities
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * @return the height of the game area
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the width of the game area
     */
    public double getWidth() {
        return width;
    }

    /**
     * Spawns a new {@link NormalBlob} at a random x-position and at the top of the
     * screen
     */
    public void spawnBlob() {
        Entity newBlob = new NormalBlob(Math.random() * width, 0.0, 1);
        entities.add(newBlob);
    }

    /**
     * Calculates the new position for an entity
     * 
     * @param position
     * @return the new position
     */
    public double[] newPosition(double[] position) {
        double[] newPosition = new double[2];
        newPosition[0] = position[0];
        newPosition[1] = position[1] + 3;
        return newPosition;
    }

    /**
     * Checks if two entities are colliding
     * 
     * @param player
     * @param entity
     * @return true if the entities are colliding, false otherwise
     */
    public boolean isColliding(Player player, Entity entity) {
        double[] playerPosition = player.getPosition();
        double[] entityPosition = entity.getPosition();

        double distance = Math.sqrt(Math.pow(Math.abs(playerPosition[0] + 15 - entityPosition[0] - 5), 2)
                + Math.pow(Math.abs(playerPosition[1] + 15 - entityPosition[1] - 8), 2));

        if (distance < 20) {
            return true;
        } else {
            return false;
        }
    }
}
