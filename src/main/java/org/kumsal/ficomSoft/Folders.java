package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class Folders {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField ara;

    @FXML
    private Pane adminPanel;

    @FXML
    private JFXTextField destisno_giriniz;

    @FXML
    private JFXButton ekle;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> sira;

    @FXML
    private TableColumn<?, ?> destisno;

    @FXML
    private TableColumn<?, ?> kayıt_tarihi;

    @FXML
    private TableColumn<?, ?> kayıt_saati;

    @FXML
    private TableColumn<?, ?> sil;

    @FXML
    private TableColumn<?, ?> desgistir;

    @FXML
    void initialize() {

    }
}
