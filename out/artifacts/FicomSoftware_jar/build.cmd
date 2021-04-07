echo "derleme islemine baslandi"
java --module-path "javafx-sdk-11.0.2\lib" --add-modules=javafx.fxml,javafx.controls -DpathConf="\config\config.properties" -Dfiles="\files"  -jar FicomSoftware.jar
