package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private CheckBox isAuth;
    MysqlDataSource dbsource= ConnectorMysql.connect();
    ObservableList<settingModel> theusersList;
    private int GlobalID=0;
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
    }
}
