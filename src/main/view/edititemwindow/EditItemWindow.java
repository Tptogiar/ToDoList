package main.view.edititemwindow;/**
 * @author Tptogiar
 * @creat 2021-03-26-12:57
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.controller.ControlOfEditItemWindow;
import main.model.Item;

import java.io.IOException;

/**
 * @author:Tptogiar
 * @Description: 新建ToDo项是弹出来的窗口
 * @date: 2021/3/26 12:57
 *
 */
public class EditItemWindow {

    //设置成为静态的才不会由于多次点击而产生多个
    public static Stage editItemStage = new Stage();
    static {
        editItemStage.setResizable(false);
    }

    /**
     * @Author: Tptogiar
     * @Description: 展示窗口并肩控制器返回
     * @Date: 2021/4/24-15:40
     */
    public static ControlOfEditItemWindow displayEditNewItemStage() throws IOException {

        editItemStage.setTitle("新建ToDo项");
        editItemStage.setWidth(450);
        editItemStage.setHeight(400);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(EditItemWindow.class.getResource("EditItemWindow.fxml"));
        AnchorPane anchorPane=(AnchorPane) loader.load();
        ControlOfEditItemWindow controller = loader.getController();

        Scene scene = new Scene(anchorPane);
        editItemStage.setScene(scene);
        editItemStage.show();
        return controller;
    }


    public static void closeEditItemStage(){
        editItemStage.close();
    }

    /**
     * @Author: Tptogiar
     * @Description: 打开修改窗口，修改是需要拿到原先的item并展示出来，所以有别于上面的dispaly函数
     * @Date: 2021/4/24-15:42
     */
    public static ControlOfEditItemWindow openCorrectItemStage(Item item) throws IOException {
        editItemStage.setTitle("编辑ToDo项");
        editItemStage.setWidth(500);
        editItemStage.setHeight(500);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(EditItemWindow.class.getResource("EditItemWindow.fxml"));
        AnchorPane anchorPane=(AnchorPane) loader.load();
        ((Button)anchorPane.getChildren().get(3)).setText("saveCorrect");
        ControlOfEditItemWindow controller = loader.getController();
        controller.setItem(item);
        Scene scene = new Scene(anchorPane);
        editItemStage.setScene(scene);
        editItemStage.show();
        return controller;



    }





}
