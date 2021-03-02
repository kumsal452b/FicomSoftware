package org.kumsal.ficomSoft;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
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
    private JFXButton login_screen_button;

    @FXML
    void login(ActionEvent event) {

    }


    @FXML
    void initialize() throws MalformedURLException, URISyntaxException, InterruptedException {
        fadeTransition = new FadeTransition(Duration.millis(4000));
        fadeTransition.setNode(prim_imageView);
        fadeTransition2 = new FadeTransition(Duration.millis(4000));
        fadeTransition2.setNode(prim_imageView);
        currentPath="src/main/resources/org/kumsal/ficomsoft/image/image"+count+".jpg";
        changeImage(currentPath);

        setFadeFromAnim();
        double newMeasure = Math.max(prim_imageView.getImage().getWidth(), prim_imageView.getImage().getHeight());
        double x = (prim_imageView.getImage().getWidth() - newMeasure) / 2;
        double y = (prim_imageView.getImage().getHeight() - newMeasure) / 2;


        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        prim_imageView.setViewport(rect);
        prim_imageView.setFitWidth(600);
        prim_imageView.setFitHeight(500);
        prim_imageView.setSmooth(true);
        double a=main_anchor_pane.prefWidthProperty().divide(2).subtract(prim_imageView.fitWidthProperty().divide(2).get()).get();
        prim_imageView.xProperty().bind(main_anchor_pane.prefWidthProperty().divide(2).subtract(prim_imageView.fitWidthProperty().divide(2)));
//        prim_imageView.setX(500);
    }
    public void changeImage(String path){
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        prim_imageView.setImage(image);
        double newMeasure = Math.min(prim_imageView.getImage().getWidth(), prim_imageView.getImage().getHeight());
        double x = (prim_imageView.getImage().getWidth() - newMeasure) / 2;
        double y = (prim_imageView.getImage().getHeight() - newMeasure) / 2;
        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        prim_imageView.setViewport(rect);
        prim_imageView.setFitWidth(600);
        prim_imageView.setFitHeight(500);
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
            setFadeFromAnim();
        });
        fadeTransition.play();
    }
}
