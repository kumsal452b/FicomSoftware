package org.kumsal.ficomSoft;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kumsal.ficomSoft.AdapterModelClass.LoginModel;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

public class PrimaryController {


    @FXML
    public ImageView prim_imageView;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private ScrollPane scrolpane1;
    @FXML
    private AnchorPane main_anchor_pane;

    private int count=1;
    private String currentPath="";
    private FadeTransition fadeTransition;
    private FadeTransition fadeTransition2;



    @FXML
    private JFXPasswordField login_password;

    @FXML
    private JFXTextField login_username;

    @FXML
    private JFXButton login_screen_button;

    @FXML
    void login(ActionEvent event) throws SQLException {
        MysqlDataSource dbSource=ConnectorMysql.connect();
        ResultSet resultSet;
        String query="select * from admin";
        String query2="select * from users";

        ArrayList<LoginModel> theLoggedAdmin=new ArrayList<>();
        ArrayList<LoginModel> theLoggedUsers=new ArrayList<>();

        Statement forAdmin = dbSource.getConnection().createStatement();
        Statement forusers = dbSource.getConnection().createStatement();

        forusers.execute(query2);
        forAdmin.execute(query);

        resultSet=forAdmin.getResultSet();
        while (resultSet.next()){
            LoginModel theModel=new LoginModel("Admin",
                    resultSet.getString("ad"),
                    resultSet.getString("soyad"),
                    resultSet.getString("username"),
                    resultSet.getString("password"));
            theLoggedAdmin.add(theModel);
        }

        resultSet=forusers.getResultSet();
        while (resultSet.next()){
            LoginModel theModel=new LoginModel("User",
                    resultSet.getString("ad"),
                    resultSet.getString("soyad"),
                    resultSet.getString("username"),
                    resultSet.getString("password"));
            theLoggedUsers.add(theModel);
        }
        String theUsername=login_username.getText();
        String thePassword=login_password.getText();
        String loginBy="";

        loggedSetings(event, theLoggedAdmin, theLoggedAdmin, theUsername, thePassword);
        loggedSetings(event, theLoggedAdmin, theLoggedUsers, theUsername, thePassword);


    }

    private void loggedSetings(ActionEvent event, ArrayList<LoginModel> theLoggedAdmin, ArrayList<LoginModel> theLoggedUsers, String theUsername, String thePassword) {
        for (int i=0; i<theLoggedUsers.size();i++){
            if (theLoggedUsers.get(i).getPassword().equals(thePassword) && theLoggedUsers.get(i).getUsername().equals(theUsername)){
                Node node = (Node) event.getSource();
                // Step 3
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                try {
                    Object path=FXMLLoader.load(getClass().getResource("main_page.fxml"));
                    // Step 4
                    Parent root = FXMLLoader.load((URL) path);
                    // Step 5
                    stage.setUserData(theLoggedAdmin.get(i));
                    // Step 6
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    // Step 7
                    stage.show();
                } catch (IOException e) {
                    System.err.println(String.format("Error: %s", e.getMessage()));
                }
            }
        }
    }


    @FXML
    void initialize() throws MalformedURLException, URISyntaxException, InterruptedException {
        fadeTransition = new FadeTransition(Duration.millis(4000));
        fadeTransition.setNode(prim_imageView);
        fadeTransition2 = new FadeTransition(Duration.millis(4000));
        fadeTransition2.setNode(prim_imageView);
        currentPath="src/main/resources/org/kumsal/ficomsoft/image/image"+count+".jpg";
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
        double a=main_anchor_pane.prefWidthProperty().divide(2).subtract(prim_imageView.fitWidthProperty().divide(2).get()).get();
        prim_imageView.xProperty().bind(main_anchor_pane.prefWidthProperty().divide(2).subtract(prim_imageView.fitWidthProperty().divide(2)));
//        prim_imageView.setX(500);
    }
    public void changeImage(String path){
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
            if (count>3){
                count=1;
            }
            currentPath="src/main/resources/org/kumsal/ficomsoft/image/image"+count+".jpg";
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
