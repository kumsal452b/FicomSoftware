package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    MysqlDataSource dbsource= ConnectorMysql.connect();
    ObservableList<settingModel> theusersList;
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
                        resultSet.getString(5)
                );
                theusersList.add(themodel);
            }
            table.getItems().addAll(theusersList);
            table.getSelectionModel().selectedItemProperty().addListener((observableValue, settingModel, t1) -> {
                ad.setText(t1.getName());
                soyad.setText(t1.getSurname());
                usernamegir.setText(t1.getUsername());
                password.setText(t1.getPassword());
            });
        }
    }
}
