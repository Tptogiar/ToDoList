package main.controller.listener;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import main.controller.EventList;
import main.model.ItemLabel;
import java.io.IOException;

/**
 * @author Tptogiar
 * @Descripiton: 标签分类的监听器
 * @creat 2021/04/15-23:13
 */


public class ClassifyBoxListencer implements ChangeListener {

    private ChoiceBox classifyBox;

    public ClassifyBoxListencer(ChoiceBox classifyBox) {
        this.classifyBox = classifyBox;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        try {
            //当newValue等于-1时表示choice的值处于null的状态，但是此时数组classifyBox.getItems()的
            //下面取-1的话会保错，因此把-1排除掉
            if ((int)newValue!=-1){
                EventList.classify((ItemLabel) classifyBox.getItems().get((int)newValue));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
