package main.view.settingwindow;/**
 * @author Tptogiar
 * @creat 2021-03-30-18:17
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author:Tptogiar
 * @Description: 设置界面
 * @date: 2021/3/30 18:17
 *
 */
public class SettingWindow {

    public static Stage settingWindow=new Stage();
    static {
        settingWindow.setResizable(false);
    }

    public static void display() throws IOException {

        settingWindow.setTitle("Setting");
        settingWindow.setWidth(500);
        settingWindow.setHeight(300);
        AnchorPane anchorPane=(AnchorPane) FXMLLoader.load(SettingWindow.class.getResource("settingWindow.fxml"));

        Scene scene = new Scene(anchorPane);
        settingWindow.setScene(scene);
        settingWindow.show();


    }







}
