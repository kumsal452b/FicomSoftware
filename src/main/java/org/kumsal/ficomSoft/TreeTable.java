package org.kumsal.ficomSoft;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

public class TreeTable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TreeTableView<String> table;

    @FXML
    private TreeTableColumn<String,String> destisno;


    ObservableList<LoadedFileModel> theFileModel;
    MysqlDataSource dbSources= ConnectorMysql.connect();
    TreeItem<String> birim=new TreeItem<>("Birim");
    TreeItem<String> destisno1=new TreeItem<>("Destis No");
    TreeItem<String> imaheTarhihi=new TreeItem<>("Imha Tarihi");
    TreeItem<String> yukleme=new TreeItem<>("Yukleme Tarihi");
    TreeItem<String> spdkod=new TreeItem<>("SPD Kodu");

    @FXML
    void initialize() throws SQLException {
        theFileModel= FXCollections.observableArrayList();

        PreparedStatement fileList=dbSources.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.tarih,a.imhatarihi,a.LFID,a.OTID FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID WHERE own.ownname=? AND own.login_id=?");
        fileList.setString(1,PrimaryController.type);
        fileList.setInt(2,PrimaryController.ID);
        ResultSet resultSet=fileList.executeQuery();
        while (resultSet.next()){
               LoadedFileModel loadedFile=new LoadedFileModel(
                       null,
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
                        null);
            theFileModel.add(loadedFile);
            TreeItem<String> birimChild=new TreeItem<>(loadedFile.getBirimad());
            birim.getChildren().add(birimChild);
        }
        table.setT

    }
}
