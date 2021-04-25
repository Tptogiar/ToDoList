package main.controller;/**
 * @author Tptogiar
 * @creat 2021-03-25-7:48
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.controller.listener.ClassifyBoxListencer;
import main.controller.listener.ListenerOfSearchFieldChange;
import main.controller.listener.ListenerOfSortBoxChange;
import main.controller.listener.ShowCriculBoxListener;
import main.model.Item;
import main.model.manager.ItemLabelsMgr;
import main.model.manager.ItemsMgr;
import main.model.serve.SortFactor;
import main.view.itemvbox.ItemAnchorPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * @author:Tptogiar
 * @Description: 界面的根节点
 * @date: 2021/3/25 7:48
 *
 */
public class ControlOfMainWindow implements Initializable {



    @FXML
    public Button newItem;
    public Button toDoPage;
    public Button resetBtn;
    public Button setting;

    public AnchorPane mainRoot;
    public AnchorPane topBar;

    public CheckBox showCricul;

    public ChoiceBox sortBox;
    public ChoiceBox classifyBox;

    public TextField searchField;

    public VBox content;

    //由于加载完Item后还需要不断修改剩余时间，所以将每次加载后itembox的地址存起来，方便后边调用修改
    private ArrayList<ControlOfItemBox> itemboxControlOfItemBoxes =new ArrayList<>();


    public ArrayList<ControlOfItemBox> getItemboxControlOfItemBoxes(){
        return itemboxControlOfItemBoxes;
    }


    /**
     * @Author: Tptogiar
     * @Description: 初始化界面
     * @Date: 2021/4/9-20:52
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //加载ToDo.data在ItemsManager这个类加载的时候就加载到内存了，
        // 此处调用upContent来更新content中的内容，完成启动初始化
        try {
            upContent(ItemsMgr.getItems());
        } catch (IOException e) {
            e.printStackTrace();
        }

        upSortBox();
        upClassifyBox();


        //设置监听器
        sortBox.getSelectionModel().selectedIndexProperty().addListener(new ListenerOfSortBoxChange(sortBox));
        searchField.textProperty().addListener(new ListenerOfSearchFieldChange(searchField));
        classifyBox.getSelectionModel().selectedIndexProperty().addListener(new ClassifyBoxListencer(classifyBox));
        showCricul.selectedProperty().addListener(new ShowCriculBoxListener(showCricul));
    }

    public void handleButtonClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == toDoPage) {
            EventList.toDoPage();
        } else if (actionEvent.getSource() == newItem) {
            EventList.editNewItemStage();
        } else if (actionEvent.getSource() == setting) {
            EventList.setting();
        } else if(actionEvent.getSource()== resetBtn){
            searchField.clear();
        }
    }


    /**
     * @Author: Tptogiar
     * @Description: 更剧传进来的内容，更新content中的内容
     * @Date: 2021/4/24-14:49
     */
    public  void  upContent(ArrayList<Item> items) throws IOException {
        content.getChildren().clear();
        itemboxControlOfItemBoxes.clear();

        for (int i=0;i<items.size();i++){
            ControlOfItemBox newItemOfControl = ItemAnchorPane.getNewItemOfContorl();
            newItemOfControl.setInfo(items.get(i));
            content.getChildren().add(newItemOfControl.getItemRoot());
//            content.getChildren().add(newItemAchorPane);
            //将地址值存起来
            itemboxControlOfItemBoxes.add(newItemOfControl);
        }
    }



    /**
     * @Author: Tptogiar
     * @Description: 更新分类按钮
     * @Date: 2021/4/16-0:05
     */
    public  void upClassifyBox(){
        classifyBox.getItems().clear();
        for (int i = 0; i < ItemLabelsMgr.getItemLabels().size(); i++) {
            classifyBox.getItems().add(ItemLabelsMgr.getItemLabels().get(i));
        }
        classifyBox.setValue(null);
    }


    /**
     * @Author: Tptogiar
     * @Description: 更新排序按钮
     * @Date: 2021/4/16-0:05
     */
    public void upSortBox(){
        sortBox.getItems().clear();
        for (int i = 0; i < SortFactor.sortFactors.size(); i++) {
            sortBox.getItems().add(SortFactor.sortFactors.get(i));
        }
        sortBox.setValue(sortBox.getItems().get(0));
    }

}

