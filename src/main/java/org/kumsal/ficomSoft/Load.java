package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Preloader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.*;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kumsal.ficomSoft.AdapterModelClass.load_model;

import javax.naming.spi.DirectoryManager;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    private JFXComboBox<Integer> upload_destıs_no;

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

    @FXML
    void initialize() {
        models = FXCollections.observableArrayList();
        PrinterJob printerJob = Objects.requireNonNull(PrinterJob.createPrinterJob(), "Cannot create printer job");

        konu.setCellValueFactory(new PropertyValueFactory<>("konu"));
        evraktarihi.setCellValueFactory(new PropertyValueFactory<>("evrakTarihi"));
        tarih.setCellValueFactory(new PropertyValueFactory<>("time"));
        sayi.setCellValueFactory(new PropertyValueFactory<>("sayi"));
        adet.setCellValueFactory(new PropertyValueFactory<>("count"));
        sayfaAdedi.setCellValueFactory(new PropertyValueFactory<>("adet"));
        imhatarihi.setCellValueFactory(new PropertyValueFactory<>("imhaTarihi"));

        Thread thread=new Thread(new Runnable() {
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
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, load_model, t1) -> {
            System.out.println(load_model.getAdet().getText());
        });
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(pattern);
        upload_yazdır.setOnMouseClicked(mouseEvent -> {
            theModels.clear();
            dowload();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("printScreen.fxml"));
            AnchorPane root= null;
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

        });
        file.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser=new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF", "*.pdf")
            );
            List<File> files= fileChooser.showOpenMultipleDialog(main_pane.getScene().getWindow());
            List<String> fileName=new ArrayList<>();
            for(File file:files){
                File tempFile=new File("src/main/resources/org/kumsal/ficomsoft/files/"+file.getName());

                try {
                    Files.copy(file.toPath(),tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    fileName.add(file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Notifications.create()
                    .darkStyle()
                    .title("Başarılı")
                    // sets node to display
                    .hideAfter(Duration.seconds(10))
                    .show();
        });

    }

    private void dowload() {
        for (int i = 0; i < table.getItems().size(); i++) {
            load_model theModel = table.getItems().get(i);
            LocalDate timeNow=theModel.getTime().getValue();
            LocalDate evrak=theModel.getEvrakTarihi().getValue();
            LocalDate imha=theModel.getImhaTarihi().getValue();

            String time = timeNow!=null?timeNow.toString():"";
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

