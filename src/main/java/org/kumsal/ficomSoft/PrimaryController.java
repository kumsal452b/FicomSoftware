package org.kumsal.ficomSoft;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    void initialize() {
    prim_imageView.setImage(new Image(new URL()));

    }
}
