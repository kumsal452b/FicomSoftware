package org.kumsal.ficomSoft;

import java.net.URL;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private TreeTableView<LoadedFileModel> table;

    @FXML
    private TreeTableColumn<LoadedFileModel,String> destisno;

    @FXML
    private TreeTableColumn<LoadedFileModel,String> birim;

    @FXML
    private TreeTableColumn<LoadedFileModel,String> spdkod;

    @FXML
    private TreeTableColumn<LoadedFileModel,String> spdkarsilik;

    @FXML
    private TreeTableColumn<LoadedFileModel,String> klasorno;

    @FXML
    private TreeTableColumn<LoadedFileModel,String> yukleme;

    @FXML
    private TreeTableColumn<LoadedFileModel,String> imha;

    ObservableList<LoadedFileModel> theFileModel;
    MysqlDataSource dbSources= ConnectorMysql.connect();

    @FXML
    void initialize() {
        theFileModel= FXCollections.observableArrayList();
        destisno.setCellValueFactory(new TreeItemPropertyValueFactory<>("destisno"));
        spdkod.setCellValueFactory(new TreeItemPropertyValueFactory<>("spdkod"));
        birim.setCellValueFactory(new TreeItemPropertyValueFactory<>("birimad"));
        spdkarsilik.setCellValueFactory(new TreeItemPropertyValueFactory<>("spdkarsilik"));
        klasorno.setCellValueFactory(new TreeItemPropertyValueFactory<>("klasno"));
        imha.setCellValueFactory(new TreeItemPropertyValueFactory<>("prosssTime"));
        yukleme.setCellValueFactory(new TreeItemPropertyValueFactory<>("yuktarihi"));
    }
}
