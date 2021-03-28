package org.kumsal.ficomSoft;

import com.jfoenix.controls.*;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

public class set {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<settingModel> table;

    @FXML
    private TableColumn<settingModel, String> username;

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
    private JFXPasswordField eskisıfre;

    @FXML
    private JFXPasswordField yenisifre;

    @FXML
    private JFXButton sifredegistir;

    @FXML
    private JFXPasswordField yeniKullaniciAdi;

    @FXML
    private JFXButton kullaniciAdiDegistir;

    @FXML
    private CheckBox isAuth;
    MysqlDataSource dbsource= ConnectorMysql.connect();
    ObservableList<settingModel> theusersList;
    private int GlobalID=0;
    ObservableList<LoadedFileModel> theFileModel;
    int dailyLoged=0;
    int totalLoged=0;
    @FXML
    void initialize() throws SQLException {

        theusersList= FXCollections.observableArrayList();
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        if (PrimaryController.type.equals("Admin")){
            PreparedStatement statement=dbsource.getConnection().prepareStatement("select * from users");
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                settingModel themodel=new settingModel(
                        resultSet.getString(4),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(5),
                        resultSet.getBoolean(6),
                        resultSet.getInt(1)
                );
                theusersList.add(themodel);
            }
            table.getItems().addAll(theusersList);
            table.getSelectionModel().selectedItemProperty().addListener((observableValue, settingModel, t1) -> {
                ad.setText(t1.getName());
                soyad.setText(t1.getSurname());
                usernamegir.setText(t1.getUsername());
                password.setText(t1.getPassword());
                isAuth.setSelected(t1.isAuth());
                GlobalID=t1.getId();
            });
            gunceller.setOnAction(event -> {
                PreparedStatement updateUers= null;
                if (ad.getText().equals("")) {
                    Notifications.create()
                            .title("Hata")
                            .text("Ad boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (soyad.getText().equals("")) {
                    Notifications.create()
                            .title("Hata")
                            .text("Soyad boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (usernamegir.getText().equals("")) {
                    Notifications.create()
                            .title("Hata")
                            .text("Kullanıcı adı boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                for (settingModel themodel:theusersList){
                    if (themodel.getUsername().equals(usernamegir.getText())){
                        Notifications.create()
                                .title("Hata")
                                .text("Aynı Kullanıcı adı zaten mevcut.")
                                .hideAfter(Duration.seconds(3))
                                .position(Pos.BASELINE_LEFT)
                                .showWarning();
                        return;
                    }
                }
                if (ad.getText().equals("")) {
                    Notifications.create()
                            .title("Hata")
                            .text("Özel kod karşılık boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                try {
                    updateUers = dbsource.getConnection().prepareStatement("UPDATE `users` SET `ad` = ?, `soyad` = ?, `username` = ?, `password` = ? , `isAuth` =? WHERE `users`.`UID` = ?");
                    updateUers.setString(1,ad.getText());
                    updateUers.setString(2,soyad.getText());
                    updateUers.setString(3,usernamegir.getText());
                    updateUers.setString(4,password.getText());
                    updateUers.setBoolean(5,isAuth.isSelected());
                    updateUers.setInt(6,GlobalID);
                    updateUers.execute();
                    Notifications.create()
                            .title("Başarılı")
                            .text("Kullanıcı başarılı bir şekilde güncellendi.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showConfirm();
                } catch (SQLException throwables) {
                    Notifications.create()
                            .title("Hata")
                            .text("Güncelleme sırasında bir hata oluştu, lütden daha sonra tekrar deneyiniz..")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    throwables.printStackTrace();
                }

            });
        }
        PreparedStatement fileList=dbsource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.tarih,a.imhatarihi,a.LFID,a.OTID FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID WHERE own.username=?");
        fileList.setString(1,PrimaryController.username);
        ResultSet resultSet=fileList.executeQuery();
        LoadedFileModel loadedFile;
        int sira=1;
        JFXButton sil;
        JFXButton degistir;
        while (resultSet.next()){
            loadedFile=new LoadedFileModel(
                    String.valueOf(sira),
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    null,
                    null
            );
            theFileModel.add(loadedFile);
            totalLoged++;
        }
        toplamdosya.setText(totalLoged+"");
        kullanicitur.setText(PrimaryController.type);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        for (LoadedFileModel model:theFileModel){
            Date date=Date.valueOf(model.getYuktarihi());
            if (isToday(date)){
                dailyLoged++;
            }
        }
        gunlukdosya.setText(dailyLoged+"");
    }
    public boolean isToday(Date date){
        LocalDate toDay=date.toLocalDate();
        if (toDay.toString().equals(date.toLocalDate().toString())){
            return true;
        }
        else{
            return false;
        }
    }
}
