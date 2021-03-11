package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.kairos.layouts.RecyclerView;
import org.kairos.layouts.ViewPager;
import org.kumsal.ficomSoft.AdapterModelClass.load_adapter;
import org.kumsal.ficomSoft.AdapterModelClass.load_model;

import javax.imageio.ImageIO;

import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;

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
    private RecyclerView<load_model> recycler_vıew;

    @FXML
    private Button upload_arsivekaydet;

    @FXML
    private Button upload_yazdır;

    private void printImage(BufferedImage image) {
        PrinterJob printJob = PrinterJob.createPrinterJob();
        java.awt.print.PrinterJob printerJob = java.awt.print.PrinterJob.getPrinterJob();

        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex != 0) {
                    return NO_SUCH_PAGE;
                }
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

    @FXML
    void initialize() {
        PrinterJob printerJob = Objects.requireNonNull(PrinterJob.createPrinterJob(), "Cannot create printer job");

        load_adapter adapter = new load_adapter();
        recycler_vıew.setAdapter(adapter);
        for (int i = 0; i < 30; i++) {
            load_model theModel = new load_model(String.valueOf(i), null, "", "", "1", null, null);
            recycler_vıew.getItems().add(theModel);
        }
        ListView<String> deneme = new ListView<>();

        upload_yazdır.setOnMouseClicked(mouseEvent -> {
            printerJob.showPrintDialog(recycler_vıew.getScene().getWindow());
            if (printerJob != null && printerJob.showPrintDialog(recycler_vıew.getScene().getWindow())) {

                WritableImage writableImage = recycler_vıew.snapshot(new SnapshotParameters(), null);
                WritableImage image = recycler_vıew.snapshot(new SnapshotParameters(), null);
                File file = new File("nodeImage.png");

                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

                    Image imageToPrint = new Image(file.toURI().toString());
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageToPrint, null);
                    printImage(bufferedImage);
                } catch (IOException ex) {
                    System.out.println(ex.toString());
                }
            }

        });
    }
}

