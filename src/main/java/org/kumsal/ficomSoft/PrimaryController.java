package org.kumsal.ficomSoft;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class PrimaryController {

    @FXML
    public ImageView prim_imageView;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private ScrollPane scrolpane1;
    @FXML
    private AnchorPane main_anchor_pane;


    @FXML
    void initialize() throws MalformedURLException, URISyntaxException {
        File file = new File("src/main/resources/org/kumsal/ficomsoft/image/image1.jpg");
        Image image = new Image(file.toURI().toString());
        prim_imageView.setImage(image);
        prim_imageView.setOnMouseClicked(mouseEvent -> {
                    setFadeFromAnim();
                }
        );
    }
    public void changeImage(String path){
        
    }
    public void setFadeFromAnim() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000));
        fadeTransition.setNode(prim_imageView);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(actionEvent -> {
            setFadeToAnim();

        });
        fadeTransition.play();
    }
    public void setFadeToAnim() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000));
        fadeTransition.setNode(prim_imageView);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(actionEvent -> {
            setFadeFromAnim();
        });
        fadeTransition.play();
    }
}
