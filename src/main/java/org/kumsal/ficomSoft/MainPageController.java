package org.kumsal.ficomSoft;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.kairos.core.Activity;
import org.kairos.core.Fragment;
import org.kairos.core.FragmentManager;
import org.kairos.core.FragmentTransaction;
import org.kairos.layouts.PagerAdapter;
import org.kairos.layouts.ViewPager;
import org.kumsal.ficomSoft.fragments.fragmentHome;

import java.net.URL;
import java.util.ResourceBundle;
public class MainPageController extends Activity {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button main_page_home;

    @FXML
    private Button main_page_load;

    @FXML
    private Button main_page_current;

    @FXML
    private Button main_page_folders;

    @FXML
    private Button main_page_print;

    @FXML
    private Button main_page_destroy;


    @FXML
    private ViewPager page_viewer;

    @FXML
    void main_page_currentFiles(ActionEvent event) {

    }

    @FXML
    void onDestroy(ActionEvent event) {

    }

    @FXML
    void onLoad(ActionEvent event) {

    }

    @FXML
    void onPrint(ActionEvent event) {

    }

    @FXML
    void showFolder(ActionEvent event) {

    }

    @FXML
    void initialize() {

        FragmentManager fragmentManager=new FragmentManager() {
            @Override
            public FragmentTransaction beginTransaction() {
                return null;
            }

            @Override
            public Fragment findFragmentByTag(String s) {
                return new fragmentHome();
            }
        };
        PagerAdapter adp=new org.kumsal.ficomSoft.AdapterModelClass.PagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return null;
            }
        };
        page_viewer.setAdapter(adp);

    }

}
