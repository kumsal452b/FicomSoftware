package org.kumsal.ficomSoft;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import javax.swing.*;

public class Folders{

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

    @FXML
    private StackPane stacpane;

    @FXML
    private JFXButton iptal;

    MysqlDataSource dbSource= ConnectorMysql.connect();
    ObservableList<foldersModel> foldersModels1;
    ArrayList<JFXButton> buttonsSil=new ArrayList<>();
    ArrayList<JFXButton> buttonsDegistir=new ArrayList<>();
    ArrayList<Integer> folderIDs=new ArrayList<>();
    ArrayList<Integer> folderIndex=new ArrayList<>();
    boolean isUpdate=false;
    int globalIndex=0;
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
        ekle.setOnMouseClicked(mouseEvent -> {
           if (isUpdate){
               if (!destisno_giriniz.getText().equals("") && destisno_giriniz.getText()!=null){
                   SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                   SimpleDateFormat myFormatTime = new SimpleDateFormat("HH:mm:ss");
                   Date dt=new Date();
                   String date=myFormat.format(dt);
                   String time=myFormatTime.format(dt);
                   try {
                       PreparedStatement preparedStatement=dbSource.getConnection().prepareStatement("UPDATE `destis` SET `destisno`=? , `kayitT` = ?,`kayitS`=? WHERE `destis`.`DID` =?; ");
                        preparedStatement.setString(1,destisno_giriniz.getText());
                        preparedStatement.setString(2,date);
                        preparedStatement.setString(3,time);
                        preparedStatement.setInt(4,globalIndex);
                        preparedStatement.execute();
                       Notifications.create()
                               .title("Başarılı")
                               .text("Destis no güncellendi.")
                               .hideAfter(Duration.seconds(3))
                               .position(Pos.CENTER_LEFT)
                               .showConfirm();
                       iptal.setVisible(false);
                       ekle.setText("Ekle");
                       isUpdate=false;
                        destisno_giriniz.setText("");

                   } catch (SQLException throwables) {
                       Notifications.create()
                               .title("Hata")
                               .text("Bilinmeyen bir hatameydana geldi.")
                               .hideAfter(Duration.seconds(3))
                               .position(Pos.CENTER_LEFT)
                               .showError();
                       throwables.printStackTrace();

                   }

               }else{
                   Notifications.create()
                           .title("Hata")
                           .text("Destis no boş bırakılamaz.")
                           .hideAfter(Duration.seconds(3))
                           .position(Pos.CENTER_LEFT)
                           .showError();
               }
           }
           else {
               SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
               SimpleDateFormat myFormatTime = new SimpleDateFormat("HH:mm:ss");
               Date dt=new Date();
               String date=myFormat.format(dt);
               String time=myFormatTime.format(dt);


               try {
                   if (!destisno_giriniz.getText().equals("") && destisno_giriniz.getText()!=null){
                       PreparedStatement savedFolder=dbSource.getConnection().prepareStatement("INSERT INTO `destis` (`DID`, `destisno`, `kayitT`, `kayitS`) VALUES (NULL, ?, ?, ?)");
                       savedFolder.setString(1,destisno_giriniz.getText());
                       savedFolder.setString(2,date);
                       savedFolder.setString(3,time);
                       savedFolder.execute();
                       Notifications.create()
                               .title("Başarılı")
                               .text("Klasör kaydedildi")
                               .hideAfter(Duration.seconds(3))
                               .position(Pos.BASELINE_LEFT)
                               .showConfirm();
                       destisno_giriniz.setText("");
                   }else{
                       Notifications.create()
                               .title("Hata")
                               .text("Destis no boş bırakılamaz.")
                               .hideAfter(Duration.seconds(3))
                               .position(Pos.CENTER_LEFT)
                               .showError();
                   }
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }
           }

        });
        FilteredList<foldersModel> filteredList=new FilteredList<>(foldersModels1, b -> true);
        ara.textProperty().addListener((observableValue, s, t1) -> {
            filteredList.setPredicate(loadedFileModel->{
                if (t1==null || t1.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = t1.toLowerCase();
                if (loadedFileModel.getDestisno().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else if (loadedFileModel.getYuklemeSaati().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                else if (String.valueOf(loadedFileModel.getYuktarihi()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false; // Does not match.

            });
        });
        SortedList<foldersModel> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
        iptal.setOnAction(event ->{
            ekle.setText("Ekle");
            isUpdate=false;
            iptal.setVisible(false);
            destisno_giriniz.setText("");
        });
    }

    private void updateList(ResultSet resultSet, int index) throws SQLException {
        JFXButton degistir;
        JFXButton sil;
        int theIndex=index;

        while (resultSet.next()){
            sil=new JFXButton("Sil");
            sil.getStyleClass().add("deleteButton");
            degistir=new JFXButton("Değiştir");
            degistir.getStyleClass().add("changeButton");
            buttonsSil.add(sil);
            buttonsDegistir.add(degistir);
            sil.setOnAction(event -> {
                JFXButton theButton=(JFXButton) event.getSource();
                int currentIndex=buttonsSil.indexOf(theButton);
                JFXButton evet=new JFXButton("Evet");
                JFXDialog dialog=new JFXDialog(stacpane,new Label("temel"), JFXDialog.DialogTransition.CENTER);
                JFXDialogLayout layout=new JFXDialogLayout();
                layout.setHeading(new Text("Dikkat"));
                layout.setBody(new Text("Bu satır silinecek. Devam etmek ister misiniz? Bu işlem geri alınamaz."));

                evet.setOnAction(event1 -> {
                    foldersModels1.remove(currentIndex);
                    buttonsSil.remove(currentIndex);
                    buttonsDegistir.remove(currentIndex);
                    try {
                        PreparedStatement preparedStatement=dbSource.getConnection().prepareStatement(
                                "DELETE FROM `destis` WHERE `destis`.`DID` = ?"
                        );
                        preparedStatement.setInt(1,folderIndex.get(currentIndex));
                        preparedStatement.execute();
                        folderIndex.remove(currentIndex);
                        Notifications.create()
                                .title("Başarılı")
                                .text("Klasör silindi")
                                .hideAfter(Duration.seconds(3))
                                .position(Pos.BASELINE_LEFT)
                                .showConfirm();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    dialog.close();
                });
                JFXButton iptal=new JFXButton("Iptal");
                iptal.setOnAction(event1 -> {
                        dialog.close();

                });
                layout.setActions(evet,iptal);
                dialog.setContent(layout);
                dialog.show();

            });
            degistir.setOnAction(event -> {
               int indexOf= buttonsDegistir.indexOf(event.getSource());
               foldersModel model=foldersModels1.get(indexOf);
               destisno_giriniz.setText(model.getDestisno());
               globalIndex=folderIDs.get(indexOf);
               isUpdate=true;
               ekle.setText("Güncelle");
               iptal.setVisible(true);
            });
            folderIndex.add(resultSet.getInt(1));
            foldersModel foldersModel=new foldersModel(String.valueOf(theIndex),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    sil,
                    degistir
                    );
            folderIDs.add(resultSet.getInt(1));
            foldersModels1.add(foldersModel);
            theIndex++;
        }
    }
}
