package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SirtlikCikartma {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<> table;

    @FXML
    private TableColumn<?, ?> ısCheck;

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
    private JFXButton yazdir;

    @FXML
    private JFXCheckBox oprion1;

    @FXML
    private JFXDatePicker first;

    @FXML
    private JFXDatePicker seccond;

    @FXML
    private JFXCheckBox option2;

    @FXML
    private JFXDatePicker onlyDate;

    @FXML
    void initialize() {

    }
}
