package main.view.quitwindow;/**
 * @author Tptogiar
 * @creat 2021-03-30-16:53
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author:Tptogiar
 * @Description: 程序退出时的提醒
 * @date: 2021/3/30 16:53
 *
 */
/**
 * @Author: Tptogiar
 * @Description: 本来想自己实现关闭的确认操作的，算了没时间了，调用javaFX的对话框得了,所以下面的类，以及这个包都没有用到了，
 *               后面有时间在来写，所以这个包先保留着吧
 * @Date: 2021/3/30-18:10
 */
public class ConfirmQuitWindow {


    public static Stage quitWindow=new Stage();

    public static void dispaly() throws IOException {

        quitWindow.setResizable(false);
        quitWindow.setTitle("确认退出");
        quitWindow.setWidth(190);
        quitWindow.setHeight(130);
        AnchorPane anchorPane=(AnchorPane) FXMLLoader.load(ConfirmQuitWindow.class.getResource("confirmQuitWindow.fxml"));

        Scene scene = new Scene(anchorPane);
        quitWindow.setScene(scene);
        quitWindow.show();

    }
}
