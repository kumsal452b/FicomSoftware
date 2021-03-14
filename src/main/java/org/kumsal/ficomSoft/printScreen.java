package org.kumsal.ficomSoft;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kairos.layouts.RecyclerView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class printScreen {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane pane;

    @FXML
    private RecyclerView<printer_model> recycler;

    @FXML
    private Button yazdir;

    @FXML
    private Button iptal;

    private void printImage(BufferedImage image) throws InterruptedException {
        PrinterJob printJob = PrinterJob.createPrinterJob();
        PageLayout layout = printJob.getPrinter().createPageLayout(javafx.print.Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL_OPPOSITES);
        printJob.getJobSettings().setPageLayout(layout);
        boolean accept=printJob.showPrintDialog(pane.getScene().getWindow());
        boolean success=false;

        if (accept){
            for (int i = 0; i < allOfList.size(); i++) {
                recycler.getItems().clear();
                recycler.getItems().addAll(allOfList.get(i));
                Thread.sleep(2);
                success= printJob.printPage(pane);
                if (!success){
                    recycler.getItems().addAll(allGlobElement);
                    break;
                }
            }
        }

        if (success) {
           boolean test= printJob.endJob();
            System.out.println(test);
        }

    }
    ArrayList<ArrayList<printer_model>> allOfList=new ArrayList<>();
    ArrayList<printer_model> allGlobElement=new ArrayList<>();
    @FXML
    void initialize() {
        iptal.setOnMouseClicked(mouseEvent -> {
            Node node = (Node) mouseEvent.getSource();
            // Step 3
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        });
        printer_adapter adapter = new printer_adapter();
        recycler.setAdapter(adapter);
        ArrayList<printer_model> partOfList=new ArrayList<>();
        for (int i = 0; i < Load.theModels.size(); i++) {
            printer_model theModel = Load.theModels.get(i);
            partOfList.add(theModel);
            if ((i%14==0 && i!=0)|| i==Load.theModels.size()-1){
                allOfList.add(partOfList);
                partOfList=new ArrayList<>();
            }
            allGlobElement.add(theModel);
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

            WritableImage writableImage = recycler.snapshot(new SnapshotParameters(), null);
            WritableImage image = recycler.snapshot(new SnapshotParameters(), null);
            File file = new File("nodeImage.png");

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

                Image imageToPrint = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(imageToPrint);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageToPrint, null);


                printImage(bufferedImage);
            } catch (IOException | InterruptedException ex) {
                System.out.println(ex.toString());
            }
        });

    }
}
