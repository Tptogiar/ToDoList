package main.controller;/**
 * @author Tptogiar
 * @creat 2021-03-30-18:15
 */

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import main.model.manager.ItemsMgr;
import main.model.serve.SaveAndLoadDate;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author:Tptogiar
 * @Description: 设置界面的控制器
 * @date: 2021/3/30 18:15
 *
 */
public class ControlOfSettingWindow implements Initializable {


    public Button setDateDirectory;
    public Label path;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        path.setText(ItemsMgr.fileDir.getAbsolutePath());
    }


    public void handleButtonClicks(ActionEvent actionEvent){

        if (actionEvent.getSource()==setDateDirectory){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("请选择一个保存数据的文件夹");
            directoryChooser.setInitialDirectory(new File("C:" + File.separator));
            File file = directoryChooser.showDialog(new Stage());
            if(file != null){
                ItemsMgr.fileDir =file;
                SaveAndLoadDate.saveObject(ItemsMgr.getItems(),ItemsMgr.itemDateFile);
                updateDirectory();
            }
        }
    }

    public void updateDirectory(){
        path.setText(ItemsMgr.fileDir.getAbsolutePath());
    }


}
