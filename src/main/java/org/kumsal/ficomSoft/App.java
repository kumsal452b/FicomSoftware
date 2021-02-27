package org.kumsal.ficomSoft;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane root=FXMLLoader.load(getClass().getResource("primary.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);

        stage.setMinHeight(250);
        stage.setMinWidth(500);
        stage.setMaxHeight(500);
        stage.setMaxWidth(1000);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}