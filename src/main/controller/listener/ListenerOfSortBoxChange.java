package main.controller.listener;/**
 * @author Tptogiar
 * @creat 2021-04-10-0:19
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import main.controller.EventList;
import main.model.serve.SortFactor;

import java.io.IOException;

/**
 * @author:Tptogiar
 * @Description: 首页排序按钮的监听器
 * @date: 2021/4/10 0:19
 *
 */
public class ListenerOfSortBoxChange implements ChangeListener {

    public ChoiceBox sortBox;

    public ListenerOfSortBoxChange(ChoiceBox choiceBox){
        this.sortBox=choiceBox;
    }



    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        try {
            EventList.sort((SortFactor) sortBox.getItems().get((int)newValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
