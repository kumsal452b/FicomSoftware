package org.kumsal.ficomSoft;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
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

    private int count=1;
    private String currentPath="";
    private FadeTransition fadeTransition;
    private FadeTransition fadeTransition2;

    @FXML
    void initialize() throws MalformedURLException, URISyntaxException {
        fadeTransition = new FadeTransition(Duration.millis(4000));
        fadeTransition.setNode(prim_imageView);

        fadeTransition2 = new FadeTransition(Duration.millis(4000));
        fadeTransition2.setNode(prim_imageView);
        currentPath="src/main/resources/org/kumsal/ficomsoft/image/image"+count+".jpg";
        changeImage(currentPath);
        prim_imageView.setOnMouseClicked(mouseEvent -> {
                    setFadeFromAnim();
                }
        );
        prim_imageView.scaleXProperty().
    }
    public void changeImage(String path){
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        prim_imageView.setImage(image);
    }
    public void setFadeFromAnim() {
        fadeTransition2.setFromValue(1);
        fadeTransition2.setToValue(0);
        fadeTransition2.setOnFinished(actionEvent -> {

            count++;
            if (count>3){
                count=1;
            }
            currentPath="src/main/resources/org/kumsal/ficomsoft/image/image"+count+".jpg";
            changeImage(currentPath);
            setFadeToAnim();
        });
        fadeTransition2.play();
    }
    public void setFadeToAnim() {
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(actionEvent -> {
//            count++;
//            if (count>3){
//                count=1;
//            }
//            currentPath="src/main/resources/org/kumsal/ficomsoft/image/image"+count+".jpg";
//            changeImage(currentPath);
            setFadeFromAnim();
        });
        fadeTransition.play();
    }
}
