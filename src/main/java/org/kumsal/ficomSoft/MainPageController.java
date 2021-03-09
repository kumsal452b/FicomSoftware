package org.kumsal.ficomSoft;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.kairos.core.Activity;


import java.io.IOException;
import java.net.URL;
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

    }

    @FXML
    void onLoad(ActionEvent event) {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_load.getStyleClass().add("currentButton");
        main_page_print.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().remove("currentButton");

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
        AnchorPane pane=FXMLLoader.load(getClass().getResource("home.fxml"));
        mainFragment.getChildren().add(pane);
        main_page_home.getStyleClass().add("currentButton");
        main_page_home.setOnMouseClicked(mouseEvent -> {
            main_page_load.getStyleClass().remove("currentButton");
            main_page_home.getStyleClass().add("currentButton");
            main_page_print.getStyleClass().remove("currentButton");
            main_page_destroy.getStyleClass().remove("currentButton");
            main_page_current.getStyleClass().remove("currentButton");
            main_page_folders.getStyleClass().remove("currentButton");
        });
    }
}
