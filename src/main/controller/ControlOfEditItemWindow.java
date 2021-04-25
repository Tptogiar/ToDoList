package main.controller;/**
 * @author Tptogiar
 * @creat 2021-03-26-20:58
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import main.controller.listener.ListenerOfCriculCountTextFieldChange;
import main.controller.listener.ListenerOfCriculIntervalTextFieldChange;
import main.controller.listener.ListenerOfExactHourTextFieldChange;
import main.controller.listener.ListenerOfExactMinuteTextFieldChange;
import main.model.Item;
import main.model.ItemCricul;
import main.model.ItemLabel;
import main.model.exception.IsAlreadyExistInItems;
import main.model.exception.NoSuchItemException;
import main.model.exception.NoSuchItemLableException;
import main.model.manager.ItemLabelsMgr;
import main.model.manager.ItemsMgr;
import main.model.serve.CriculUnit;
import main.model.serve.SaveAndLoadDate;
import main.view.edititemwindow.EditItemWindow;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;


/**
 * @author:Tptogiar
 * @Description:
 * @date: 2021/3/26 20:58
 *
 */
public class ControlOfEditItemWindow implements Initializable {

    @FXML
    public AnchorPane EditItemWindowRoot;
    public HBox criculHbox;

    public TextField titleText;
    public TextField criculIntervalCountField;
    public TextField criculCountField;
    public TextField exactHourTextField;
    public TextField exactMinuteTextField;
    public TextField newLabelField;

    public TextArea descriptionText;
    public Label criculLeftCountTip;
    public Label deadlineLabel;

    public Button saveNewItem;
    public Button newLable;
    public Button deleteLabel;

    public DatePicker deadlineSelect;

    public CheckBox isCriculBox;

    public ChoiceBox<ItemLabel> itemLabelSelect;
    public ChoiceBox criculUnitChoiceBox;

    private Item item;
    private ControlOfItemBox controlOfItemBox;
    private static ControlOfMainWindow mainWindowContorll;

    public Item getItem() {
        return item;
    }

    public void setOtherContorll(ControlOfMainWindow mainWindowContorll){
        ControlOfEditItemWindow.mainWindowContorll =mainWindowContorll;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        deadlineSelect.setValue(LocalDate.now());
        for (int i = 0; i < ItemLabelsMgr.getItemLabels().size(); i++) {
            itemLabelSelect.getItems().add(ItemLabelsMgr.getItemLabels().get(i));
        }
        itemLabelSelect.setValue(itemLabelSelect.getItems().get(0));

        //TODO 改为自定义的枚举类
        for (int i = 0; i < CriculUnit.criculUnits.size(); i++) {
            criculUnitChoiceBox.getItems().add(CriculUnit.criculUnits.get(i));
        }
        criculUnitChoiceBox.setValue(criculUnitChoiceBox.getItems().get(0));


        //设置监听器,全部写成匿名类会显得代码有点多，所以把监听器写在listener
        exactHourTextField.textProperty().addListener(new ListenerOfExactHourTextFieldChange(exactHourTextField));
        exactMinuteTextField.textProperty().addListener(new ListenerOfExactMinuteTextFieldChange(exactMinuteTextField));
        criculCountField.textProperty().addListener(new ListenerOfCriculCountTextFieldChange(criculCountField,isCriculBox));
        criculIntervalCountField.textProperty().addListener(new ListenerOfCriculIntervalTextFieldChange(criculIntervalCountField,isCriculBox));
    }


    public void handleButtonClicks(ActionEvent actionEvent) throws IOException, NoSuchItemException, IsAlreadyExistInItems, InterruptedException, NoSuchItemLableException {
        if (actionEvent.getSource()==saveNewItem) {
            if(! isCanSave()){
                return;
            }

            String title=titleText.getText();
            String description=descriptionText.getText();
            ItemLabel itemLabel = itemLabelSelect.getValue();


            //如果此时是进行的修改操作，controlOfItemBox是赋过值的，不会是null
            if(controlOfItemBox!=null){
                EventList.correctItem(this,item,title,description,itemLabel,getDeadline(),getItemCricul());
            }else{
                EventList.saveNewItem(title,description,itemLabel,getDeadline(),getItemCricul());
            }

            mainWindowContorll.upContent(ItemsMgr.getItems());
            EditItemWindow.closeEditItemStage();
        }
        else if(actionEvent.getSource()==newLable){
            if(newLabelField.getText().isEmpty()){
                newLabelField.setStyle("-fx-border-color: red");
            }else{
                newLabelField.setStyle("-fx-border-color: inherit");
                String newLabelFieldText = newLabelField.getText();
                ItemLabelsMgr.addItemLabel(newLabelFieldText);
                upItemLabels(ItemLabelsMgr.getItemLabels());
                SaveAndLoadDate.saveObject(ItemLabelsMgr.getItemLabels(),ItemLabelsMgr.itemLabelDateFile);
                //使新建完之后choice的值立马跳到这个刚新建的值这里
                itemLabelSelect.setValue(itemLabelSelect.getItems().get(itemLabelSelect.getItems().size()-1));
                EventList.controlOfMainWindow.upClassifyBox();
            }

        }
        else if(actionEvent.getSource()==deleteLabel){
            EventList.deleteLabel(itemLabelSelect.getValue());
            upItemLabels(ItemLabelsMgr.getItemLabels());
        }
    }



    //将判断是否到达可以保存的的标准的逻辑抽出来
    private boolean isCanSave(){

        //判断标题是否为空
        if (titleText.getText().isEmpty()){
            titleText.setStyle("-fx-border-color: red");
            return false;
        }else {
            titleText.setStyle("-fx-border-color: inherit");
            //如果截止时间中精确到小时分钟的那两个空都没有写，就默认取当前系统时间的下一分钟
            if(exactHourTextField.getText().isEmpty()){
                exactHourTextField.setText(String.valueOf(LocalDateTime.now().getHour()));
            }
            if(exactMinuteTextField.getText().isEmpty()){
                exactMinuteTextField.setText(String.valueOf(LocalDateTime.now().getMinute()+1));
            }

            if (isCriculBox.isSelected()){
                if(criculIntervalCountField.getText().isEmpty()){
                    criculIntervalCountField.setStyle("-fx-border-color: red");
                    return false;
                }else{
                    criculIntervalCountField.setStyle("-fx-border-color: inherit");
                }
                if (criculCountField.getText().isEmpty()){
                    criculCountField.setStyle("-fx-border-color: red");
                    return false;
                }else{
                    criculCountField.setStyle("-fx-border-color: inherit");
                }
            }

            LocalDateTime deadline = getDeadline();
            LocalDateTime now = LocalDateTime.now();

            //如果该截止时间再当前系统时间之前，这不合法，不允新建
            if ((deadline.isBefore(now) && controlOfItemBox==null)){
                deadlineLabel.setTextFill(Paint.valueOf("red"));
                return false;
            }
            //如果此时进行的是编辑原有的item，则新的截止时间不能再创建时间之前，否则不予修改
            else if(controlOfItemBox!=null && controlOfItemBox.creatTime.isAfter(deadline)){
                deadlineLabel.setTextFill(Paint.valueOf("red"));
                return false;
            }
            else{
                deadlineLabel.setTextFill(Paint.valueOf("black"));
            }
        }
        return true;
    }

    //将属于itemCricul的信息整合成一个itemCricul后返回出来
    private ItemCricul getItemCricul(){

        int criculCountOfIntervalUnit=0;
        int criculCount=0;

        boolean isCricul=isCriculBox.isSelected();
        CriculUnit criculUnit = (CriculUnit)criculUnitChoiceBox.getValue();

        if(isCricul){
        criculCountOfIntervalUnit=Integer.parseInt(criculIntervalCountField.getText());
        criculCount=Integer.parseInt(criculCountField.getText());
        }else{
            criculCountOfIntervalUnit=0;
            criculCount=0;
        }
        return new ItemCricul(isCricul,criculCountOfIntervalUnit,criculUnit,criculCount);
    }



    /**
     * @Author: Tptogiar
     * @Description: 本来这段代码是现在save按钮绑定的点击事件下的，后面有别的地方也要用到所以
     * 单拎出来的，用于将日期选择器和后面连个精确的空整合起来，转化为LoaclDateTime类型
     * @Date: 2021/4/14-23:26
     */
    public LocalDateTime getDeadline(){
        //获取目前正要新建的item的截止时间
        LocalDate deadline_localDate = deadlineSelect.getValue();
        //将LocalDate类型转变为LocalDateTime类型
        LocalDateTime deadline = LocalDateTime.ofInstant(deadline_localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        deadline= deadline.plusHours(Integer.valueOf(exactHourTextField.getText()));
        deadline= deadline.plusMinutes(Integer.valueOf(exactMinuteTextField.getText()));
        return deadline;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setControlOfItemBox(ControlOfItemBox controlOfItemBox) {
        this.controlOfItemBox = controlOfItemBox;
    }

    public ControlOfItemBox getControlOfItemBox() {
        return controlOfItemBox;
    }

    public void setInfo(Item item){
        titleText.setText(item.getTitle());
        descriptionText.setText(item.getDescription());
        deadlineSelect.setChronology(item.getAboutTime().getDeadlineTime().getChronology());
        exactHourTextField.setText(String.valueOf(item.getAboutTime().getDeadlineTime().getHour()));
        exactMinuteTextField.setText(String.valueOf(item.getAboutTime().getDeadlineTime().getMinute()));
        itemLabelSelect.setValue(item.getLabel());


        //item 有三类，一类是作为重复项用来产生重复的Item的Item（暂且称之为母体吧），第二类是被重复项产生出来的重复的Item，第三类是普通的，不重复的item
        if(item.isCopy()==false && item.getItemCricul().isCricul()==true){
            isCriculBox.setVisible(false);
            criculLeftCountTip.setText("重复一次，目前剩余");
            criculCountField.setText(String.valueOf(item.getItemCricul().getCurrrentLeftCount()));

        }
        if(item.isCopy()==true){
            isCriculBox.setVisible(false);


            /**
             * @Author: Tptogiar
             * @Description: 这里有点小bug，想实现显示是第几个的，但是数值设置的有点问题，还没改
             * @Date: 2021/4/24-14:51
             */
            //TODO 使重复的item点击修改后的界面显示当前这个item是在本次重复的item里面的第几个
//            criculLeftCountTip.setText("重复一次，当前为第");
//            criculCountField.setText(String.valueOf(item.getOrderNumber()));
        }
        if(item.isCopy()==false && item.getItemCricul().isCricul()==false){
            
        }

        isCriculBox.setSelected(item.getItemCricul().isCricul());
        if (isCriculBox.isSelected()){
            criculIntervalCountField.setText(String.valueOf(item.getItemCricul().getCountOfIntervalUnits()));
            criculCountField.setText(String.valueOf(item.getItemCricul().getCurrrentLeftCount()));
        }


    }

    public  void upItemLabels(ArrayList<ItemLabel> itemLabels){
        itemLabelSelect.getItems().clear();
        for (int i = 0; i < itemLabels.size(); i++) {
            itemLabelSelect.getItems().add(itemLabels.get(i));
        }

    }



}
