package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoadedFile {

    @FXML
    private TableView<LoadedFileModel> table;

    @FXML
    private TableColumn<LoadedFileModel, String> sira;

    @FXML
    private TableColumn<LoadedFileModel, String> destisno;

    @FXML
    private TableColumn<LoadedFileModel, String> birimad;

    @FXML
    private TableColumn<LoadedFileModel, String> spdkod;

    @FXML
    private TableColumn<LoadedFileModel, String> spdkarsilik;

    @FXML
    private TableColumn<LoadedFileModel, String> ozelkod;

    @FXML
    private TableColumn<LoadedFileModel, String> ozelkarsilik;

    @FXML
    private TableColumn<LoadedFileModel, String> klasno;

    @FXML
    private TableColumn<LoadedFileModel, String> ktarihi;

    @FXML
    private TableColumn<LoadedFileModel, String> aciklama;

    @FXML
    private TableColumn<LoadedFileModel, String> yuktarihi;

    @FXML
    private TableColumn<LoadedFileModel, JFXButton> sil;

    @FXML
    private TableColumn<LoadedFileModel, JFXButton> desgistir;

    @FXML
    private JFXTextField ara;
    MysqlDataSource dataSource = ConnectorMysql.connect();
    ObservableList<LoadedFileModel> theFileModel;

    @FXML
    void onClic(ActionEvent event){

    }

    @SuppressWarnings("unchecked")
    private void editFocusedCell() {
        final TablePosition< LoadedFileModel, String > focusedCell = table
                .focusModelProperty().get().focusedCellProperty().get();
        table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @FXML
    void initialize() throws SQLException {
        theFileModel= FXCollections.observableArrayList();
        sira.setCellValueFactory(new PropertyValueFactory<>("sira"));
        destisno.setCellValueFactory(new PropertyValueFactory<>("destisno"));
        spdkod.setCellValueFactory(new PropertyValueFactory<>("spdkod"));
        spdkarsilik.setCellValueFactory(new PropertyValueFactory<>("spdkarsilik"));
        ozelkod.setCellValueFactory(new PropertyValueFactory<>("ozelkod"));
        ozelkarsilik.setCellValueFactory(new PropertyValueFactory<>("ozelkarsilik"));
        klasno.setCellValueFactory(new PropertyValueFactory<>("klasno"));
        ktarihi.setCellValueFactory(new PropertyValueFactory<>("ktarihi"));
        aciklama.setCellValueFactory(new PropertyValueFactory<>("aciklama"));
        yuktarihi.setCellValueFactory(new PropertyValueFactory<>("yuktarihi"));
        sil.setCellValueFactory(new PropertyValueFactory<>("sil"));
        desgistir.setCellValueFactory(new PropertyValueFactory<>("desgistir"));

        table.setEditable(true);
        table.getSelectionModel().cellSelectionEnabledProperty().set(true);
        table.setEditable(true);
        // allows the individual cells to be selected
        table.getSelectionModel().cellSelectionEnabledProperty().set(true);
        // when character or numbers pressed it will start edit in editable
        // fields
        table.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().isLetterKey() || keyEvent.getCode().isDigitKey()) {
                editFocusedCell();
            } else if (keyEvent.getCode() == KeyCode.RIGHT ||
                    keyEvent.getCode() == KeyCode.TAB) {
                table.getSelectionModel().selectNext();
                keyEvent.consume();
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                // work around due to
                // TableView.getSelectionModel().selectPrevious() due to a bug
                // stopping it from working on
                // the first column in the last row of the table
//            selectPrevious();
//            event.consume();
            }

        });
        PreparedStatement fileList=dataSource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.prossTime FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID WHERE own.username=?");
        fileList.setString(1,PrimaryController.username);

        ResultSet resultSet=fileList.executeQuery();
        LoadedFileModel loadedFile;
        int sira=1;
        JFXButton sil;
        JFXButton degistir;
        while (resultSet.next()){
            if (PrimaryController.type.equals("Admin")){
                sil=new JFXButton("Sil");
                sil.getStyleClass().add("deleteButton");
                sil.setOnAction(event -> {
                    System.out.println(event.getSource().toString());;
                });
                degistir=new JFXButton("Degistr");
                degistir.getStyleClass().add("changeButton");
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
                        sil,
                        degistir
                );
            }else {
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
                        new JFXButton("Degistr")
                );
            }
            theFileModel.add(loadedFile);
            sira++;
        }
        FilteredList<LoadedFileModel> filteredList=new FilteredList<>(theFileModel,b -> true);
        ara.textProperty().addListener((observableValue, s, t1) -> {
            filteredList.setPredicate(loadedFileModel->{
                if (t1==null || t1.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = t1.toLowerCase();
                if (loadedFileModel.getAciklama().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else if (loadedFileModel.getBirimad().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                else if (String.valueOf(loadedFileModel.getYuktarihi()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false; // Does not match.

            });
        });
        SortedList<LoadedFileModel> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
}
