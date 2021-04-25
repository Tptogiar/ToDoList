package main.controller.listener;/**
 * @author Tptogiar
 * @creat 2021-04-10-0:02
 */


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import main.model.serve.InputNumberConverter;

/**
 * @author:Tptogiar
 * @Description: 新建item页中输入框 “循环间隔 ”’的监听器
 * @date: 2021/4/10 0:02
 *
 */
public class ListenerOfCriculIntervalTextFieldChange implements ChangeListener {

    private TextField criculInterval;
    private CheckBox isCriculBox;

    public ListenerOfCriculIntervalTextFieldChange(TextField textField,CheckBox isCriculBox){
        this.criculInterval=textField;
        this.isCriculBox=isCriculBox;
    }
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {

        if (! isCriculBox.isSelected()){
            criculInterval.clear();
            return;
        }
        if (! InputNumberConverter.isNumeric((String)newValue)){
            criculInterval.setText((String)oldValue);
            return;
        }

        //当发生改变之前，文本域本省没有值就不进行
        // 不然会传入一个空的文本进去,源码在调用setTextFormatter的时候
        // 会对文本进行一些操作，进而导致空指针
        if (((String) oldValue).length()!=0) {
            criculInterval.setTextFormatter(new TextFormatter<String>(new InputNumberConverter(99, 01)));
            criculInterval.commitValue();
        }

    }

}
