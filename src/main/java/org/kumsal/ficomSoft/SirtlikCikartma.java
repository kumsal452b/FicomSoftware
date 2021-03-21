package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class SirtlikCikartma {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<SirtlikModel> table;

    @FXML
    private TableColumn<SirtlikModel,JFXCheckBox> ısCheck;

    @FXML
    private TableColumn<SirtlikModel, String> destisno;

    @FXML
    private TableColumn<SirtlikModel, String> birimad;

    @FXML
    private TableColumn<SirtlikModel, String> spdkod;

    @FXML
    private TableColumn<SirtlikModel, String> spdkarsilik;

    @FXML
    private TableColumn<SirtlikModel, String> ozelkod;

    @FXML
    private TableColumn<SirtlikModel, String> ozelkarsilik;

    @FXML
    private TableColumn<SirtlikModel, String> klasno;

    @FXML
    private TableColumn<SirtlikModel, String> ktarihi;

    @FXML
    private TableColumn<SirtlikModel, String> aciklama;

    @FXML
    private TableColumn<SirtlikModel, String> yuktarihi;

    @FXML
    private JFXButton yazdir;

    @FXML
    private JFXRadioButton oprion1;

    @FXML
    private JFXDatePicker first;

    @FXML
    private JFXDatePicker seccond;

    @FXML
    private JFXRadioButton option2;

    @FXML
    private JFXDatePicker onlyDate;

    @FXML
    void initialize() {
        onlyDate.setDisable(true);
        ısCheck.setCellValueFactory(new PropertyValueFactory<>("ısCheck"));
        destisno.setCellValueFactory(new PropertyValueFactory<>("destisno"));
        spdkod.setCellValueFactory(new PropertyValueFactory<>("spdkod"));
        spdkarsilik.setCellValueFactory(new PropertyValueFactory<>("spdkarsilik"));
        ozelkod.setCellValueFactory(new PropertyValueFactory<>("ozelkod"));
        ozelkarsilik.setCellValueFactory(new PropertyValueFactory<>("ozelkarsilik"));
        klasno.setCellValueFactory(new PropertyValueFactory<>("klasno"));
        ktarihi.setCellValueFactory(new PropertyValueFactory<>("ktarihi"));
        aciklama.setCellValueFactory(new PropertyValueFactory<>("aciklama"));
        yuktarihi.setCellValueFactory(new PropertyValueFactory<>("yuktarihi"));

        ToggleGroup group=new ToggleGroup();
        group.getToggles().add(option2);
        group.getToggles().add(oprion1);

//        Group group1=new Group();
//        group1.getChildren().add(oprion1);
//        group1.getChildren().add(option2);

    }
}
