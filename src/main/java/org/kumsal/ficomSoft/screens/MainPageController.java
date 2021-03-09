package org.kumsal.ficomSoft.screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.kairos.core.Activity;
import org.kairos.core.Fragment;
import org.kairos.core.FragmentManager;
import org.kairos.core.FragmentTransaction;
import org.kairos.layouts.ViewPager;

import java.net.URL;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.kumsal.ficomSoft.AdapterModelClass.PagerAdapter;
import org.kumsal.ficomSoft.fragments.fragmentHome;

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
//            PagerAdapter denem=new PagerAdapter(getFragmentManager()) {
//                @Override
//                public Fragment getItem(int position) {
//                    return null;
//                }
//            };
//
//            page_viewer.setAdapter(denem);
        }

}
