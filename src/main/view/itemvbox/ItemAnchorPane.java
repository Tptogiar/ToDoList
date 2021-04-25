package main.view.itemvbox;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.controller.ControlOfItemBox;

import java.io.IOException;

/**
 * @author Tptogiar
 * @Descripiton: 用于呈现item中的信心的AnchorPane
 * @creat 2021/04/14-21:20
 */


public class ItemAnchorPane {


    public static ControlOfItemBox getNewItemOfContorl() throws IOException {

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(ItemAnchorPane.class.getResource("itemBox.fxml"));
        loader.load();
        ControlOfItemBox itemVBoxContorll =(ControlOfItemBox) loader.getController();
        return itemVBoxContorll;
    }


}
