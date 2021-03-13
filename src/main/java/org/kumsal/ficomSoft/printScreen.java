package org.kumsal.ficomSoft;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import org.kairos.layouts.RecyclerView;
import org.kumsal.ficomSoft.AdapterModelClass.load_adapter;
import org.kumsal.ficomSoft.AdapterModelClass.load_model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class printScreen {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane pane;

    @FXML
    private RecyclerView<load_model> recycler;

    @FXML
    private Button yazdir;

    private void printImage(BufferedImage image) {
        PrinterJob printJob = PrinterJob.createPrinterJob();
        PageLayout layout = printJob.getPrinter().createPageLayout(javafx.print.Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL_OPPOSITES);
        for (int i = 0; i < 1; i++) {
            printJob.printPage(layout, pane);
        }
        printJob.showPrintDialog(pane.getScene().getWindow());
        printJob.getJobSettings().setPageLayout(layout);
        if (printJob.showPrintDialog(pane.getScene().getWindow())) {
            printJob.endJob();
        }

    }

    @FXML
    void initialize() {

        load_adapter adapter = new load_adapter();
        recycler.setAdapter(adapter);
        for (int i = 0; i < 12; i++) {
            load_model theModel = new load_model(String.valueOf(i), null, "", "", "1", null, null);
            recycler.getItems().add(theModel);
        }
        yazdir.setOnMouseClicked(mouseEvent -> {
            SnapshotParameters snapshotParameters = new SnapshotParameters();
//            snapshotParameters.setFill(C)
            URL url = null;
            try {
                url = new File("src/main/resources/org/kumsal/ficomSoft/image/image1.jpg").toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
//            Image image1 = new Image(url.toString());
//            ImageView imageView = new ImageView(image1);
//            printImage(imageView);
            Book book = new Book();

            SnapshotParameters parameters = new SnapshotParameters();

            WritableImage writableImage = recycler.snapshot(new SnapshotParameters(), null);
            WritableImage image = recycler.snapshot(new SnapshotParameters(), null);
            File file = new File("nodeImage.png");

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

                Image imageToPrint = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(imageToPrint);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageToPrint, null);


                printImage(bufferedImage);
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        });

    }
}
