package main.controller.listener;/**
 * @author Tptogiar
 * @creat 2021-04-09-23:36
 */


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import main.model.serve.InputNumberConverter;


/**
 * @author:Tptogiar
 * @Description: 新建item页的 “ 分钟 ” 输入框的监听器
 * @date: 2021/4/9 23:36
 *
 */
public class ListenerOfExactMinuteTextFieldChange implements ChangeListener {

    public TextField exactMinuteTextField;

    public ListenerOfExactMinuteTextFieldChange(TextField textField){
        this.exactMinuteTextField=textField;
    }



    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {


        if (! InputNumberConverter.isNumeric((String)newValue)){
            exactMinuteTextField.setText((String)oldValue);
            return;
        }

        //当发生改变之前，文本域本省没有值就不进行
        // 不然会传入一个空的文本进去,源码在调用setTextFormatter的时候
        // 会对文本进行一些操作，进而导致空指针
        if (((String) oldValue).length()!=0){
            exactMinuteTextField.setTextFormatter(new TextFormatter<String>(new InputNumberConverter(59,00)));
            exactMinuteTextField.commitValue();
        }
    }
}
