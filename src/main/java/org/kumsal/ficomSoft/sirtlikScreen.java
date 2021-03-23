package org.kumsal.ficomSoft;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class sirtlikScreen {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane pane;

    @FXML
    private RecyclerView<RecyclerView<sirtlikModel2>> recycler;

    @FXML
    private Button yazdir;

    @FXML
    private Button iptal;

    private RecyclerView<sirtlikModel2> recyclerView=new RecyclerView<>();

    private ArrayList<RecyclerView<sirtlikModel2>> horizontalDate=new ArrayList<>();

    private void printImage(BufferedImage image) throws InterruptedException {
        PrinterJob printJob = PrinterJob.createPrinterJob();
        PageLayout layout = printJob.getPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        printJob.getJobSettings().setPageLayout(layout);
        boolean accept=printJob.showPrintDialog(pane.getScene().getWindow());
        boolean success=false;


        if (accept){
            for (int i = 0; i < allOfList.size(); i++) {
                recycler.getItems().clear();
//                recycler.getItems().addAll(allOfList.get(i));
//                success= printJob.printPage(pane);
//                if (!success){
//                    recycler.getItems().addAll(allGlobElement);
//                    break;
//                }
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

        recyclerView.setAdapter(new sirlik_adapter());
        recyclerView.setOrientation(Orientation.VERTICAL);

        recycler.setAdapter(new sirtlikHorizontalAdapter());
        recycler.setOrientation(Orientation.VERTICAL);
        int count=0;
        int index=0;
        ArrayList<sirtlikModel2> tempData=new ArrayList<>();
        ArrayList<RecyclerView<sirtlikModel2>> horizontal=new ArrayList<>();
        for (sirtlikModel2 model2:SirtlikCikartma.allDataSirtlik){
            tempData.add(model2);
            index++;
            count++;
            if (count%3==0 && count!=0){
                recyclerView.getItems().addAll(tempData);
                tempData.clear();
                horizontal.add(recyclerView);
                recyclerView=new RecyclerView<>();
                recyclerView.setAdapter(new sirlik_adapter());
                recyclerView.setOrientation(Orientation.VERTICAL);
            }
        }
        recycler.getItems().addAll(horizontal);
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
