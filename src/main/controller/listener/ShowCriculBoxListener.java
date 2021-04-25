package main.controller.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import main.controller.EventList;

import java.io.IOException;

/**
 * @author Tptogiar
 * @Descripiton: 首页筛选循环按钮的监听器
 * @creat 2021/04/21-10:23
 */


public class ShowCriculBoxListener implements ChangeListener {

    private CheckBox selectCriculCheckBox;

    public ShowCriculBoxListener(CheckBox selectCriculCheckBox) {
        this.selectCriculCheckBox = selectCriculCheckBox;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {

        boolean current=(boolean)newValue;
        if(current){
            try {
                EventList.showCricul();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                EventList.toDoPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }
}
