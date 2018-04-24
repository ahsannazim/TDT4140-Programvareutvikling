package tdt4140.gr1878.app.ui;

/**
 * Created by Polakken97
 * 2. mar. 2018
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage someStage) throws IOException {
        someStage.setTitle("JavaFX");
        Label label = new Label("Hello World!");
        StackPane root = new StackPane();
        root.getChildren().add(label);
        someStage.setScene(new Scene(root, 250, 250));
        someStage.show();
    }
}
