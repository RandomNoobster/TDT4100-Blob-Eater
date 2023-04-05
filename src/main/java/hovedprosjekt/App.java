package hovedprosjekt;

import java.io.IOException;

import hovedprosjekt.Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainView.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);
        Parent root = loader.load();

        primaryStage.setTitle("Blob Eater");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Stop the game loop when the window is closed
        primaryStage.setOnCloseRequest(event -> {
            controller.stopGameLoop();
            System.out.println("Window closed");
        });

        controller.startGameLoop();
    }

}
