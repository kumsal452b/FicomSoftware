package org.kumsal.ficomSoft;

import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kairos.layouts.RecyclerView;

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

    private void printImage() throws InterruptedException {
        PrinterJob printJob = PrinterJob.createPrinterJob();
        PageLayout layout = printJob.getPrinter().createPageLayout(javafx.print.Paper.A4, PageOrientation.PORTRAIT, 0,15,0,0);
        printJob.getJobSettings().setPageLayout(layout);
        boolean accept=printJob.showPrintDialog(pane.getScene().getWindow());
        boolean success=false;

        if (accept){
            for (int i = 0; i < allOfList.size(); i++) {
                recycler.getItems().clear();
                recycler.getItems().addAll(allOfList.get(i));
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
            if ((i%11==0 && i!=0)|| i==Load.theModels.size()-1){
                allOfList.add(partOfList);
                partOfList=new ArrayList<>();
            }
            allGlobElement.add(theModel);
            recycler.getItems().add(theModel);
        }
        yazdir.setOnMouseClicked(mouseEvent -> {

            try {
                printImage();
            } catch (InterruptedException ignored) {

            }
        });

    }
}
