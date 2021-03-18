package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class Load {

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
    private JFXDatePicker upload_tarih;

    @FXML
    private JFXDatePicker upload_imha;

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
    private TableColumn<load_model, JFXDatePicker> tarih;

    @FXML
    private TableColumn<load_model, JFXDatePicker> evraktarihi;

    @FXML
    private TableColumn<load_model, JFXDatePicker> imhatarihi;


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

    private void printImage(BufferedImage image) {
        PrinterJob printJob = PrinterJob.createPrinterJob();
        java.awt.print.PrinterJob printerJob = java.awt.print.PrinterJob.getPrinterJob();
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex != 0) {
                    return NO_SUCH_PAGE;
                }
                Paper paper = new Paper();
                paper.setSize(400, 600);
                pageFormat.setPaper(paper);
                graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                return PAGE_EXISTS;
            }
        });
        try {
            printerJob.print();
        } catch (PrinterException e1) {
            e1.printStackTrace();
        }
    }

    ObservableList<load_model> models;
    File tempFile;
    List<File> files;
    List<File> destFile = new ArrayList<>();
    List<File> sourceFile = new ArrayList<>();
    MysqlDataSource dbSource = ConnectorMysql.connect();

    @FXML
    void initialize() throws SQLException {


        PrinterJob printerJob = Objects.requireNonNull(PrinterJob.createPrinterJob(), "Cannot create printer job");
        initilizeElement();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 32; i++) {
                    JFXTextField sayi = new JFXTextField();
                    sayi.setPrefHeight(25);
                    load_model themodel = new load_model(String.valueOf(i),
                            sayi,
                            new JFXTextField(),
                            new JFXTextField(),
                            new JFXDatePicker(),
                            new JFXDatePicker(),
                            new JFXDatePicker());
                    models.add(themodel);
                }
            }
        });
        thread.run();
        ekle.setOnMouseClicked(mouseEvent -> {
//            theModels.clear();
//            dowload();
            JFXTextField sayi = new JFXTextField();
            sayi.setPrefHeight(25);
            load_model themodel = new load_model(String.valueOf(table.getItems().size()),
                    sayi,
                    new JFXTextField(),
                    new JFXTextField(),
                    new JFXDatePicker(),
                    new JFXDatePicker(),
                    new JFXDatePicker());
            table.getItems().add(themodel);
        });
        table.getItems().addAll(models);
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        upload_yazdır.setOnMouseClicked(mouseEvent -> {
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
        upload_arsivekaydet.setOnMouseClicked(mouseEvent -> {
            try {
                if (upload_imha.getValue()==null){
                    Notifications.create()
                            .title("Başarılı")
                            .text("İmha tarihi boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (upload_tarih.getValue()==null){
                    Notifications.create()
                            .title("Başarılı")
                            .text("Tarih boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (upload_destıs_no.getSelectionModel().getSelectedIndex()==-1){
                    Notifications.create()
                            .title("Başarılı")
                            .text("Destıs no boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                String test=upload_birim.getText();
                if (upload_birim.getText().equals("")){
                    Notifications.create()
                            .title("Başarılı")
                            .text("Birim boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (upload_spdno.getText().equals("")){
                    Notifications.create()
                            .title("Başarılı")
                            .text("SPD no boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (upload_spdkarsilik.getText().equals("")){
                    Notifications.create()
                            .title("Başarılı")
                            .text("SPD Karşılık boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (upload_ozelkod.getText().equals("")){
                    Notifications.create()
                            .title("Başarılı")
                            .text("Özel kod boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (upload_ozelkodkarssiligi.getText().equals("")){
                    Notifications.create()
                            .title("Başarılı")
                            .text("Özel kod karşılık boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (upload_klasorno.getText().equals("")){
                    Notifications.create()
                            .title("Başarılı")
                            .text("Klasör no boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (upload_aciklama.getText().equals("")){
                    Notifications.create()
                            .title("Başarılı")
                            .text("Açıklama boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }

                PreparedStatement preparedStatement = dbSource.getConnection().prepareStatement(
                        "INSERT INTO `load_flle` (`LFID`, `DID`, `OTID`, `birim`, `spd_kod`, `spdkarsilik`, `ozel_kod`, `ozelkarsilik`, `klsorno`, `aciklama`, `tarih`, `imhatarihi`,`prossTime`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)"
                );
                preparedStatement.setInt(1,destisNo.get(upload_destıs_no.getSelectionModel().getSelectedIndex()));
                preparedStatement.setInt(2,Integer.valueOf(PrimaryController.ownTypeID));
                preparedStatement.setString(3,upload_birim.getText());
                preparedStatement.setString(4,upload_spdno.getText());
                preparedStatement.setString(5,upload_spdkarsilik.getText());
                preparedStatement.setString(6,upload_ozelkod.getText());
                preparedStatement.setString(7,upload_ozelkodkarssiligi.getText());
                preparedStatement.setString(8,upload_klasorno.getText());
                preparedStatement.setString(9,upload_aciklama.getText());
                LocalDate tarihSql=upload_tarih.getValue();
                preparedStatement.setDate(10,Date.valueOf(tarihSql));
                LocalDate imhaSql=upload_imha.getValue();
                preparedStatement.setDate(11,Date.valueOf(imhaSql));
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datetime = sdf.format(dt);
                preparedStatement.setString(12,datetime);
                preparedStatement.execute();
                PreparedStatement ownType=dbSource.getConnection().prepareStatement("select * from load_file where OTID=? and prossTime=?");
                ownType.setInt(1,Integer.valueOf(PrimaryController.ownTypeID));
                ownType.setString(2,datetime);
                ResultSet resultSet=ownType.executeQuery();
                String proccessId="";
                while (resultSet.next()) {
                    proccessId=resultSet.getString("FLID");
                }
                try {
                    if (files.size()>0){
                        for(File file:files) {
                            Files.copy(file.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String fileSql="";
                
                Statement saveFile=

            } catch (SQLException throwables) {
                throwables.printStackTrace();
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

        listview.setContextMenu(contextMenu);
        listview.setExpanded(true);
        file.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF", "*.pdf")
            );
            files = fileChooser.showOpenMultipleDialog(main_pane.getScene().getWindow());
            List<String> fileName = new ArrayList<>();
            sourceFile.addAll(files);
            for (File file : files) {
                tempFile = new File("src/main/resources/org/kumsal/ficomsoft/files/" + file.getName());
                destFile.add(tempFile);
                listview.getItems().add(file.getName());

            }
            Notifications.create()
                    .title("Başarılı")
                    .text("Dosyalar tanimlandı")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BASELINE_LEFT)
                    .showConfirm();
        });

    }
    private List<Integer> destisNo=new ArrayList<>();

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

    public void printImage(Node node) {

        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.getDefaultPageLayout();
        System.out.println("PageLayout: " + pageLayout);

        // Printable area
        double pWidth = pageLayout.getPrintableWidth();
        double pHeight = pageLayout.getPrintableHeight();
        System.out.println("Printable area is " + pWidth + " width and "
                + pHeight + " height.");

        // Node's (Image) dimensions
        double nWidth = node.getBoundsInParent().getWidth();
        double nHeight = node.getBoundsInParent().getHeight();
        System.out.println("Node's dimensions are " + nWidth + " width and "
                + nHeight + " height");

        // How much space is left? Or is the image to big?
        double widthLeft = pWidth - nWidth;
        double heightLeft = pHeight - nHeight;
        System.out.println("Width left: " + widthLeft
                + " height left: " + heightLeft);

        // scale the image to fit the page in width, height or both
        double scale = 0;

        if (widthLeft < heightLeft) {
            scale = pWidth / nWidth;
        } else {
            scale = pHeight / nHeight;
        }

        // preserve ratio (both values are the same)
        node.getTransforms().add(new Scale(scale, scale));

        // after scale you can check the size fit in the printable area
        double newWidth = node.getBoundsInParent().getWidth();
        double newHeight = node.getBoundsInParent().getHeight();
        System.out.println("New Node's dimensions: " + newWidth
                + " width " + newHeight + " height");

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }
}

