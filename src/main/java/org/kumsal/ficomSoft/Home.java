package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
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
    void initialize() {
        InputStream fontStream = Home.class.getResourceAsStream("skyline1.tff");
        Font font=Font.loadFont(fontStream,50);
        auth.setFont(font);
        login_type.setFont(font);

    }
}
