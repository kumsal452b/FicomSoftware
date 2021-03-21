package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXRadioButton;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

public class SirtlikCikartma {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<SirtlikModel> table;

    @FXML
    private TableColumn<SirtlikModel, JFXCheckBox> ısCheck;

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

    MysqlDataSource dbSource = ConnectorMysql.connect();

    @FXML
    void initialize() throws SQLException {
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


        ToggleGroup group = new ToggleGroup();
        group.getToggles().add(option2);
        group.getToggles().add(oprion1);
        oprion1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (oprion1.isSelected()) {
                    onlyDate.setDisable(true);
                    first.setDisable(false);
                    seccond.setDisable(false);
                }
            }
        });
        option2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (option2.isSelected()) {
                    onlyDate.setDisable(false);
                    first.setDisable(true);
                    seccond.setDisable(true);

                }
            }
        });
        PreparedStatement fileList = dbSource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.prossTime FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID WHERE own.username=?");
        fileList.setString(1, PrimaryController.username);

        ResultSet resultSet = fileList.executeQuery();
        SirtlikModel loadedFile;
        int sira = 1;
        JFXButton sil;
        while (resultSet.next()) {

//                loadedFile=new SirtlikModel(
//                        new JFXCheckBox(),
//                        resultSet.getString(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getString(5),
//                        resultSet.getString(6),
//                        resultSet.getString(7),
//                        resultSet.getString(8),
//                        resultSet.getString(9),
//                        resultSet.getString(10)
//                );

        }
//            table.add(loadedFile);
    }
}




