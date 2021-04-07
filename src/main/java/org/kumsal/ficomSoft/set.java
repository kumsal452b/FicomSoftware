package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class set {

    public JFXPasswordField yenisifretekrar;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<settingModel> table;

    @FXML
    private TableColumn<settingModel, String> username;

    @FXML
    private JFXTextField usernamegir;

    @FXML
    private JFXTextField ad;

    @FXML
    private JFXTextField soyad;

    @FXML
    private JFXTextField password;

    @FXML
    private JFXButton gunceller;

    @FXML
    private Label kullanicitur;

    @FXML
    private Label gunlukdosya;

    @FXML
    private Label toplamdosya;

    @FXML
    private Label rozetler;
    @FXML
    private JFXPasswordField eskisıfre;

    @FXML
    private JFXPasswordField yenisifre;

    @FXML
    private JFXButton sifredegistir;

    @FXML
    private JFXTextField yeniKullaniciAdi;

    @FXML
    private JFXButton kullaniciAdiDegistir;

    @FXML
    private CheckBox isAuth;


    @FXML
    private Label adminDailyLoged;

    @FXML
    private Label adminTotalLoged;


    @FXML
    private CheckBox notusername;

    MysqlDataSource dbsource = ConnectorMysql.connect();
    ObservableList<settingModel> theusersList;
    ObservableList<settingModel> adminAndList;
    private int GlobalID = 0;
    private String currentusername = "";
    ObservableList<LoadedFileModel> theFileModel;
    int dailyLoged = 0;
    int totalLoged = 0;
    ArrayList<String> prossTime = new ArrayList<>();
    String currentPassword = "";

    int currentID=0;
    int countForAdmin=0;
    int countForUser=0;
    int countForDaily=0;
    boolean isAdmin=false;

    @FXML
    void initialize() throws SQLException, ParseException {
        adminAndList = FXCollections.observableArrayList();
        theFileModel = FXCollections.observableArrayList();
        theusersList = FXCollections.observableArrayList();
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        PreparedStatement statement = dbsource.getConnection().prepareStatement("select * from users");
        ResultSet resultSet = statement.executeQuery();
        settingModel themodel;
        settingModel themodel2;
        while (resultSet.next()) {
            themodel = new settingModel(
                    resultSet.getString(4),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(5),
                    resultSet.getBoolean(6),
                    resultSet.getInt(1)
            );
            if (themodel.getUsername().equals(PrimaryController.username)) {
                currentPassword = themodel.getPassword();
                currentID = themodel.getId();
                currentusername = themodel.getUsername();
            }
        }
        PreparedStatement forAdmin = dbsource.getConnection().prepareStatement("select * from Admin");
        resultSet = forAdmin.executeQuery();
        while (resultSet.next()) {
            themodel2 = new settingModel(
                    resultSet.getString(4),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(5),
                    resultSet.getInt(1)
            );
            if (!themodel2.getUsername().equals(PrimaryController.username)) {
                adminAndList.add(themodel2);
            }else{
                currentID=themodel2.getId();
            }
        }

        if (PrimaryController.type.equals("Admin")) {
            PreparedStatement statement1 = dbsource.getConnection().prepareStatement("select * from users");
            ResultSet resultSet2 = statement1.executeQuery();
            while (resultSet2.next()) {
                themodel = new settingModel(
                        resultSet2.getString(4),
                        resultSet2.getString(2),
                        resultSet2.getString(3),
                        resultSet2.getString(5),
                        resultSet2.getBoolean(6),
                        resultSet2.getInt(1)
                );

                if (!themodel.getUsername().equals(PrimaryController.username)) {
                    theusersList.add(themodel);
                }
            }
            table.getItems().addAll(theusersList);
            table.getItems().addAll(adminAndList);
            table.getSelectionModel().selectedItemProperty().addListener((observableValue, settingModel, t1) -> {
                if (theusersList.contains(t1)){
                    try {
                        PreparedStatement fileList2 = dbsource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.tarih,a.imhatarihi,a.LFID,a.OTID ,a.prossTime FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID  WHERE own.ownname=? AND own.login_id=?");
                        fileList2.setString(1, "User");
                        fileList2.setInt(2,t1.getId());
                        ResultSet fıleResultsSet = fileList2.executeQuery();
                        LoadedFileModel loadedFile;
                        while (fıleResultsSet.next()) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            String model=fıleResultsSet.getString(14);
                            java.util.Date date11 = format.parse(model);
                            System.out.println(date11.toString());
                            LocalDateTime tume = date11.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime();
                            System.out.println(tume.toString());
                            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            String originalDate = tume.format(customFormatter);
                            Date date = Date.valueOf(originalDate);
                            if (isToday(date)) {
                                countForDaily++;
                            }
                            countForUser++;
                        }
                        adminTotalLoged.setText("Toplam giriş: "+countForUser);
                        adminDailyLoged.setText("Günlük giriş : "+countForDaily);
                        countForUser=0;
                        countForDaily=0;
                    } catch (SQLException | ParseException throwables) {
                        throwables.printStackTrace();
                    }

                    ad.setText(t1.getName());
                    soyad.setText(t1.getSurname());
                    usernamegir.setText(t1.getUsername());
                    password.setText(t1.getPassword());
                    isAuth.setSelected(t1.getIssAuth());
                    GlobalID = t1.getId();
                    isAuth.setVisible(true);
                    isAdmin=false;
                }
                if (adminAndList.contains(t1)){
                    try {
                        PreparedStatement fileList2 = dbsource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.tarih,a.imhatarihi,a.LFID,a.OTID ,a.prossTime FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID  WHERE own.ownname=? AND own.login_id=?");
                        fileList2.setString(1, "Admin");
                        fileList2.setInt(2,t1.getId());

                        ResultSet fıleResultsSet = fileList2.executeQuery();
                        LoadedFileModel loadedFile;
                        while (fıleResultsSet.next()) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            String model=fıleResultsSet.getString(14);
                            java.util.Date date11 = format.parse(model);
                            System.out.println(date11.toString());
                            LocalDateTime tume = date11.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime();
                            System.out.println(tume.toString());
                            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            String originalDate = tume.format(customFormatter);
                            Date date = Date.valueOf(originalDate);
                            if (isToday(date)) {
                                countForDaily++;
                            }
                            countForAdmin++;
                        }
                        adminTotalLoged.setText("Toplam giriş: "+countForAdmin);
                        adminDailyLoged.setText("Günlük giriş : "+countForDaily);
                        countForAdmin=0;
                        countForDaily=0;
                    } catch (SQLException | ParseException throwables) {
                        throwables.printStackTrace();
                    }
                    ad.setText(t1.getName());
                    soyad.setText(t1.getSurname());
                    usernamegir.setText(t1.getUsername());
                    password.setText(t1.getPassword());
                    isAuth.setVisible(false);
                    GlobalID = t1.getId();
                    isAdmin=true;
                }
            });
            gunceller.setOnAction(event -> {
                PreparedStatement updateUers = null;
                if (ad.getText().equals("")) {
                    Notifications.create()
                            .title("Hata")
                            .text("Ad boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (soyad.getText().equals("")) {
                    Notifications.create()
                            .title("Hata")
                            .text("Soyad boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (usernamegir.getText().equals("")) {
                    Notifications.create()
                            .title("Hata")
                            .text("Kullanıcı adı boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
                if (!notusername.isSelected()){
                    for (settingModel themodelIn : theusersList) {
                        if (themodelIn.getUsername().equals(usernamegir.getText()) && !themodelIn.getUsername().equals(PrimaryController.username)) {
                            Notifications.create()
                                    .title("Hata")
                                    .text("Aynı Kullanıcı adı zaten mevcut.")
                                    .hideAfter(Duration.seconds(3))
                                    .position(Pos.BASELINE_LEFT)
                                    .showWarning();
                            return;
                        }
                    }
                }
                if (ad.getText().equals("")) {
                    Notifications.create()
                            .title("Hata")
                            .text("Özel kod karşılık boş bırakılamaz.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BASELINE_LEFT)
                            .showError();
                    return;
                }
               if (isAdmin){
                   try {
                       updateUers = dbsource.getConnection().prepareStatement("UPDATE `admin` SET `ad` = ?, `soyad` = ?, `username` = ?, `password` = ? WHERE `admin`.`AID` = ?");
                       updateUers.setString(1, ad.getText());
                       updateUers.setString(2, soyad.getText());
                       updateUers.setString(3, usernamegir.getText());
                       updateUers.setString(4, password.getText());
                       updateUers.setInt(5,GlobalID);
                       updateUers.execute();
                       Notifications.create()
                               .title("Başarılı")
                               .text("Kullanıcı başarılı bir şekilde güncellendi.")
                               .hideAfter(Duration.seconds(3))
                               .position(Pos.BASELINE_LEFT)
                               .showConfirm();
                   } catch (SQLException throwables) {
                       Notifications.create()
                               .title("Hata")
                               .text("Güncelleme sırasında bir hata oluştu, lütden daha sonra tekrar deneyiniz..")
                               .hideAfter(Duration.seconds(3))
                               .position(Pos.BASELINE_LEFT)
                               .showError();
                       throwables.printStackTrace();
                   }

               }else{
                   try {
                       updateUers = dbsource.getConnection().prepareStatement("UPDATE `users` SET `ad` = ?, `soyad` = ?, `username` = ?, `password` = ? , `isAuth` =? WHERE `users`.`UID` = ?");
                       updateUers.setString(1, ad.getText());
                       updateUers.setString(2, soyad.getText());
                       updateUers.setString(3, usernamegir.getText());
                       updateUers.setString(4, password.getText());
                       updateUers.setBoolean(5, isAuth.isSelected());
                       updateUers.setInt(6, GlobalID);
                       updateUers.execute();
                       Notifications.create()
                               .title("Başarılı")
                               .text("Kullanıcı başarılı bir şekilde güncellendi.")
                               .hideAfter(Duration.seconds(3))
                               .position(Pos.BASELINE_LEFT)
                               .showConfirm();
                   } catch (SQLException throwables) {
                       Notifications.create()
                               .title("Hata")
                               .text("Güncelleme sırasında bir hata oluştu, lütden daha sonra tekrar deneyiniz..")
                               .hideAfter(Duration.seconds(3))
                               .position(Pos.BASELINE_LEFT)
                               .showError();
                       throwables.printStackTrace();
                   }
               }

            });
        }
        PreparedStatement fileList = dbsource.getConnection().prepareStatement("SELECT de.destisno,a.birim,a.spd_kod,a.spdkarsilik,a.ozel_kod,a.ozelkarsilik,a.klsorno,a.tarih,a.aciklama,a.tarih,a.imhatarihi,a.LFID,a.OTID ,a.prossTime FROM `load_flle` a INNER JOIN destis de ON a.DID=de.DID INNER JOIN owntype own ON own.OTID=a.OTID  WHERE own.ownname=? AND own.login_id=?");
        fileList.setString(1, PrimaryController.type);
        fileList.setInt(2,PrimaryController.ID);

        ResultSet fıleResultsSet = fileList.executeQuery();
        LoadedFileModel loadedFile;
        int sira = 1;
        while (fıleResultsSet.next()) {
            loadedFile = new LoadedFileModel(
                    String.valueOf(sira),
                    fıleResultsSet.getString(1),
                    fıleResultsSet.getString(2),
                    fıleResultsSet.getString(3),
                    fıleResultsSet.getString(4),
                    fıleResultsSet.getString(5),
                    fıleResultsSet.getString(6),
                    fıleResultsSet.getString(7),
                    fıleResultsSet.getString(8),
                    fıleResultsSet.getString(9),
                    fıleResultsSet.getString(10),
                    null,
                    null
            );

            prossTime.add(fıleResultsSet.getString(14));
            theFileModel.add(loadedFile);
            totalLoged++;
        }
        toplamdosya.setText(totalLoged + "");
        kullanicitur.setText(PrimaryController.type);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (String model : prossTime) {
            java.util.Date date11 = format.parse(model);
            System.out.println(date11.toString());
            LocalDateTime tume = date11.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            System.out.println(tume.toString());
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String originalDate = tume.format(customFormatter);
            Date date = Date.valueOf(originalDate);
            if (isToday(date)) {
                dailyLoged++;
            }
        }
        gunlukdosya.setText(dailyLoged + "");
        sifredegistir.setOnAction(event -> {
            try {
                if (!eskisıfre.getText().equals("") && !yenisifre.getText().equals("") && !yenisifretekrar.getText().equals("")) {
                    verifiyingUsers(currentPassword);
                } else {
                    Notifications.create()
                            .title("Hata")
                            .text("Boş alanları doldurun")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.CENTER_LEFT)
                            .showError();
                }
            } catch (SQLException throwables) {
                Notifications.create()
                        .title("Hata")
                        .text("Ölümcül hata meydana geldi. " + throwables.getErrorCode() + "\n" + throwables.getMessage())
                        .hideAfter(Duration.seconds(6))
                        .position(Pos.CENTER_LEFT)
                        .showError();
                throwables.printStackTrace();

            }
        });
        kullaniciAdiDegistir.setOnAction(event -> {
            if (!yeniKullaniciAdi.getText().equals("")){
                if (verifiyingUsername(yeniKullaniciAdi.getText())){
                    PreparedStatement updateUers = null;
                    try {
                        if (PrimaryController.type.equals("Admin")){
                            updateUers = dbsource.getConnection().prepareStatement("UPDATE `admin` SET `username` = ?  WHERE `admin`.`AID` = ?");
                            updateUers.setString(1, yeniKullaniciAdi.getText());
                            updateUers.setInt(2, currentID);
                            updateUers.execute();
                            Notifications.create()
                                    .title("Başarılı")
                                    .text("Kullanıcı adı başarılı bir şekilde güncellendi.")
                                    .hideAfter(Duration.seconds(3))
                                    .position(Pos.CENTER_LEFT)
                                    .showConfirm();

                        }else{
                            updateUers = dbsource.getConnection().prepareStatement("UPDATE `users` SET `username` = ?  WHERE `users`.`UID` = ?");
                            updateUers.setString(1, yeniKullaniciAdi.getText());
                            updateUers.setInt(2, currentID);
                            updateUers.execute();
                            currentPassword = yenisifre.getText();
                            Notifications.create()
                                    .title("Başarılı")
                                    .text("Kullanıcı adı başarılı bir şekilde güncellendi.")
                                    .hideAfter(Duration.seconds(3))
                                    .position(Pos.CENTER_LEFT)
                                    .showConfirm();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                }

            }else{
                Notifications.create()
                        .title("Hata")
                        .text("Boş alanları doldurunuz.")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER_LEFT)
                        .showError();
            }

        });
    }

    public boolean verifiyingUsername(String username) {
        if (!username.equals(yeniKullaniciAdi)) {
            for (settingModel model : theusersList) {
                if (model.getUsername().equals(yeniKullaniciAdi)) {
                    Notifications.create()
                            .title("Hata")
                            .text("Bu kullanici adi başka bir kullanıcı tarafından kullanılıyor.")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.CENTER_LEFT)
                            .showError();
                    return false;
                }
            }
            return true;

        } else {
            Notifications.create()
                    .title("Hata")
                    .text("Girilen kullanıcı adı, mevcut kullanıcı adıyla aynı.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.CENTER_LEFT)
                    .showError();
            return false;
        }
    }

    public boolean verifiyingUsers(String password) throws SQLException {

        if (currentPassword.equals(eskisıfre.getText())) {
            if (yenisifre.getText().equals(yenisifretekrar.getText())) {
               if (PrimaryController.type.equals("Admin")){
                   PreparedStatement updateUers = dbsource.getConnection().prepareStatement("UPDATE `admin` SET `password` = ?  WHERE `admin`.`AID` = ?");
                   updateUers.setString(1, yenisifre.getText());
                   updateUers.setInt(2, currentID);
                   updateUers.execute();
                   currentPassword = yenisifre.getText();
                   Notifications.create()
                           .title("Başarılı")
                           .text("Şifreniz başarılı bir şekilde güncellendi.")
                           .hideAfter(Duration.seconds(3))
                           .position(Pos.CENTER_LEFT)
                           .showConfirm();
                   return true;

               }else{
                   PreparedStatement updateUers = dbsource.getConnection().prepareStatement("UPDATE `users` SET `password` = ?  WHERE `users`.`UID` = ?");
                   updateUers.setString(1, yenisifre.getText());
                   updateUers.setInt(2, currentID);
                   updateUers.execute();
                   currentPassword = yenisifre.getText();
                   Notifications.create()
                           .title("Başarılı")
                           .text("Şifreniz başarılı bir şekilde güncellendi.")
                           .hideAfter(Duration.seconds(3))
                           .position(Pos.CENTER_LEFT)
                           .showConfirm();
                   return true;
               }

            } else {
                Notifications.create()
                        .title("Hata")
                        .text("Yeni şifreniz, tekrar girilmek istenene şifre ile uyuşmuyor.")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.CENTER_LEFT)
                        .showError();
                return false;
            }
        } else {
            Notifications.create()
                    .title("Hata")
                    .text("Eski şifreniz, yeni girilen şifre ile uyuşmuyor.")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.CENTER_LEFT)
                    .showError();
            return false;
        }

    }

    public boolean isToday(Date date) {
        LocalDate toDay = LocalDate.now();
        if (toDay.toString().equals(date.toLocalDate().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
