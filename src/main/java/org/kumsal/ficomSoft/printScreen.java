package org.kumsal.ficomSoft;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
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
        java.awt.print.PrinterJob printerJob = java.awt.print.PrinterJob.getPrinterJob();
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex != 0) {
                    return NO_SUCH_PAGE;
                }
                Paper paper=new Paper();
                paper.setSize(400,600);
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

    @FXML
    void initialize() {

        load_adapter adapter = new load_adapter();
        recycler.setAdapter(adapter);
        for (int i = 0; i < 30; i++) {
            load_model theModel = new load_model(String.valueOf(i), null, "", "", "1", null, null);
            recycler.getItems().add(theModel);
        }
        yazdir.setOnMouseClicked(mouseEvent -> {
            SnapshotParameters snapshotParameters=new SnapshotParameters();
//            snapshotParameters.setFill(C)
            URL url=null;
            try {
                url = new File("src/main/resources/org/kumsal/ficomSoft/image/image1.jpg").toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
//            Image image1 = new Image(url.toString());
//            ImageView imageView = new ImageView(image1);
//            printImage(imageView);
            Book book=new Book();

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
