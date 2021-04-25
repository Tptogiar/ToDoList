package main.controller.listener;/**
 * @author Tptogiar
 * @creat 2021-04-10-0:23
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import main.controller.EventList;

import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * @author:Tptogiar
 * @Description: 首页搜索栏的监听器
 * @date: 2021/4/10 0:23
 *
 */
public class ListenerOfSearchFieldChange implements ChangeListener {

    public TextField searchField;

    public ListenerOfSearchFieldChange(TextField textField){
        this.searchField=textField;
    }


    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        try {
            EventList.search((String) newValue);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
