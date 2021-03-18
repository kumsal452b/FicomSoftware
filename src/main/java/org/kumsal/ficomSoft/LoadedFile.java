package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LoadedFile {

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> sira;

    @FXML
    private TableColumn<?, ?> destisno;

    @FXML
    private TableColumn<?, ?> birimad;

    @FXML
    private TableColumn<?, ?> spdkod;

    @FXML
    private TableColumn<?, ?> spdkarsilik;

    @FXML
    private TableColumn<?, ?> ozelkod;

    @FXML
    private TableColumn<?, ?> ozelkarsilik;

    @FXML
    private TableColumn<?, ?> klasno;

    @FXML
    private TableColumn<?, ?> ktarihi;

    @FXML
    private TableColumn<?, ?> aciklama;

    @FXML
    private TableColumn<?, ?> yuktarihi;

    @FXML
    private TableColumn<?, JFXButton> sil;

    @FXML
    private TableColumn<?, JFXButton> desgistir;

    @FXML
    private JFXTextField ara;

    @FXML
    void initialize() {


    }

}
