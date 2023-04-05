package hovedprosjekt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hovedprosjekt.Model.Computer;
import hovedprosjekt.Model.Player;
import hovedprosjekt.Utils.Value;

public class ComputerTest {
    private Computer computer;

    @BeforeEach
    public void constructComputer() {
        computer = new Computer();
    }

    @Test
    @DisplayName("Test contructing a computer")
    public void testConstructor() {
        assertEquals(500, computer.getHeight());
        assertEquals(500, computer.getWidth());
        assertEquals(5, computer.getLivesCount());
        assertEquals(0, computer.getEntities().size());
        assertEquals(Player.class, computer.getPlayer().getClass());
    }

    @Test
    @DisplayName("Test incrementing score")
    public void testScore() {
        Value score = computer.getScore();
        assertEquals(0, score.getValue());
        score.incrementValue(1);
        assertEquals(1, score.getValue());
        assertThrows(IllegalArgumentException.class, () -> score.incrementValue(-2));
    }

    @Test
    @DisplayName("Test setting and getting high score")
    public void testHighScore() {
        assertThrows(IllegalArgumentException.class, () -> computer.setHighScore(-1));
        assertDoesNotThrow(() -> computer.setHighScore(46));
        computer.setHighScore(46);
        assertEquals(46, computer.getHighScore());
    }

    @Test
    @DisplayName("Test incrementing lives")
    public void testLives() {
        Value lives = computer.getLives();
        assertEquals(5, lives.getValue());
        lives.incrementValue(-1);
        assertEquals(4, lives.getValue());
        assertThrows(IllegalArgumentException.class, () -> lives.incrementValue(-5));
    }
}
