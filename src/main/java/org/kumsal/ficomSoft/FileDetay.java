package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kumsal.ficomSoft.AdapterModelClass.load_model;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FileDetay {

    public Button upload_yazdır;
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
    MysqlDataSource dataSource = ConnectorMysql.connect();
    ObservableList<load_model> models;

    @FXML
    void initialize() throws SQLException {
        models = FXCollections.observableArrayList();
        konu.setCellValueFactory(new PropertyValueFactory<>("konu"));
        evraktarihi.setCellValueFactory(new PropertyValueFactory<>("evrakTarihi"));
        tarih.setCellValueFactory(new PropertyValueFactory<>("time"));
        sayi.setCellValueFactory(new PropertyValueFactory<>("sayi"));
        adet.setCellValueFactory(new PropertyValueFactory<>("count"));
        sayfaAdedi.setCellValueFactory(new PropertyValueFactory<>("adet"));
        imhatarihi.setCellValueFactory(new PropertyValueFactory<>("imhaTarihi"));

        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(
                "SELECT * FROM `file_detail` WHERE LFID=?"
        );
        preparedStatement.setInt(1, LoadedFile.fileIDFor);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        int count = 1;
        JFXTextField sayi;
        JFXTextField konu;
        JFXTextField adet;
        DatePicker date;
        DatePicker evrakTarihi;
        DatePicker imhaTarihi;

        models.clear();
        while (resultSet.next()) {
            sayi = new JFXTextField();
            sayi.setEditable(false);
            konu = new JFXTextField();
            konu.setEditable(false);
            adet = new JFXTextField();
            adet.setEditable(false);
            date = new DatePicker();
            date.setEditable(false);
            evrakTarihi = new DatePicker();
            evrakTarihi.setEditable(false);
            imhaTarihi = new DatePicker();
            imhaTarihi.setEditable(false);

            sayi.setText(resultSet.getString(3));
            konu.setText(resultSet.getString(4));
            adet.setText(resultSet.getString(5));
            String dateStr = resultSet.getString(6);
            String evrakDate = resultSet.getString(7);
            String imHtarihi = resultSet.getString(8);
            if (!dateStr.equals("")) {
                date.setValue(LocalDate.parse(dateStr));
            }
            if (!evrakDate.equals("")) {
                evrakTarihi.setValue(LocalDate.parse(evrakDate));
            }
            if (!imHtarihi.equals("")) {
                imhaTarihi.setValue(LocalDate.parse(imHtarihi));
            }
            load_model theModel2 = new load_model(String.valueOf(count), sayi, konu, adet, date, evrakTarihi, imhaTarihi);
            models.add(theModel2);
            count++;
        }
        table.getItems().addAll(models);
        upload_yazdır.setOnMouseClicked(mouseEvent -> {
           if (models.size()>0){
               printer_model printer_model;
               Load.theModels.clear();
               int counter=1;
               for (load_model load_model:models){
                   printer_model=new printer_model(String.valueOf(counter),
                           load_model.getTime().getValue() != null ? load_model.getTime().getValue().toString() : "",
                           load_model.getSayi().getText(),load_model.getKonu().getText(),
                           load_model.getAdet().getText(),
                           load_model.getEvrakTarihi().getValue() != null ? load_model.getEvrakTarihi().getValue().toString() : "",
                           load_model.getImhaTarihi().getValue() != null ? load_model.getImhaTarihi().getValue().toString() : "");
                   Load.theModels.add(printer_model);

               }
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
           }
        });
    }


}
