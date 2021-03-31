package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.xdevapi.PreparableStatement;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kumsal.ficomSoft.AdapterModelClass.LoginModel;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrimaryController {


    @FXML
    public ImageView prim_imageView;

    public JFXButton exit;
    public JFXButton twitter;
    public JFXButton facebook;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private ScrollPane scrolpane1;
    @FXML
    private AnchorPane main_anchor_pane;

    private int count = 1;
    private String currentPath = "";
    private FadeTransition fadeTransition;
    private FadeTransition fadeTransition2;


    @FXML
    private JFXPasswordField login_password;

    @FXML
    private JFXTextField login_username;

    @FXML
    private JFXButton login_screen_button;

    RequiredFieldValidator validator = new RequiredFieldValidator();

    MysqlDataSource dbSource=null;
    ArrayList<Boolean> isAuthList=new ArrayList<>();
    @FXML
    void login(ActionEvent event) throws SQLException {
        PropertiesCache cache=PropertiesCache.isIsBreakDb();
        if (!cache.isBreak){
           if (!login_username.getText().equals("") && !login_password.getText().equals("")){
               dbSource = ConnectorMysql.connect();
               ResultSet resultSet;
               String query = "select * from admin";
               String query2 = "select * from users";

               ArrayList<LoginModel> theLoggedAdmin = new ArrayList<>();
               ArrayList<LoginModel> theLoggedUsers = new ArrayList<>();

               try{
                   Statement forAdmin = dbSource.getConnection().createStatement();
                   Statement forusers = dbSource.getConnection().createStatement();

                   forusers.execute(query2);
                   forAdmin.execute(query);

                   resultSet = forAdmin.getResultSet();
                   while (resultSet.next()) {
                       LoginModel theModel = new LoginModel("Admin",
                               resultSet.getString("ad"),
                               resultSet.getString("soyad"),
                               resultSet.getString("username"),
                               resultSet.getString("password"),
                               resultSet.getString("AID"));
                       theLoggedAdmin.add(theModel);
                   }
                   resultSet = forusers.getResultSet();
                   while (resultSet.next()) {
                       LoginModel theModel = new LoginModel("User",
                               resultSet.getString("ad"),
                               resultSet.getString("soyad"),
                               resultSet.getString("username"),
                               resultSet.getString("password"),
                               resultSet.getString("UID"));
                       theLoggedUsers.add(theModel);
                       isAuthList.add(resultSet.getBoolean(6));
                   }
                   String theUsername = login_username.getText();
                   String thePassword = login_password.getText();
                   String loginBy = "";

                   loggedSetings(event, theLoggedAdmin, theUsername, thePassword, false);
                   loggedSetings(event, theLoggedUsers, theUsername, thePassword, true);
               }catch (Exception e){
                   System.out.println(e.getMessage());
                   ButtonType foo = new ButtonType("Tamam", ButtonBar.ButtonData.OK_DONE);
                   Alert alert = new Alert(Alert.AlertType.WARNING,"Uzak sunucu hatası \n"+e.getMessage(),foo);
                   alert.setTitle("Uyarı");
                   alert.setHeaderText("Uyarı");
                   alert.show();
               }
           }
           else {
            showError("Alanları doldurmak zorunludur.");
           }
        }else{
            ButtonType foo = new ButtonType("Tamam", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.WARNING,"Uzak sumucu bağlantısı hasarlı. Lütfen uygulamayı tekrar yükleyiniz veya uza" +
                    "k sunucu ayarlarını kontrol ediniz.",foo);
            alert.setTitle("Uyarı");
            alert.setHeaderText("Uyarı");
            alert.show();
        }
    }

    static public String type = "";
    static public String name = "";
    static public String username="";
    static public  Stage stage;
    static public String datetime="";
    static public String ownTypeID="";
    static public Boolean isAuth=false;
    static public int ID=0;
    private void loggedSetings(ActionEvent event, ArrayList<LoginModel> sendLogedData, String theUsername, String thePassword, boolean isEnd) {
        for (int i = 0; i < sendLogedData.size(); i++) {
            if (sendLogedData.get(i).getPassword().equals(thePassword) && sendLogedData.get(i).getUsername().equals(theUsername)) {
                Node node = (Node) event.getSource();
                // Step 3

                stage = (Stage) node.getScene().getWindow();
                stage.close();
                try {


                    // Step 4
                    // Step 5
                    type = sendLogedData.get(i).getLoginby();
                    username=sendLogedData.get(i).getUsername();
                    name = sendLogedData.get(i).getName() + " " + sendLogedData.get(i).getSurname();
                    ID=Integer.valueOf(sendLogedData.get(i).getId());
                    stage.setUserData(sendLogedData.get(i));
                    if (type.equals("User")){
                        isAuth=isAuthList.get(i);
                    }
                    PreparedStatement ownSave=dbSource.getConnection().prepareStatement("INSERT INTO `owntype` (`OTID`, `ownname`, `login_id`, `username`, `date`) VALUES (NULL, ?, ?, ?, ?)");
                    ownSave.setString(1,type);
                    ownSave.setString(2,sendLogedData.get(i).getId());
                    ownSave.setString(3,username);
                    java.util.Date dt = new java.util.Date();
                    java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    datetime = sdf.format(dt);
                    ownSave.setString(4,datetime);
                    ownSave.execute();

                    PreparedStatement ownType=dbSource.getConnection().prepareStatement("select * from ownType where date=? and username=?");
                    ownType.setString(1,datetime);
                    ownType.setString(2,username);
                    ResultSet resultSet=ownType.executeQuery();
                    while (resultSet.next()) {
                        ownTypeID=resultSet.getString("OTID");
                    }

                    Parent root = FXMLLoader.load(getClass().getResource("main_screen.fxml"));


                    // Step 6
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.setWidth(1200);
                    stage.setHeight(630);
                    stage.setMinHeight(630);
                    stage.setMinWidth(1200);
                    // Step 7
                    stage.show();
                    isEnd=false;
                    return;
                } catch (IOException | SQLException e) {
                    System.err.println(String.format("Error: %s", e.getMessage()));
                }
            }
            if (isEnd) {
                if (i == sendLogedData.size() - 1) {
                    Notifications.create()
                            .title("Hata")
                            .text("Kullanıcı veya şifreniz hatalıdır..")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.CENTER_LEFT)
                            .showError();
                }
            }
        }
        if (sendLogedData.size() == 0) {
            showError("Kullanıcı adı veya şifreyi doğrulayamadık");
        }
    }

    private void showError(String s) {
        validator.setMessage(s);
        login_password.validate();
        login_username.validate();
    }


    @FXML
    void initialize() throws MalformedURLException, URISyntaxException, InterruptedException {

        exit.setOnAction(event -> {
            System.exit(0);
        });
        facebook.setOnAction(event -> {
            URI uri = null;
            try {
                uri=new URI("https://www.facebook.com/ficom.ismet.3");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        twitter.setOnAction(event -> {
            URI uri = null;
            try {
                uri=new URI("https://www.twitter.com/BilisimFicom");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        login_username.getValidators().add(validator);
        login_password.getValidators().add(validator);

        login_username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (s.length() > 0) {
                    login_username.resetValidation();
                }
            }
        });

        login_password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (s.length() > 0) {
                    login_password.resetValidation();
                }
            }
        });

        fadeTransition = new FadeTransition(Duration.millis(4000));
        fadeTransition.setNode(prim_imageView);
        fadeTransition2 = new FadeTransition(Duration.millis(4000));
        fadeTransition2.setNode(prim_imageView);
        currentPath = "src/main/resources/org/kumsal/ficomsoft/image/image" + count + ".jpg";
        changeImage(currentPath);

        setFadeFromAnim();
        double newMeasure = Math.min(prim_imageView.getImage().getWidth(), prim_imageView.getImage().getHeight());
        double x = (prim_imageView.getImage().getWidth() - newMeasure) / 2;
        double y = (prim_imageView.getImage().getHeight() - newMeasure) / 2;


        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        prim_imageView.setViewport(rect);
        prim_imageView.setFitWidth(600);
        prim_imageView.setFitHeight(500);
        prim_imageView.setSmooth(true);
        double a = main_anchor_pane.prefWidthProperty().divide(2).subtract(prim_imageView.fitWidthProperty().divide(2).get()).get();
        prim_imageView.xProperty().bind(main_anchor_pane.prefWidthProperty().divide(2).subtract(prim_imageView.fitWidthProperty().divide(2)));
//        prim_imageView.setX(500);
    }

    public void changeImage(String path) {
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        prim_imageView.setImage(image);
        double newMeasure = Math.min(prim_imageView.getImage().getWidth(), prim_imageView.getImage().getHeight());
        double x = (prim_imageView.getImage().getWidth() - newMeasure) / 2;
        double y = (prim_imageView.getImage().getHeight() - newMeasure) / 2;
        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        prim_imageView.setViewport(rect);
        prim_imageView.setFitWidth(600);
        prim_imageView.setFitHeight(500);
    }

    public void setFadeFromAnim() {
        fadeTransition2.setFromValue(1);
        fadeTransition2.setToValue(0);
        fadeTransition2.setOnFinished(actionEvent -> {

            count++;
            if (count > 3) {
                count = 1;
            }
            currentPath = "src/main/resources/org/kumsal/ficomsoft/image/image" + count + ".jpg";
            changeImage(currentPath);
            setFadeToAnim();
        });
        fadeTransition2.play();
    }

    public void setFadeToAnim() {
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(actionEvent -> {
            setFadeFromAnim();
        });
        fadeTransition.play();
    }
}
