package org.kumsal.ficomSoft;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import com.mysql.cj.MysqlConnection;
import com.mysql.cj.MysqlxSession;
import com.mysql.cj.Query;
import com.mysql.cj.Session;
import com.mysql.cj.conf.ConnectionUrl;
import com.mysql.cj.jdbc.ConnectionGroupManager;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.protocol.x.XProtocol;
import com.mysql.jdbc.Driver;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import oracle.jdbc.OracleDriver;
import org.kairos.layouts.RecyclerView;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;
import org.w3c.dom.Text;

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

        ArrayList<String> adminUsName=new ArrayList<>();
        ArrayList<String> userUsName=new ArrayList<>();

        ArrayList<String> adminPasse=new ArrayList<>();
        ArrayList<String> userPasse=new ArrayList<>();

        Statement forAdmin = dbSource.getConnection().createStatement();
        Statement forusers = dbSource.getConnection().createStatement();

        forusers.execute(query2);
        forAdmin.execute(query);

        resultSet=forAdmin.getResultSet();
        while (resultSet.next()){
            adminUsName.add(resultSet.getString("username"));
            adminPasse.add(resultSet.getString("password"));
        }

        resultSet=forusers.getResultSet();
        while (resultSet.next()){
            userUsName.add(resultSet.getString("username"));
            userPasse.add(resultSet.getString("password"));
        }
        String theUsername=login_username.getText();
        String thePassword=login_password.getText();
        String loginBy="";
        if ((userUsName.contains(theUsername) && userPasse.contains(thePassword))) {
            loginBy="User";

        }else if ((adminUsName.contains(theUsername) && adminPasse.contains(thePassword)){
            loginBy="User";
        }


        // Step 2

    }
    void login(ActionEvent event){
        Node node = (Node) event.getSource();
        // Step 3
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        try {
            // Step 4
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SceneA.fxml"));
            // Step 5
            stage.setUserData();
            // Step 6
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // Step 7
            stage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
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
