package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Home {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton letsgo_button;

    @FXML
    private Text login_type;

    @FXML
    private Text auth;

    @FXML
    void initialize() throws IOException {
    letsgo_button.setOnMouseClicked(mouseEvent -> {

//            MainPageController nesne=new MainPageController();
//            nesne.getMain_page_current().setText("Selam  evlat");
//                System.out.println("tamamdir");
            }
            );

    }
}
