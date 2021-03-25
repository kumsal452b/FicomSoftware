package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class set {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> username;

    @FXML
    private JFXTextField usernamegir;

    @FXML
    private JFXTextField ad;

    @FXML
    private JFXTextField soyad;

    @FXML
    private JFXTextField password;

    @FXML
    private JFXButton gunceller;

    @FXML
    private Label kullanicitur;

    @FXML
    private Label gunlukdosya;

    @FXML
    private Label toplamdosya;

    @FXML
    private Label rozetler;

    @FXML
    void initialize() {


    }
}
