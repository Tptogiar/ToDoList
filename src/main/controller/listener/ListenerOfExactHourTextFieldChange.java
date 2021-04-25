package main.controller.listener;/**
 * @author Tptogiar
 * @creat 2021-04-09-23:29
 */

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import main.model.serve.InputNumberConverter;

/**
 * @author:Tptogiar
 * @Description: 新建item页的 “ 时 ” 输入框的监听器
 * @date: 2021/4/9 23:29
 *
 */
public class ListenerOfExactHourTextFieldChange implements ChangeListener {

    public TextField exactHourTextField;

    public ListenerOfExactHourTextFieldChange(TextField textField){
        this.exactHourTextField=textField;
    }


    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {

        if (! InputNumberConverter.isNumeric((String)newValue)){
            exactHourTextField.setText((String)oldValue);
            return;
        }


        //当发生改变之前，文本域本省没有值就不进行
        // 不然会传入一个空的文本进去,源码在调用setTextFormatter的时候
        // 会对文本进行一些操作，进而导致空指针
        if (((String) oldValue).length()!=0){
            exactHourTextField.setTextFormatter(new TextFormatter<String>(new InputNumberConverter(23,00)));
            exactHourTextField.commitValue();
        }
    }
}
