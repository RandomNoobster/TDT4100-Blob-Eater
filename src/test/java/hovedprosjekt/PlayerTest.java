package hovedprosjekt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hovedprosjekt.Model.Player;
import hovedprosjekt.Utils.Vars.Movement;

public class PlayerTest {
    @Test
    @DisplayName("Test contructing a player")
    void testPlayerConstructor() {
        Player player = new Player();
        double[] position = player.getPosition();
        assertEquals(0, position[0]);
        assertEquals(0, position[1]);
        assertEquals(Movement.NONE, player.getMovement());
    }

    @Test
    @DisplayName("Test setting and getting movement")
    void testPlayerMovement() {
        Player player = new Player();
        player.setMovement(Movement.LEFT);
        assertEquals(Movement.LEFT, player.getMovement());
        player.setMovement(Movement.RIGHT);
        assertEquals(Movement.RIGHT, player.getMovement());
        player.setMovement(Movement.NONE);
        assertEquals(Movement.NONE, player.getMovement());
    }

    @Test
    @DisplayName("Test moving the player left and right")
    void testPlayerPosition() {
        Player player = new Player(0,0);
        int speed = player.getSpeed();
        player.moveLeft();
        assertEquals(0, player.getPosition()[0]);
        player.moveRight();
        assertEquals(speed, player.getPosition()[0]);
        player = new Player(500,0);
        player.moveRight();
        assertEquals(500-30, player.getPosition()[0]);
        player.moveLeft();
        assertEquals(500-speed-30, player.getPosition()[0]);
    }
}
