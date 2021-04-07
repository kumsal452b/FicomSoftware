package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kairos.core.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainPageController extends Activity {

    public JFXButton exit_app;
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
    private JFXButton settings;

    @FXML
    private AnchorPane mainPane;

    private String currentFragment = "";
    @FXML
    private StackPane stakpane;

    @FXML
    void main_page_currentFiles(ActionEvent event) {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().add("currentButton");
        main_page_print.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().remove("currentButton");
        main_page_load.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().remove("currentButton");
        settings.getStyleClass().remove("currentButton");
        try{
            close("loaded_file.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        access = true;
    }

    @FXML
    void onDestroy(ActionEvent event) throws IOException {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().add("currentButton");
        main_page_print.getStyleClass().remove("currentButton");
        main_page_load.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().remove("currentButton");
        settings.getStyleClass().remove("currentButton");
        close("destroyList.fxml");
        currentFragment = "destroyList.fxml";
        access = true;

        Transition transition = new FadeTransition(Duration.millis(1000));
//        transition.

    }

    public Button getMain_page_current() {
        return main_page_current;
    }

    @FXML
    void onLoad(ActionEvent event) throws IOException {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_load.getStyleClass().add("currentButton");
        main_page_print.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().remove("currentButton");
        settings.getStyleClass().remove("currentButton");
        close("load.fxml");
        access = true;
    }

    boolean access = true;
    ArrayList<String> stack = new ArrayList<>();
    AnchorPane pane = null;

    void close(String name) throws IOException {
        FadeTransition transition = new FadeTransition(Duration.millis(500), pane);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        transition.play();
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (access) {
                    Parent pane = null;
                    try {
                        FXMLLoader loader=new FXMLLoader();
                        pane = loader.load(getClass().getResourceAsStream(name));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mainFragment.getChildren().clear();
                    mainFragment.getChildren().add(pane);
                    transition.setNode(pane);
                    transition.setFromValue(0.0);
                    transition.setToValue(1.0);
                    transition.play();
                    access = false;
                }
            }
        });
    }

    @FXML
    void onPrint(ActionEvent event) throws IOException {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_print.getStyleClass().add("currentButton");
        main_page_load.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().remove("currentButton");
        settings.getStyleClass().remove("currentButton");
        close("sirtlikCikartma.fxml");
        currentFragment = "sirtlikCikartma.fxml";
        access = true;

    }

    @FXML
    void showFolder(ActionEvent event) throws IOException {
        main_page_home.getStyleClass().remove("currentButton");
        main_page_folders.getStyleClass().add("currentButton");
        main_page_load.getStyleClass().remove("currentButton");
        main_page_destroy.getStyleClass().remove("currentButton");
        main_page_current.getStyleClass().remove("currentButton");
        main_page_print.getStyleClass().remove("currentButton");
        settings.getStyleClass().remove("currentButton");
        close("folders.fxml");
        currentFragment = "folders.fxml";
        access = true;
    }

    @FXML
    void initialize() throws IOException {
        if (PrimaryController.type.equals("User")){
            main_page_destroy.setVisible(false);
        }
        exit_app.setOnAction(event -> {
            ButtonType foo = new ButtonType("Evet", ButtonBar.ButtonData.OK_DONE);
            ButtonType bar = new ButtonType("Hayir", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,"Oturum kapatılacak. Onaylıyormusunuz?",foo, bar);
            alert.setTitle("Uyarı");
            alert.setHeaderText("Uyarı");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(bar) == foo) {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();

                stage.close();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("primary.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.setResizable(false);
                stage.setHeight(530);
                stage.setWidth(1000);
                stage.setMinHeight(530);
                stage.setMinWidth(1000);
                stage.setMaximized(false);
                stage.show();
            }
        });
        settings.setOnAction(event -> {
            main_page_home.getStyleClass().remove("currentButton");
            main_page_folders.getStyleClass().remove("currentButton");
            main_page_load.getStyleClass().remove("currentButton");
            main_page_destroy.getStyleClass().remove("currentButton");
            main_page_current.getStyleClass().remove("currentButton");
            main_page_print.getStyleClass().remove("currentButton");
            settings.getStyleClass().add("currentButton");
            try {
                close("settings.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentFragment = "settings.fxml";
            access = true;
        });
        mainPane.widthProperty().addListener((observableValue, number, t1) -> {
            mainFragment.setPrefWidth(mainFragment.getWidth()+(t1.intValue()-1200));
            System.out.println(t1.intValue());
            System.out.println(number.intValue());
        });
        mainPane.heightProperty().addListener((observableValue, number, t1) -> {
            int calculate = (number.intValue() * 10) / 600;
        });


        pane = FXMLLoader.load(getClass().getResource("home.fxml"));
        currentFragment = "home.fxml";
        mainFragment.getChildren().add(pane);
        main_page_home.getStyleClass().add("currentButton");
        main_page_home.setOnMouseClicked(mouseEvent -> {
            main_page_home.getStyleClass().add("currentButton");
            main_page_current.getStyleClass().remove("currentButton");
            main_page_print.getStyleClass().remove("currentButton");
            main_page_destroy.getStyleClass().remove("currentButton");
            main_page_load.getStyleClass().remove("currentButton");
            main_page_folders.getStyleClass().remove("currentButton");
            settings.getStyleClass().remove("currentButton");
            try{
                close("home.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            access = true;
        });

    }
}
