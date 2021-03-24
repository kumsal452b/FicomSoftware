package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
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
        updateList(resultSet, index);
        table.getItems().addAll(foldersModels1);
        ekle.setOnMouseClicked(mouseEvent -> {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-mm-dd");
            SimpleDateFormat myFormatTime = new SimpleDateFormat("HH:mm:ss");
            Date dt=new Date();
            String date=myFormat.format(dt);
            String time=myFormatTime.format(dt);

            try {
               if (destisno_giriniz.getText()!="" && destisno_giriniz.getText()!=null){
                   PreparedStatement savedFolder=dbSource.getConnection().prepareStatement("INSERT INTO `destis` (`DID`, `destisno`, `kayitT`, `kayitS`) VALUES (NULL, ?, ?, ?)");
                   savedFolder.setString(1,destisno_giriniz.getText());
                   savedFolder.setString(2,date);
                   savedFolder.setString(3,time);
                   if (savedFolder.execute()){
                       Notifications.create()
                               .title("Başarılı")
                               .text("Klasör kaydedildi")
                               .hideAfter(Duration.seconds(3))
                               .position(Pos.BASELINE_LEFT)
                               .showConfirm();
                       updateList(resultSet, index);

                   }
               }else{
                   Notifications.create()
                           .title("Hata")
                           .text("Destis no boş bırakılamaz.")
                           .hideAfter(Duration.seconds(3))
                           .position(Pos.CENTER_LEFT)
                           .showConfirm();
               }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
    }

    private void updateList(ResultSet resultSet, int index) throws SQLException {
        JFXButton degistir;
        JFXButton sil;
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
    }
}
