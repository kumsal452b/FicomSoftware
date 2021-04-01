package org.kumsal.ficomSoft;

import com.jfoenix.controls.*;
import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class LoadedFile {

    public StackPane stakcpane;

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

    @FXML
    private VBox slidder;

    @FXML
    private JFXComboBox<String> upload_destıs_no;

    @FXML
    private JFXTextField upload_birim;

    @FXML
    private JFXTextField upload_spdno;

    @FXML
    private JFXTextField upload_spdkarsilik;

    @FXML
    private JFXTextField upload_ozelkod;

    @FXML
    private JFXTextField upload_ozelkodkarssiligi;

    @FXML
    private JFXTextField upload_klasorno;

    @FXML
    private JFXDatePicker upload_tarih;

    @FXML
    private JFXDatePicker upload_imha;

    @FXML
    private JFXTextField upload_aciklama;

    @FXML
    private JFXButton guncelle;

    @FXML
    private JFXButton dvt;


    @FXML
    private TreeTableView<String> table1;

    @FXML
    private TreeTableColumn<String,String> destisno1;


    @FXML
    private JFXCheckBox isWannaAll;
    MysqlDataSource dataSource = ConnectorMysql.connect();
    ObservableList<LoadedFileModel> theFileModel;
    MysqlDataSource dbSources= ConnectorMysql.connect();


    @SuppressWarnings("unchecked")
    private void editFocusedCell() {
        final TablePosition< LoadedFileModel, String > focusedCell = table
                .focusModelProperty().get().focusedCellProperty().get();
        table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }
    ArrayList<Integer> destisNo=new ArrayList<>();
    ArrayList<Integer> fileID=new ArrayList<>();
    ArrayList<Integer> typeIDsID=new ArrayList<>();
    ArrayList<JFXButton> silButtons=new ArrayList<>();
    ArrayList<JFXButton> degistirButtons=new ArrayList<>();
    ArrayList<Date> imhaDates=new ArrayList<>();
    int index=0;

    TreeItem<String> birimForTree=new TreeItem<>("Birim");
    TreeItem<String> destisnoForTree=new TreeItem<>("Destis No");
    TreeItem<String> imhaForTree=new TreeItem<>("Imha Tarihi");
    TreeItem<String> yuklemeForTree=new TreeItem<>("Yukleme Tarihi");
    TreeItem<String> spdForTree=new TreeItem<>("SPD Kodu");
    TreeItem<String> ozel=new TreeItem<>("Özel Kod");
    TreeItem<String> ozelkarsilikForTree=new TreeItem<>("Özel Kod karşılığı");
    TreeItem<String> root=new TreeItem<>("Veri Kümesi");

    Duration duration = Duration.millis(2500);
    //Create new scale transition
    ScaleTransition scaleTransition = new ScaleTransition(duration, slidder);
    @FXML
    void initialize() throws SQLException {
        if (PrimaryController.type.equals("User")){
            isWannaAll.setDisable(true);
        }
        treeLoad();
        slidder.setVisible(false);
        theFileModel= FXCollections.observableArrayList();
        sira.setCellValueFactory(new PropertyValueFactory<>("sira"));
        destisno.setCellValueFactory(new PropertyValueFactory<>("destisno"));
        spdkod.setCellValueFactory(new PropertyValueFactory<>("spdkod"));
        birimad.setCellValueFactory(new PropertyValueFactory<>("birimad"));
        spdkarsilik.setCellValueFactory(new PropertyValueFactory<>("spdkarsilik"));
        ozelkod.setCellValueFactory(new PropertyValueFactory<>("ozelkod"));
        ozelkarsilik.setCellValueFactory(new PropertyValueFactory<>("ozelkarsilik"));
        klasno.setCellValueFactory(new PropertyValueFactory<>("klasno"));
        ktarihi.setCellValueFactory(new PropertyValueFactory<>("ktarihi"));
        aciklama.setCellValueFactory(new PropertyValueFactory<>("aciklama"));
        yuktarihi.setCellValueFactory(new PropertyValueFactory<>("yuktarihi"));
        sil.setCellValueFactory(new PropertyValueFactory<>("sil"));
        desgistir.setCellValueFactory(new PropertyValueFactory<>("desgistir"));

        scaleTransition.setByX(1.5);
        //Set how much Y should
        scaleTransition.setByY(1.5);


        dvt.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("treeTable.fxml"));
            AnchorPane root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PrimaryController.stage);
            stage.show();
        });
        Statement forDestis = dbSources.getConnection().createStatement();
        forDestis.execute("select * from destis");

        ResultSet resultSet1;
        resultSet1 = forDestis.getResultSet();
        while (resultSet1.next()) {
            upload_destıs_no.getItems().add(resultSet1.getString("destisno"));
            destisNo.add(resultSet1.getInt("DID"));
        }

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
        guncelle.setOnAction(event -> {
            update();
        });
        isWannaAll.selectedProperty().addListener((observableValue, aBoolean, t1) -> {

        });
        isWannaAll.setOnAction(test -> {
            theFileModel.clear();
            degistirButtons.clear();
            silButtons.clear();
            if (isWannaAll.isSelected()){
                PreparedStatement fileList= null;
                try {
                    fileList = dataSource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.tarih,a.imhatarihi,a.LFID,a.OTID FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID");
                    ResultSet resultSet=fileList.executeQuery();
                    LoadedFileModel loadedFile;
                    int sira=1;
                    JFXButton sil;
                    JFXButton degistir;
                    while (true){
                        try {
                            if (!resultSet.next()) break;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        if (PrimaryController.type.equals("Admin")){
                            sil=new JFXButton("Sil");
                            sil.getStyleClass().add("deleteButton");
                            sil.setOnAction(event -> {
                                deleteElement(event);
                            });
                            degistir=new JFXButton("Degistr");
                            degistir.getStyleClass().add("changeButton");
                            degistir.setOnAction(event -> {
                                changeStatement(event);

                            });
                            silButtons.add(sil);
                            degistirButtons.add(degistir);
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
                        }else if (PrimaryController.isAuth){
                            sil=new JFXButton("Sil");
                            sil.getStyleClass().add("deleteButton");
                            sil.setOnAction(event -> {
                                deleteElement(event);
                            });
                            degistir=new JFXButton("Degistr");
                            degistir.getStyleClass().add("changeButton");
                            degistir.setOnAction(event -> {
                                changeStatement(event);
                            });

                            silButtons.add(sil);
                            degistirButtons.add(degistir);
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
                        }
                        else {
                            degistir=new JFXButton("Degistr");
                            degistir.getStyleClass().add("changeButton");
                            degistir.setOnAction(event -> {
                                changeStatement(event);
                            });
                            degistirButtons.add(degistir);
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
                                    degistir);
                        }

                        imhaDates.add(resultSet.getDate(11));
                        fileID.add(resultSet.getInt(12));
                        typeIDsID.add(resultSet.getInt(13));
                        theFileModel.add(loadedFile);
                        sira++;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }else{
                try {
                    PreparedStatement fileList=dataSource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.tarih,a.imhatarihi,a.LFID,a.OTID FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID WHERE own.ownname=? AND own.login_id=?");
                    fileList.setString(1,PrimaryController.type);
                    fileList.setInt(2,PrimaryController.ID);
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
                                deleteElement(event);
                            });
                            degistir=new JFXButton("Degistr");
                            degistir.getStyleClass().add("changeButton");
                            degistir.setOnAction(event -> {
                                changeStatement(event);

                            });
                            silButtons.add(sil);
                            degistirButtons.add(degistir);
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
                        }else if (PrimaryController.isAuth){
                            sil=new JFXButton("Sil");
                            sil.getStyleClass().add("deleteButton");
                            sil.setOnAction(event -> {
                                deleteElement(event);
                            });
                            degistir=new JFXButton("Degistr");
                            degistir.getStyleClass().add("changeButton");
                            degistir.setOnAction(event -> {
                                changeStatement(event);
                            });

                            silButtons.add(sil);
                            degistirButtons.add(degistir);
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
                        }
                        else {
                            degistir=new JFXButton("Degistr");
                            degistir.getStyleClass().add("changeButton");
                            degistir.setOnAction(event -> {
                                changeStatement(event);
                            });
                            degistirButtons.add(degistir);
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
                                    degistir);
                        }

                        imhaDates.add(resultSet.getDate(11));
                        fileID.add(resultSet.getInt(12));
                        typeIDsID.add(resultSet.getInt(13));
                        theFileModel.add(loadedFile);
                        sira++;
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        if (isWannaAll.isSelected()){
            PreparedStatement fileList=dataSource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.tarih,a.imhatarihi,a.LFID,a.OTID FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID");
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
                        deleteElement(event);
                    });
                    degistir=new JFXButton("Degistr");
                    degistir.getStyleClass().add("changeButton");
                    degistir.setOnAction(event -> {
                        changeStatement(event);

                    });
                    silButtons.add(sil);
                    degistirButtons.add(degistir);
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
                }else if (PrimaryController.isAuth){
                    sil=new JFXButton("Sil");
                    sil.getStyleClass().add("deleteButton");
                    sil.setOnAction(event -> {
                        deleteElement(event);
                    });
                    degistir=new JFXButton("Degistr");
                    degistir.getStyleClass().add("changeButton");
                    degistir.setOnAction(event -> {
                        changeStatement(event);
                    });

                    silButtons.add(sil);
                    degistirButtons.add(degistir);
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
                }
                else {
                    degistir=new JFXButton("Degistr");
                    degistir.getStyleClass().add("changeButton");
                    degistir.setOnAction(event -> {
                        changeStatement(event);
                    });
                    degistirButtons.add(degistir);
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
                            degistir);
                }

                imhaDates.add(resultSet.getDate(11));
                fileID.add(resultSet.getInt(12));
                typeIDsID.add(resultSet.getInt(13));
                theFileModel.add(loadedFile);
                sira++;
            }

        }else{
            PreparedStatement fileList=dataSource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.tarih,a.imhatarihi,a.LFID,a.OTID FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID WHERE own.ownname=? AND own.login_id=?");
            fileList.setString(1,PrimaryController.type);
            fileList.setInt(2,PrimaryController.ID);
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
                        deleteElement(event);
                    });
                    degistir=new JFXButton("Degistr");
                    degistir.getStyleClass().add("changeButton");
                    degistir.setOnAction(event -> {
                        changeStatement(event);

                    });
                    silButtons.add(sil);
                    degistirButtons.add(degistir);
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
                }else if (PrimaryController.isAuth){
                    sil=new JFXButton("Sil");
                    sil.getStyleClass().add("deleteButton");
                    sil.setOnAction(event -> {
                        deleteElement(event);
                    });
                    degistir=new JFXButton("Degistr");
                    degistir.getStyleClass().add("changeButton");
                    degistir.setOnAction(event -> {
                        changeStatement(event);
                    });

                    silButtons.add(sil);
                    degistirButtons.add(degistir);
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
                }
                else {
                    degistir=new JFXButton("Degistr");
                    degistir.getStyleClass().add("changeButton");
                    degistir.setOnAction(event -> {
                        changeStatement(event);
                    });
                    degistirButtons.add(degistir);
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
                            degistir);
                }

                imhaDates.add(resultSet.getDate(11));
                fileID.add(resultSet.getInt(12));
                typeIDsID.add(resultSet.getInt(13));
                theFileModel.add(loadedFile);
                sira++;
            }
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

    private void treeLoad() throws SQLException {
        destisno1.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
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
        table1.setRoot(root);
        root.setExpanded(true);
    }
    private void deleteElement(javafx.event.ActionEvent event) {
        int currentIndex=silButtons.indexOf(event.getSource());
        JFXButton evet=new JFXButton("Evet");
        JFXDialog dialog=new JFXDialog(stakcpane,new Label("temel"), JFXDialog.DialogTransition.CENTER);
        JFXDialogLayout layout=new JFXDialogLayout();
        layout.setHeading(new Text("Dikkat"));
        layout.setBody(new Text("Bu satır silinecek. Devam etmek ister misiniz? Bu işlem geri alınamaz."));
        evet.setOnAction(event1 -> {
            theFileModel.remove(currentIndex);
            silButtons.remove(currentIndex);
            degistirButtons.remove(currentIndex);
            try {
                PreparedStatement preparedStatement=dbSources.getConnection().prepareStatement(
                        "DELETE FROM `load_flle` WHERE `load_flle`.`LFID` = ?"
                );
                preparedStatement.setInt(1,fileID.get(index));
                preparedStatement.execute();
                fileID.remove(index);
                Notifications.create()
                        .title("Başarılı")
                        .text("Klasör silindi")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BASELINE_LEFT)
                        .showConfirm();
                slidder.setVisible(false);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            dialog.close();
        });
        JFXButton iptal=new JFXButton("Iptal");
        iptal.setOnAction(event1 -> {
            dialog.close();
            slidder.setVisible(false);

        });
        layout.setActions(evet,iptal);
        dialog.setContent(layout);
        dialog.show();
    }

    private void changeStatement(javafx.event.ActionEvent event) {
        index=degistirButtons.indexOf(event.getSource());
        LoadedFileModel model=table.getItems().get(index);
        upload_destıs_no.getSelectionModel().select(upload_destıs_no.getItems().indexOf(model.getDestisno()));
        upload_birim.setText(model.getBirimad());
        upload_spdno.setText(model.getSpdkod());
        upload_aciklama.setText(model.getAciklama());
        upload_klasorno.setText(model.getKlasno());
        upload_imha.setValue(imhaDates.get(index).toLocalDate());
        upload_ozelkod.setText(model.getOzelkod());
        upload_ozelkodkarssiligi.setText(model.getOzelkarsilik());
        upload_spdkarsilik.setText(model.getSpdkarsilik());
        Date date= Date.valueOf(model.getYuktarihi());
        upload_tarih.setValue(date.toLocalDate());
        slidder.setVisible(true);
        scaleTransition.play();
    }

    private void update() {
        try {
            PreparedStatement updateFıle=dbSources.getConnection().prepareStatement("UPDATE `load_flle` SET " +
                    "`DID` =?,`OTID`=?, `birim` =?, `spd_kod` =?, `spdkarsilik` =?, `ozel_kod` =?, " +
                    "`ozelkarsilik` =?, `klsorno` =?, `aciklama` =?, `tarih` =?, " +
                    "`imhatarihi` =?, `prossTime` =? WHERE `load_flle`.`LFID` = ?");
            updateFıle.setInt(1, destisNo.get(upload_destıs_no.getSelectionModel().getSelectedIndex()));
            updateFıle.setInt(2, Integer.valueOf(typeIDsID.get(index)));
            updateFıle.setString(3, upload_birim.getText());
            updateFıle.setString(4, upload_spdno.getText());
            updateFıle.setString(5, upload_spdkarsilik.getText());
            updateFıle.setString(6, upload_ozelkod.getText());
            updateFıle.setString(7, upload_ozelkodkarssiligi.getText());
            updateFıle.setString(8, upload_klasorno.getText());
            updateFıle.setString(9, upload_aciklama.getText());
            LocalDate tarihSql = upload_tarih.getValue();
            updateFıle.setDate(10, Date.valueOf(tarihSql));
            LocalDate imhaSql = upload_imha.getValue();
            updateFıle.setDate(11, Date.valueOf(imhaSql));
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime = sdf.format(dt);
            updateFıle.setString(12, datetime);
            updateFıle.setInt(13,fileID.get(index));
            updateFıle.execute();
            slidder.setVisible(false);
            Notifications.create()
                    .title("Başarılı")
                    .text("Güncelleme başarılı.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showConfirm();
        } catch (SQLException throwables) {
            Notifications.create()
                    .title("Hata")
                    .text("Güncelleme sırasında bir hata ile karşılaştık. Lütfen tekrar deneyiniz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            throwables.printStackTrace();
        }
    }
}
