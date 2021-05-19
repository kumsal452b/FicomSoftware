package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kumsal.ficomSoft.AdapterModelClass.load_model;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Load {

    public static int fileID;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private DatePicker upload_tarih;

    @FXML
    private DatePicker upload_imha;

    @FXML
    private JFXTextField upload_aciklama;


    @FXML
    private TableView<load_model> table;

    @FXML
    private TableColumn<load_model, String> adet;

    @FXML
    private TableColumn<load_model, String> sayi;

    @FXML
    private TableColumn<load_model, JFXTextField> konu;

    @FXML
    private TableColumn<load_model, JFXTextField> sayfaAdedi;

    @FXML
    private TableColumn<load_model, DatePicker> tarih;

    @FXML
    private TableColumn<load_model, DatePicker> evraktarihi;

    @FXML
    private TableColumn<load_model, DatePicker> imhatarihi;


    @FXML
    private Button upload_arsivekaydet;

    @FXML
    private Button ekle;

    @FXML
    private Button upload_yazdır;

    @FXML
    private Button file;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private JFXListView<String> listview;

    public static ArrayList<printer_model> theModels = new ArrayList<>();

    ObservableList<load_model> models;
    File tempFile;
    List<File> files;
    List<File> destFile = new ArrayList<>();
    List<File> sourceFile = new ArrayList<>();
    MysqlDataSource dbSource = ConnectorMysql.connect();
    private String clearSomeCharacter(String data){
        String text=data;
        text=text.replace("'","");
        return text;
    }

    @FXML
    void initialize() throws SQLException {


        PrinterJob printerJob = Objects.requireNonNull(PrinterJob.createPrinterJob(), "Cannot create printer job");
        initilizeElement();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 11; i++) {
                    JFXTextField sayi = new JFXTextField();
                    sayi.setPrefHeight(25);
                    load_model themodel = new load_model(String.valueOf(i+1),
                            sayi,
                            new JFXTextField(),
                            new JFXTextField(),
                            new DatePicker(),
                            new DatePicker(),
                            new DatePicker());
                    models.add(themodel);
                }
            }
        });
        thread.run();
        ekle.setOnAction(mouseEvent -> {
//            theModels.clear();
//            dowload();
            try {
                check(mouseEvent);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int consept=table.getItems().size();
            int sira=0;
            if (consept==0){
                sira=1;
                JFXTextField sayi = new JFXTextField();
                sayi.setPrefHeight(25);
                load_model themodel = new load_model(String.valueOf(sira),
                        sayi,
                        new JFXTextField(),
                        new JFXTextField(),
                        new DatePicker(),
                        new DatePicker(),
                        new DatePicker());
                table.getItems().add(themodel);
                models.add(themodel);
            }else{
                 load_model model=table.getItems().get(consept-1);
                 sira=Integer.valueOf(model.getCount());
                sira+=1;
                JFXTextField sayi = new JFXTextField();
                sayi.setPrefHeight(25);
                load_model themodel = new load_model(String.valueOf(sira),
                        sayi,
                        new JFXTextField(),
                        new JFXTextField(),
                        new DatePicker(),
                        new DatePicker(),
                        new DatePicker());
                table.getItems().add(themodel);
                models.add(themodel);
            }
        });
        table.getItems().addAll(models);
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        upload_yazdır.setOnAction(mouseEvent -> {
            try {
                check(mouseEvent);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            theModels.clear();
            dowload();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("printScreen.fxml"));
            AnchorPane root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PrimaryController.stage);
            stage.show();
        });
        upload_arsivekaydet.setOnAction(mouseEvent -> {
            try {
                check(mouseEvent);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkFields()){
                try {
                    PreparedStatement preparedStatement = dbSource.getConnection().prepareStatement(
                            "INSERT INTO `load_flle` (`LFID`, `DID`, `OTID`, `birim`, `spd_kod`, `spdkarsilik`, `ozel_kod`, `ozelkarsilik`, `klsorno`, `aciklama`, `tarih`, `imhatarihi`,`prossTime`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)"
                    );
                    preparedStatement.setInt(1, destisNo.get(upload_destıs_no.getSelectionModel().getSelectedIndex()));
                    preparedStatement.setInt(2, Integer.valueOf(PrimaryController.ownTypeID));
                    preparedStatement.setString(3, upload_birim.getText());
                    preparedStatement.setString(4, upload_spdno.getText());
                    preparedStatement.setString(5, upload_spdkarsilik.getText());
                    preparedStatement.setString(6, upload_ozelkod.getText());
                    preparedStatement.setString(7, upload_ozelkodkarssiligi.getText());
                    preparedStatement.setString(8, upload_klasorno.getText());
                    preparedStatement.setString(9, upload_aciklama.getText());
                    LocalDate tarihSql = upload_tarih.getValue();
                    preparedStatement.setDate(10, Date.valueOf(tarihSql));
                    LocalDate imhaSql = upload_imha.getValue();
                    preparedStatement.setDate(11, Date.valueOf(imhaSql));
                    java.util.Date dt = new java.util.Date();
                    java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String datetime = sdf.format(dt);
                    preparedStatement.setString(12, datetime);
                    preparedStatement.execute();
                    PreparedStatement ownType = dbSource.getConnection().prepareStatement("select * from load_flle where OTID=? and prossTime=?");
                    ownType.setInt(1, Integer.valueOf(PrimaryController.ownTypeID));
                    ownType.setString(2, datetime);
                    ResultSet resultSet = ownType.executeQuery();
                    String proccessId = "";
                    while (resultSet.next()) {
                        proccessId = resultSet.getString("LFID");
                    }
                    for (int i=0;i<table.getItems().size(); i++){
                        load_model theModel=table.getItems().get(i);
                        if (!theModel.getAdet().getText().equals("") ||
                        !theModel.getKonu().getText().equals("") || !theModel.getSayi().getText().equals("") || theModel.getTime().equals("")||
                        theModel.getEvrakTarihi().getValue()!=null || theModel.getImhaTarihi().getValue()!=null){
                            PreparedStatement statement=dbSource.getConnection().prepareStatement(
                                    "INSERT INTO `file_detail` (`FDID`, `LFID`, `sayi`, `konu`, `adet`, `tarih`, `evrakTarihi`, `imhaTarihi`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)"
                            );
                            statement.setInt(1,Integer.valueOf(proccessId));
                            statement.setString(2,theModel.getSayi().getText());
                            statement.setString(3,theModel.getKonu().getText());
                            statement.setString(4,theModel.getAdet().getText());
                            statement.setString(5,theModel.getTime().getValue() != null ? theModel.getTime().getValue().toString() : "");
                            statement.setString(6,theModel.getEvrakTarihi().getValue() != null ? theModel.getEvrakTarihi().getValue().toString() : "");
                            statement.setString(7,theModel.getImhaTarihi().getValue() != null ? theModel.getImhaTarihi().getValue().toString() : "");
                            statement.execute();
                        }
                    }
                    try {
                        if (sourceFile.size() > 0) {
                            String filename = "";
                            String partOfsql = "(NULL, '" + filename + "', '" + proccessId + "')";
                            String fileSql = "INSERT INTO `file` (`FID`, `filepath`, `LFID`) VALUES ";
                            int index = 0;
                            for (File file : sourceFile) {
                                Files.copy(file.toPath(), destFile.get(index).toPath(), StandardCopyOption.REPLACE_EXISTING);
                                filename = clearSomeCharacter(destFile.get(index).getName());
                                partOfsql = "(NULL, '" + filename + "', '" + proccessId + "')";
                                fileSql += partOfsql;
                                if (index != sourceFile.size() - 1) {
                                    fileSql += ",";
                                }
                                index++;
                            }
                            index = 0;
                            System.out.println(fileSql);
                            Statement saveFile = dbSource.getConnection().createStatement();
                            saveFile.execute(fileSql);
                            fileSql = "";
                            sourceFile.clear();
                            destFile.clear();
                            upload_aciklama.setText("");
                            upload_klasorno.setText("");
                            upload_ozelkodkarssiligi.setText("");
                            upload_ozelkod.setText("");
                            upload_spdkarsilik.setText("");
                            upload_spdno.setText("");
                            upload_birim.setText("");
                            listview.getItems().clear();
                            Notifications.create()
                                    .title("Bşarılı")
                                    .text("Kayıtlar arşive eklendi")
                                    .hideAfter(Duration.seconds(3))
                                    .position(Pos.BASELINE_LEFT)
                                    .showConfirm();
                        }
                        else {
                            upload_aciklama.setText("");
                            upload_klasorno.setText("");
                            upload_ozelkodkarssiligi.setText("");
                            upload_ozelkod.setText("");
                            upload_spdkarsilik.setText("");
                            upload_spdno.setText("");
                            upload_birim.setText("");
                            listview.getItems().clear();
                            Notifications.create()
                                    .title("Bşarılı")
                                    .text("Kayıtlar arşive eklendi")
                                    .hideAfter(Duration.seconds(3))
                                    .position(Pos.BASELINE_LEFT)
                                    .showConfirm();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item = new MenuItem("Sil");
        item.setOnAction(event -> {
            int indis = listview.getSelectionModel().getSelectedIndex();
            destFile.remove(indis);
            sourceFile.remove(indis);
            listview.getItems().remove(indis);
        });
        contextMenu.getItems().add(item);

        ContextMenu contextMenu2 = new ContextMenu();
        MenuItem item2 = new MenuItem("Sil");
        item2.setOnAction(event -> {
            int indis = table.getSelectionModel().getSelectedIndex();
            models.remove(indis);
            table.getItems().remove(indis);
            for(int i=indis; i<table.getItems().size(); i++){
                load_model model=table.getItems().get(i);
                model.setCount(String.valueOf(Integer.valueOf(model.getCount())-1));
                table.getItems().set(i,model);
            }
        });
        contextMenu2.getItems().add(item2);
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if(t.getButton() == MouseButton.SECONDARY) {
                    contextMenu2.show(table, t.getScreenX(), t.getScreenY());
                }
            }
        });
        listview.setContextMenu(contextMenu2);

        listview.setContextMenu(contextMenu);
        listview.setExpanded(true);
        file.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
//            fileChooser.getExtensionFilters().addAll(
//                    new FileChooser.ExtensionFilter
//            );
            files = fileChooser.showOpenMultipleDialog(main_pane.getScene().getWindow());

            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            s+=System.getProperty("files");
            for (File file : files) {
                tempFile = new File(s+"\\"+ clearSomeCharacter(file.getName()));
                System.out.println(tempFile.getName()+" "+tempFile.getPath());
                destFile.add(tempFile);
                listview.getItems().add(file.getName());

            }
            sourceFile.addAll(files);
            Notifications.create()
                    .title("Başarılı")
                    .text("Dosyalar tanimlandı")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showConfirm();
        });

    }
    void check(ActionEvent event) throws SQLException, IOException {
        if (CheckIsDenied.isDenied(PrimaryController.ID,PrimaryController.username,
                PrimaryController.type == "Admin")){
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("check_denied.fxml"));
            main_pane.getChildren().clear();
            main_pane.getChildren().add(root);
            return;
        }
    }

    private boolean checkFields() {
        if (upload_imha.getValue() == null) {
            Notifications.create()
                    .title("Hata")
                    .text("İmha tarihi boş bırakılamaz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            return false;
        }
        if (upload_tarih.getValue() == null) {
            Notifications.create()
                    .title("Hata")
                    .text("Tarih boş bırakılamaz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            return false;
        }
        if (upload_destıs_no.getSelectionModel().getSelectedIndex() == -1) {
            Notifications.create()
                    .title("Hata")
                    .text("Destıs no boş bırakılamaz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            return false;
        }
        String test = upload_birim.getText();
        if (upload_birim.getText().equals("")) {
            Notifications.create()
                    .title("Hata")
                    .text("Birim boş bırakılamaz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            return false;
        }
        if (upload_spdno.getText().equals("")) {
            Notifications.create()
                    .title("Hata")
                    .text("SPD no boş bırakılamaz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            return false;
        }
        if (upload_spdkarsilik.getText().equals("")) {
            Notifications.create()
                    .title("Hata")
                    .text("SPD Karşılık boş bırakılamaz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            return false;
        }
        if (upload_ozelkod.getText().equals("")) {
            Notifications.create()
                    .title("Hata")
                    .text("Özel kod boş bırakılamaz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            return false;
        }
        if (upload_ozelkodkarssiligi.getText().equals("")) {
            Notifications.create()
                    .title("Hata")
                    .text("Özel kod karşılık boş bırakılamaz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            return false;
        }
        if (upload_klasorno.getText().equals("")) {
            Notifications.create()
                    .title("Hata")
                    .text("Klasör no boş bırakılamaz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            return false;
        }
        if (upload_aciklama.getText().equals("")) {
            Notifications.create()
                    .title("Hata")
                    .text("Açıklama boş bırakılamaz.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showError();
            return false;
        }
        if (!betweenDate(upload_tarih.getValue(),upload_imha.getValue())){
            return false;
        }
        return true;
    }

    private List<Integer> destisNo = new ArrayList<>();

    private void initilizeElement() throws SQLException {
        models = FXCollections.observableArrayList();
        konu.setCellValueFactory(new PropertyValueFactory<>("konu"));
        evraktarihi.setCellValueFactory(new PropertyValueFactory<>("evrakTarihi"));
        tarih.setCellValueFactory(new PropertyValueFactory<>("time"));
        sayi.setCellValueFactory(new PropertyValueFactory<>("sayi"));
        adet.setCellValueFactory(new PropertyValueFactory<>("count"));
        sayfaAdedi.setCellValueFactory(new PropertyValueFactory<>("adet"));
        imhatarihi.setCellValueFactory(new PropertyValueFactory<>("imhaTarihi"));

        Statement forDestis = dbSource.getConnection().createStatement();
        forDestis.execute("select * from destis");


        ResultSet resultSet;
        resultSet = forDestis.getResultSet();
        while (resultSet.next()) {
            upload_destıs_no.getItems().add(resultSet.getString("destisno"));
            destisNo.add(resultSet.getInt("DID"));
        }
    }

    private void dowload() {
        for (int i = 0; i < table.getItems().size(); i++) {
            load_model theModel = table.getItems().get(i);
            LocalDate timeNow = theModel.getTime().getValue();
            LocalDate evrak = theModel.getEvrakTarihi().getValue();
            LocalDate imha = theModel.getImhaTarihi().getValue();

            String time = timeNow != null ? timeNow.toString() : "";
            String sayi = theModel.getSayi() != null ? theModel.getSayi().getText() : "";
            String konu = theModel.getKonu() != null ? theModel.getKonu().getText() : "";
            String adet = theModel.getAdet() != null ? theModel.getAdet().getText() : "";
            String evrak2 = evrak != null ? evrak.toString() : "";
            String imhaTarihi = imha != null ? imha.toString() : "";
            printer_model thePrintModel = new printer_model(
                    theModel.getCount(), time, sayi, konu, adet, evrak2, imhaTarihi);
            theModels.add(thePrintModel);
        }
    }
    public boolean betweenDate(LocalDate first, LocalDate second){
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date1 = myFormat.parse(first.toString());
            java.util.Date date2 = myFormat.parse(second.toString());
            long diff = date2.getTime() - date1.getTime();
            long differance= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (differance<0){
                Notifications.create()
                        .title("Hata")
                        .text("Tarih İmha tarihinden sonra olamaz")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER_LEFT)
                        .showError();
                return false;
            }else{
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}

