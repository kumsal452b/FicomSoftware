package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.kairos.layouts.RecyclerView;
import org.kumsal.ficomSoft.AdapterModelClass.load_adapter;
import org.kumsal.ficomSoft.AdapterModelClass.load_model;

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

    @FXML
    void initialize() {

        load_model theModel = new load_model("1", LocalDate.now(), "12", "test", "1", LocalDate.now(), LocalDate.now());
        load_adapter adapter = new load_adapter();
        recycler_vıew.setAdapter(adapter);
        recycler_vıew.getItems().add(theModel);

    }
}
