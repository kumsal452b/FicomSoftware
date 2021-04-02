package org.kumsal.ficomSoft;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("primary.fxml"));
        AnchorPane root=loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) throws ParseException, IOException {
//        launch();
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File("src/main/resources/org/kumsal/ficomSoft/files/Stage 1 - W.W. Jackobs - The Monkeys Paw.pdf"));
    }

}