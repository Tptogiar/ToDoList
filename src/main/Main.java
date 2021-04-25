package main; /**
 * @author Tptogiar
 * @creat 2021-03-24-21:55
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.controller.EventList;
import main.controller.ControlOfMainWindow;
import main.controller.ControlOfEditItemWindow;
import main.model.otherthread.CountDownThread;

import java.util.Optional;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane root = new StackPane();
        root.setPrefSize(700,700);
        primaryStage.setResizable(false);

        /**
         * @Author: Tptogiar
         * @Description: 加载各个界面，以及他们的控制器
         * @Date: 2021/3/27-22:48
         */
        FXMLLoader mainWindowLoader = new FXMLLoader();
        FXMLLoader newItemWindowLoader = new FXMLLoader();
        mainWindowLoader.setLocation(getClass().getResource("view/mainwindow/mainWindow.fxml"));
        newItemWindowLoader.setLocation(getClass().getResource("view/edititemwindow/EditItemWindow.fxml"));

        AnchorPane mainWindowroot= mainWindowLoader.load();
        AnchorPane newItemwindowroot=newItemWindowLoader.load();

        ControlOfMainWindow controllerOfMainWindow = mainWindowLoader.getController();
        ControlOfEditItemWindow newItemWindowController = newItemWindowLoader.getController();



        /**
         * @Author: Tptogiar
         * @Description: 将mianWindow的控制器共享给newItemWindowContorll,countDownThread线程
         * @Date: 2021/3/27-22:46
         */
        newItemWindowController.setOtherContorll(controllerOfMainWindow);
        CountDownThread.mainWindowContorll=controllerOfMainWindow;
        EventList.setControl(controllerOfMainWindow,newItemWindowController);


        root.getChildren().add(0,mainWindowroot);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        /**
         * @Author: Tptogiar
         * @Description: 本来想自己实现关闭的确认操作的，算了没时间了，
         * 调用javaFX的对话框得了
         * @Date: 2021/3/30-18:06
         */
//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                //退出时关闭子线程
//                countDownThread.stop();
//
//                event.consume();
//                try {
//                    ConfirmQuitWindow.dispaly();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        //添加监听
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("退出？");
                confirm.setHeaderText("确认退出？");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.get() == ButtonType.OK){
                    //退出时关闭子线程
                    CountDownThread.scheduledExecutorService.shutdown();
                    primaryStage.close();
                } else {
                    event.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}