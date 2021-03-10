package org.kumsal.ficomSoft;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.kairos.core.Activity;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class MainPageController extends Activity {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button main_page_home;

    @FXML
    private Button main_page_load;

    @FXML
    private Button main_page_current;

    @FXML
    private Button main_page_folders;

    @FXML
    private Button main_page_print;

    @FXML
    private Button main_page_destroy;


    @FXML
    private AnchorPane mainFragment;

    private String currentFragment="";

    @FXML
    void main_page_currentFiles(ActionEvent event) {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().add("currentButton");
        main_page_print.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().remove("currentButton");
        main_page_load.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().remove("currentButton");

    }

    @FXML
    void onDestroy(ActionEvent event) {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().add("currentButton");
        main_page_print.getStyleClass().remove("currentButton");
        main_page_load.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().remove("currentButton");

        Transition transition=new FadeTransition(Duration.millis(1000));
//        transition.

    }

    @FXML
    void onLoad(ActionEvent event) throws IOException {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_load.getStyleClass().add("currentButton");
        main_page_print.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().remove("currentButton");
        close("Load.fxml");
       currentFragment="Load.fxml";
        access=true;
    }
    boolean access=true;
    ArrayList<String> stack=new ArrayList<>();
    AnchorPane pane=null;
    void close(String name) throws IOException {

//        AnchorPane pane=FXMLLoader.load(getClass().getResource(currentFragment));
//        mainFragment.getChildren().remove(0);
//        mainFragment.getChildren().add(pane);
        FadeTransition transition=new FadeTransition(Duration.millis(500),pane);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        transition.play();
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    if (access){
                        AnchorPane pane= null;
                        try {
                            pane = FXMLLoader.load(getClass().getResource(name));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mainFragment.getChildren().add(pane);
                        transition.setNode(pane);
                        transition.setFromValue(0.0);
                        transition.setToValue(1.0);
                        transition.play();
                        access=false;
                    }
            }
        });
    }

    @FXML
    void onPrint(ActionEvent event) {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_print.getStyleClass().add("currentButton");
        main_page_load.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().remove("currentButton");

    }

    @FXML
    void showFolder(ActionEvent event) {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().add("currentButton");
        main_page_load.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().remove("currentButton");
        main_page_print.getStyleClass().remove("currentButton");
    }

    @FXML
    void initialize() throws IOException {


        pane=FXMLLoader.load(getClass().getResource("home.fxml"));
        currentFragment="home.fxml";
        mainFragment.getChildren().add(pane);
        main_page_home.getStyleClass().add("currentButton");
        main_page_home.setOnMouseClicked(mouseEvent -> {
            main_page_load.getStyleClass().remove("currentButton");
            main_page_home.getStyleClass().add("currentButton");
            main_page_print.getStyleClass().remove("currentButton");
            main_page_destroy.getStyleClass().remove("currentButton");
            main_page_current.getStyleClass().remove("currentButton");
            main_page_folders.getStyleClass().remove("currentButton");
            try {
                close("Home.fxml");
                currentFragment=("Home.fxml");
                access=true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}