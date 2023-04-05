package hovedprosjekt.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class HighScore {
    public HighScore() {
    }

    /**
     * @return the highscore from the highscore.txt file
     */
    public int getHighScore() {
        int number = 0;
        try (Scanner scanner = new Scanner(getClass().getResource("/hovedprosjekt/data/highscore.txt").openStream())) {
            number = scanner.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number;
    }

    /**
     * Sets the highscore in the highscore.txt file
     * 
     * @param number
     */
    public void setHighScore(int number) {
        if (number < 0) {
            throw new IllegalArgumentException();
        }
        try (FileWriter writer = new FileWriter(new File(
                getClass().getResource("/hovedprosjekt/data/highscore.txt").toURI()))) {
            writer.write("" + number);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
