package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

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
    private TableView<foldersModel> table;

    @FXML
    private TableColumn<foldersModel,String> sira;

    @FXML
    private TableColumn<foldersModel,String> destisno;

    @FXML
    private TableColumn<foldersModel,String> kayıt_tarihi;

    @FXML
    private TableColumn<foldersModel,String> kayıt_saati;

    @FXML
    private TableColumn<foldersModel, JFXButton> sil;

    @FXML
    private TableColumn<foldersModel, JFXButton> desgistir;
    MysqlDataSource dbSource= ConnectorMysql.connect();
    ObservableList<foldersModel> foldersModels1;

    @FXML
    void initialize() throws SQLException {
        foldersModels1= FXCollections.observableArrayList();
        sira.setCellValueFactory(new PropertyValueFactory<>("sira"));
        destisno.setCellValueFactory(new PropertyValueFactory<>("destisno"));
        kayıt_saati.setCellValueFactory(new PropertyValueFactory<>("yuklemeSaati"));
        kayıt_tarihi.setCellValueFactory(new PropertyValueFactory<>("yuktarihi"));
        sil.setCellValueFactory(new PropertyValueFactory<>("sil"));
        desgistir.setCellValueFactory(new PropertyValueFactory<>("desgistir"));

        Statement folder=dbSource.getConnection().createStatement();
        ResultSet resultSet=folder.executeQuery("select * from destis");
        int index=1;
        JFXButton sil;
        JFXButton degistir;
        while (resultSet.next()){
            sil=new JFXButton("Sil");
            degistir=new JFXButton("Değiştir");
            foldersModel foldersModel=new foldersModel(String.valueOf(index),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    sil,
                    degistir
                    );
            foldersModels1.add(foldersModel);
        }
        table.getItems().addAll(foldersModels1);
        ekle.setOnMouseClicked(mouseEvent -> {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-mm-dd");
            SimpleDateFormat myFormatTime = new SimpleDateFormat("HH:mm:ss");
            


        });
    }
}
