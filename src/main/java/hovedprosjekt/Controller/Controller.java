package hovedprosjekt.Controller;

import java.util.List;

import hovedprosjekt.Model.Computer;
import hovedprosjekt.Model.Entity;
import hovedprosjekt.Model.Player;
import hovedprosjekt.Utils.Value;
import hovedprosjekt.Utils.Vars.Movement;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Controller {
    // Dette er egentlig all tilstanden vi har i Controller
    private Thread gameThread = new Thread();
    private boolean gameActive;
    private final Image sprite = new Image("/hovedprosjekt/media/heart.png");

    // under er objekter som allerede er opprettet i Computer.java. Feltene er her
    // for å gjøre de lettere å referere til.
    private Computer computer;
    private Player player;
    private Value score;
    private Value lives;
    private List<Entity> entities;

    @FXML
    private Text scoreField;

    @FXML
    private Canvas canvas;

    @FXML
    private Text gameOverText;

    @FXML
    private Text yourScoreText;

    @FXML
    private Button restartButton;

    @FXML
    private AnchorPane background;

    @FXML
    private HBox livesField;

    @FXML
    private HBox topWrapper;

    /**
     * Creates a new {@link Controller} object.
     */
    public Controller() {
        computer = new Computer();
        player = computer.getPlayer();
        score = computer.getScore();
        lives = computer.getLives();
        entities = computer.getEntities();
    }

    /**
     * This is run after the constructor, and it calls {@link Computer#reset()}.
     */
    @FXML
    public void initialize() {
        computer.reset();
    }

    /**
     * Creates and starts a new {@code Thread} with a loop that runs the game. It
     * runs the game logic calculations and updates the GUI.
     */
    public void startGameLoop() {
        background.requestFocus();
        gameActive = true;
        gameThread = new Thread(() -> {
            while (gameActive) {
                computer.tick();

                this.updateGUI();
                if (computer.gameOver()) {
                    gameActive = false;
                    break;
                }

                try {
                    Thread.sleep(10); // 60 frames per second = 16 millis
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.gameOver();
        });

        // Start the game thread
        gameThread.start();
    }

    /**
     * Stops the game loop by setting {@code gameActive} to {@code false} and
     * joining the {@code gameThread}.
     */
    public void stopGameLoop() {
        gameActive = false;
        try {
            gameThread.join();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Linked to the {@code Restart}-button in the view. It calls
     * {@link Computer#reset()}, restarts the game loop and resets some GUI
     * elements.
     */
    public void restart() {
        computer.reset();
        this.stopGameLoop();
        this.startGameLoop();

        gameOverText.setVisible(false);
        yourScoreText.setVisible(false);
        restartButton.setVisible(false);

        scoreField.setText("0");
        topWrapper.setVisible(true);
    }

    /**
     * Function meant to be run when the game ends. It updates some GUI elements and
     * updates the highscore if applicable.
     */
    public void gameOver() {
        gameOverText.setVisible(true);
        yourScoreText.setVisible(true);
        restartButton.setVisible(true);
        int highScore = computer.getHighScore();
        String text;
        if (score.getValue() > highScore) {
            text = "You set a new highscore of " + score.getValue() + " points!";
            computer.setHighScore(score.getValue());
        } else if (score.getValue() == highScore) {
            text = "You got the same as the\nold highscore of " + score.getValue() + " points!";
        } else {
            text = "You were " + (highScore - score.getValue()) + " points away\nfrom the highscore, loser";
        }
        yourScoreText.setText(text);
        topWrapper.setVisible(false);
    }

    /**
     * Function that draws the game entities to the canvas. It also updates the
     * score and lives fields.
     */
    public void updateGUI() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Canvas buffer = new Canvas(500, 500);
        GraphicsContext bgc = buffer.getGraphicsContext2D();

        bgc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        bgc.setFill(Color.web("#154B52"));
        entities.forEach(entity -> {
            double[] position = entity.getPosition();
            bgc.fillOval(position[0], position[1], 10, 10);
        });

        double[] playerPosition = player.getPosition();
        bgc.setFill(Color.web("#154B52"));
        bgc.fillRect(playerPosition[0], playerPosition[1], 30, 30);

        // ettersom vi kaller updateGUI fra en annen Thread enn JavaFX Application
        // Thread, må vi bruke Platform.runlater() for å kunne kjøre snapshot()
        Platform.runLater(() -> {

            int currentDisplayedLives = livesField.getChildren().size();

            for (int i = currentDisplayedLives; i < lives.getValue(); i++) {
                ImageView imageView = new ImageView(sprite);
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                livesField.getChildren().add(imageView);
            }
            for (int i = currentDisplayedLives; i > lives.getValue(); i--) {
                livesField.getChildren().remove(livesField.getChildren().size() - 1);
            }

            // code that updates the JavaFX UI
            gc.drawImage(buffer.snapshot(null, null), 0, 0);
        });
        scoreField.setText("" + score);
    }

    /**
     * Handles key presses. It sets the {@code movement} of the {@code player} to
     * {@code LEFT} or {@code RIGHT} depending on which key was pressed.
     * 
     * @param event
     */
    @FXML
    public void handleOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            player.setMovement(Movement.LEFT);
        } else if (event.getCode() == KeyCode.RIGHT) {
            player.setMovement(Movement.RIGHT);
        }
    }

    /**
     * Handles key releases. It sets the {@code movement} of the {@code player} to
     * {@code NONE} if the key released was the same as the one that was pressed.
     * 
     * @param event
     */
    @FXML
    public void handleOnKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT && player.getMovement() == Movement.LEFT) {
            player.setMovement(Movement.NONE);
        } else if (event.getCode() == KeyCode.RIGHT && player.getMovement() == Movement.RIGHT) {
            player.setMovement(Movement.NONE);
        }
    }
}
