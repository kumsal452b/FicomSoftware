package org.kumsal.ficomSoft;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
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
    TreeItem<String> birimForTree=new TreeItem<>("Birim");
    TreeItem<String> destisnoForTree=new TreeItem<>("Destis No");
    TreeItem<String> imhaForTree=new TreeItem<>("Imha Tarihi");
    TreeItem<String> yuklemeForTree=new TreeItem<>("Yukleme Tarihi");
    TreeItem<String> spdForTree=new TreeItem<>("SPD Kodu");
    TreeItem<String> ozel=new TreeItem<>("Özel Kod");
    TreeItem<String> ozelkarsilikForTree=new TreeItem<>("Özel Kod karşılığı");
    TreeItem<String> root=new TreeItem<>("Veri Kümesi");
    @FXML
    void initialize() throws SQLException {
        theFileModel= FXCollections.observableArrayList();

        destisno.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<String, String> stringStringCellDataFeatures) {
                return new SimpleStringProperty(stringStringCellDataFeatures.getValue().getValue());
            }
        });
        PreparedStatement fileList=dbSources.getConnection().prepareStatement("SELECT de.destisno,a.birimForTree,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilikForTree,a.klsorno,a.tarih,a.aciklama,a.tarih,a.imhatarihi,a.LFID,a.OTID FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID WHERE own.ownname=? AND own.login_id=?");
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
            TreeItem<String> birimForTreeChild=new TreeItem<>(loadedFile.getBirimad());
            birimForTree.getChildren().add(birimForTreeChild);
            TreeItem<String> destisnoChild=new TreeItem<>(loadedFile.getDestisno());
            destisnoForTree.getChildren().add(destisnoChild);
            TreeItem<String> imhaChild=new TreeItem<>(loadedFile.getKtarihi());
            imhaForTree.getChildren().add(imhaChild);
            TreeItem<String> yuklemeForTreeChild=new TreeItem<>(loadedFile.getYuktarihi());
            yuklemeForTree.getChildren().add(yuklemeForTreeChild);
            TreeItem<String> spdChild=new TreeItem<>(loadedFile.getSpdkod());
            spdForTree.getChildren().add(spdChild);

            TreeItem<String> ozelChild=new TreeItem<>(loadedFile.getOzelkod());
            ozel.getChildren().add(ozelChild);

            TreeItem<String> ozelKarChild=new TreeItem<>(loadedFile.getOzelkarsilik());
            ozelkarsilikForTree.getChildren().add(ozelKarChild);

        }
        root.getChildren().addAll(birimForTree,destisnoForTree,imhaForTree,yuklemeForTree,spdForTree,ozel,ozelkarsilikForTree);
        table.setRoot(root);
        root.setExpanded(true);
    }
}
