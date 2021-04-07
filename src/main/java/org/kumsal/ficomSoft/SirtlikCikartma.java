package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class SirtlikCikartma {

    public ImageView imageView;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<SirtlikModel> table;

    @FXML
    private TableColumn<SirtlikModel, JFXCheckBox> ısCheck;

    @FXML
    private TableColumn<SirtlikModel, String> destisno;

    @FXML
    private TableColumn<SirtlikModel, String> birimad;

    @FXML
    private TableColumn<SirtlikModel, String> spdkod;

    @FXML
    private TableColumn<SirtlikModel, String> spdkarsilik;

    @FXML
    private TableColumn<SirtlikModel, String> ozelkod;

    @FXML
    private TableColumn<SirtlikModel, String> ozelkarsilik;

    @FXML
    private TableColumn<SirtlikModel, String> klasno;

    @FXML
    private TableColumn<SirtlikModel, String> ktarihi;

    @FXML
    private TableColumn<SirtlikModel, String> aciklama;

    @FXML
    private TableColumn<SirtlikModel, String> yuktarihi;

    @FXML
    private JFXButton yazdir;

    @FXML
    private JFXRadioButton oprion1;

    @FXML
    private DatePicker first;

    @FXML
    private DatePicker seccond;

    @FXML
    private JFXRadioButton option2;

    @FXML
    private DatePicker onlyDate;

    MysqlDataSource dbSource = ConnectorMysql.connect();
    ObservableList<SirtlikModel> modelObservableValue;
    public static ArrayList<sirtlikModel2> allDataSirtlik = new ArrayList<>();
    public ArrayList<sirtlikModel2> allTempElement = new ArrayList<>();
    @FXML
    void initialize() throws SQLException {
        imageView.setImage(new Image(getClass().getResourceAsStream("image/print.png")));
        modelObservableValue = FXCollections.observableArrayList();
        onlyDate.setDisable(true);
        ısCheck.setCellValueFactory(new PropertyValueFactory<>("ısCheck"));
        destisno.setCellValueFactory(new PropertyValueFactory<>("destisno"));
        birimad.setCellValueFactory(new PropertyValueFactory<>("birimad"));
        spdkod.setCellValueFactory(new PropertyValueFactory<>("spdkod"));
        spdkarsilik.setCellValueFactory(new PropertyValueFactory<>("spdkarsilik"));
        ozelkod.setCellValueFactory(new PropertyValueFactory<>("ozelkod"));
        ozelkarsilik.setCellValueFactory(new PropertyValueFactory<>("ozelkarsilik"));
        klasno.setCellValueFactory(new PropertyValueFactory<>("klasno"));
        ktarihi.setCellValueFactory(new PropertyValueFactory<>("ktarihi"));
        aciklama.setCellValueFactory(new PropertyValueFactory<>("imhaTarihi"));
        yuktarihi.setCellValueFactory(new PropertyValueFactory<>("yuktarihi"));
        int count=0;
        yazdir.setOnMouseClicked(mouseEvent -> {
            allTempElement.clear();
            for (SirtlikModel model : table.getItems()) {
                if (model.getIsCheck().isSelected()) {
                    sirtlikModel2 theModel = new sirtlikModel2(
                            model.getDestisno(),
                            model.getBirimad(),
                            model.getSpdkod(),
                            model.getSpdkarsilik(),
                            model.getOzelkod(),
                            model.getOzelkarsilik(),
                            model.getOzelkarsilik(),
                            model.getKtarihi(),
                            model.getImhaTarihi()
                    );
                    if (!allTempElement.contains(theModel))
                        allTempElement.add(theModel);
                }

            }
            allDataSirtlik.clear();
            if (allTempElement.size() > 0) {
                allDataSirtlik.addAll(allTempElement);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("sirtlikYazdir.fxml"));
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
            } else {
                Notifications.create()
                        .title("Hata")
                        .text("Sırtlık seçilmedem işlem yürütülemez..")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BASELINE_LEFT)
                        .showError();

            }
        });


        ToggleGroup group = new ToggleGroup();
        group.getToggles().add(option2);
        group.getToggles().add(oprion1);
        oprion1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (oprion1.isSelected()) {
                    onlyDate.setDisable(true);
                    first.setDisable(false);
                    seccond.setDisable(false);
                }
            }
        });
        option2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (option2.isSelected()) {
                    onlyDate.setDisable(false);
                    first.setDisable(true);
                    seccond.setDisable(true);
                    table.getItems().clear();
                    if (onlyDate.getValue() != null) {
                        LocalDate date = onlyDate.getValue();
                        String date2 = date.toString();
                        for (SirtlikModel models : modelObservableValue) {
                            if (models.getKtarihi().equals(date2)) {
                                table.getItems().add(models);
                            }
                        }
                    }

                }
            }
        });

        File file = new File("src/main/resources/org/kumsal/ficomSoft/image/ficomtranslogo.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        table.setPlaceholder(imageView);
        onlyDate.valueProperty().addListener((observableValue, localDate, t1) -> {
            table.getItems().clear();
            if (onlyDate.getValue() != null) {
                LocalDate date = onlyDate.getValue();
                String date2 = date.toString();
                for (SirtlikModel models : modelObservableValue) {
                    if (models.getKtarihi().equals(date2)) {
                        table.getItems().add(models);
                    }
                }
            }

        });
        first.valueProperty().addListener((observableValue, localDate, t1) -> {
            table.getItems().clear();
            if (seccond.getValue() != null && first.getValue() != null) {
                if (betweenDate(first.getValue(), seccond.getValue())) {
                    for (SirtlikModel models : modelObservableValue) {
                        if (betweenDateForPivot(first.getValue(), seccond.getValue(), models.getKtarihi())) {
                            table.getItems().add(models);
                        }
                    }
                }
            }
        });
        seccond.valueProperty().addListener((observableValue, localDate, t1) -> {
            table.getItems().clear();
            if (seccond.getValue() != null && first.getValue() != null) {
                if (betweenDate(first.getValue(), seccond.getValue())) {
                    for (SirtlikModel models : modelObservableValue) {
                        if (betweenDateForPivot(first.getValue(), seccond.getValue(), models.getKtarihi())) {
                            table.getItems().add(models);
                        }
                    }
                }
            }
        });

        PreparedStatement fileList = dbSource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.imhatarihi,a.prossTime FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID WHERE own.ownname=? AND own.login_id=?");
        fileList.setString(1, PrimaryController.type);
        fileList.setInt(2,PrimaryController.ID);
        ResultSet resultSet = fileList.executeQuery();
        SirtlikModel loadedFile;
        int sira = 1;
        JFXButton sil;
        while (resultSet.next()) {

            loadedFile = new SirtlikModel(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    new JFXCheckBox()
            );
            modelObservableValue.add(loadedFile);
        }
//            table.add(loadedFile);
    }

    public static boolean betweenDateForPivot(LocalDate first, LocalDate second, String pwvot) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date date1 = myFormat.parse(first.toString());
            Date date2 = myFormat.parse(second.toString());
            Date pivot = myFormat.parse(pwvot);
            long diff = pivot.getTime() - date1.getTime();
            long diff2 = date2.getTime() - pivot.getTime();
            if (diff >= 0 && diff2 >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean betweenDate(LocalDate first, LocalDate second) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date date1 = myFormat.parse(first.toString());
            Date date2 = myFormat.parse(second.toString());
            long diff = date2.getTime() - date1.getTime();
            long differance = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (differance < 0) {
                Notifications.create()
                        .title("Hata")
                        .text("Son tarih ilk tarihten önce olamaz")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER_LEFT)
                        .showError();
                this.first.setValue(this.seccond.getValue());
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}




