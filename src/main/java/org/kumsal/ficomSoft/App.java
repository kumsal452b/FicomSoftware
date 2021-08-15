package org.kumsal.ficomSoft;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("image/genclık.jpg")));
        stage.show();

        ComboBox<String> comm=new ComboBox<>();
        comm.itemsProperty().addListener(new ChangeListener<ObservableList<String>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<String>> observableValue, ObservableList<String> strings, ObservableList<String> t1) {

            }
        });
    }


    public static void main(String[] args) throws ParseException, IOException {

        SpinnerValueFactory<Integer> spinner=new SpinnerValueFactory.IntegerSpinnerValueFactory()


        launch();
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        s+=System.getProperty("pathConf");
        System.out.println(s);
        File file=new File(s);
        InputStream inputStream=new FileInputStream(file);
        if (file.exists()){
            System.out.println("dosya mevcut" +file.getName());
            int a=inputStream.read();
            System.out.println(a);
        }else {
            System.out.println("dosya bulunamadi");
        }
    }

}