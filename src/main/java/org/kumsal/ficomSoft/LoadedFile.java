package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LoadedFile {

    @FXML
    private TableView<LoadedFileModel> table;

    @FXML
    private TableColumn<LoadedFileModel, String> sira;

    @FXML
    private TableColumn<LoadedFileModel, String> destisno;

    @FXML
    private TableColumn<LoadedFileModel, String> birimad;

    @FXML
    private TableColumn<LoadedFileModel, String> spdkod;

    @FXML
    private TableColumn<LoadedFileModel, String> spdkarsilik;

    @FXML
    private TableColumn<LoadedFileModel, String> ozelkod;

    @FXML
    private TableColumn<LoadedFileModel, String> ozelkarsilik;

    @FXML
    private TableColumn<LoadedFileModel, String> klasno;

    @FXML
    private TableColumn<LoadedFileModel, String> ktarihi;

    @FXML
    private TableColumn<LoadedFileModel, String> aciklama;

    @FXML
    private TableColumn<LoadedFileModel, String> yuktarihi;

    @FXML
    private TableColumn<LoadedFileModel, JFXButton> sil;

    @FXML
    private TableColumn<LoadedFileModel, JFXButton> desgistir;

    @FXML
    private JFXTextField ara;

    @FXML
    void initialize() {


    }

}
