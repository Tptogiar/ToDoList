package main.controller;/**
 * @author Tptogiar
 * @creat 2021-03-26-12:34
 */

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.model.Item;
import main.model.ItemAboutTime;
import main.model.ItemCricul;
import main.model.ItemLabel;
import main.model.exception.IsAlreadyExistInItems;
import main.model.exception.NoSuchItemException;
import main.model.exception.NoSuchItemLableException;
import main.model.manager.ItemLabelsMgr;
import main.model.manager.ItemsMgr;
import main.model.serve.CopyAndLoadImage;
import main.model.serve.SaveAndLoadDate;
import main.model.serve.SortFactor;
import main.view.edititemwindow.EditItemWindow;
import main.view.settingwindow.SettingWindow;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.*;
import java.util.ArrayList;

/**
 * @author:Tptogiar
 * @Description: 此类中定义了各个界面中各个控件所绑定的事件
 * @date: 2021/3/26 12:34
 *
 */
public class EventList {

    public static ControlOfMainWindow controlOfMainWindow;
    public static ControlOfEditItemWindow newItemWindowControl;


    /**
     * @Author: Tptogiar
     * @Description: 获取其他界面的控制器以便实现交互
     * @Date: 2021/4/16-13:32
     */
    public static void setControl(ControlOfMainWindow controlOfMainWindow, ControlOfEditItemWindow newItemWindowControl) {
        EventList.controlOfMainWindow = controlOfMainWindow;
        EventList.newItemWindowControl =newItemWindowControl;
    }


    /**
     * @Author: Tptogiar
     * @Description: 首页ToDo按钮所对应的事件
     * @Date: 2021/3/26-12:44
     */
    public static void toDoPage() throws IOException {
        ItemsMgr.upCriculItemToFuture();
        controlOfMainWindow.upContent(ItemsMgr.getItems());
        controlOfMainWindow.showCricul.setSelected(false);
    }


    /**
     * @Author: Tptogiar
     * @Description: 首页new按钮所对应事件
     * @Date: 2021/3/26-12:43
     */
    public static void editNewItemStage() throws IOException {
        EditItemWindow.displayEditNewItemStage();
    }

    /**
     * @Author: Tptogiar
     * @Description: 首页setting按钮所对应的事件
     * @Date: 2021/3/26-12:48
     */
    public static void setting() throws IOException {
        SettingWindow.display();
    }


    /**
     * @Author: Tptogiar
     * @Description: 新建Item窗口中save按钮所对应事件
     * @Date: 2021/3/27-8:48
     */
    public static void saveNewItem(String title, String description, ItemLabel itemLabel, LocalDateTime deadline,ItemCricul itemCricul
                                ) throws NoSuchItemException, IsAlreadyExistInItems {

        //计算剩余时间
        LocalDateTime creatTime=LocalDateTime.now();
        Duration leftTime= Duration.between(creatTime,deadline);

        ItemAboutTime itemAboutTime = new ItemAboutTime(creatTime, deadline, leftTime);

        if (itemCricul.isCricul()){
            ItemsMgr.addCriculItem(title,itemLabel,description,itemAboutTime,itemCricul);
        }else{
            ItemsMgr.newItem(title, itemLabel, description, itemAboutTime, itemCricul);
        }


    }

    /**
     * @Author: Tptogiar
     * @Description: 因为普通的item和循环的item是分开储存的，所以这里也有别于上面的函数
     * @Date: 2021/4/24-14:55
     */
    public static void saveCriculItem(Item indeedItem) throws IOException {
        Item viceItem = copyItemToCricul(indeedItem);
        ItemsMgr.newItem(viceItem);
        //运行javafx程序时，会自动生成FX application线程，且不予许在别的线程中修改界面上组件的信息，
        //所以此处应调用Platform.runLater，翻看源码可以知道，此方法会将参数runnable传递给Fx application线程
        Platform.runLater(()->{
            try {
                EventList.controlOfMainWindow.upContent(ItemsMgr.getItems());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        SaveAndLoadDate.saveObject(ItemsMgr.getCriculItems(), ItemsMgr.criculItemFile);
    }

    /**
     * @Author: Tptogiar
     * @Description: 在主窗口点击排序后执行此函数以对item在内存层面进行排序后展现出来
     * @Date: 2021/4/16-13:31
     */
    public static void sort(SortFactor sortFactor) throws IOException {
        ItemsMgr.sortItemBy(sortFactor);
        EventList.controlOfMainWindow.upContent(ItemsMgr.getItems());
    }

    /**
     * @Author: Tptogiar
     * @Description: 在主窗口点击搜索后执行此函数以展示搜索后与搜索条件匹配的item
     * @Date: 2021/4/16-13:29
     */
    public static void search(String string) throws IOException {
        ArrayList<Item> searchResult = ItemsMgr.searchByStr(string);
        controlOfMainWindow.upContent(searchResult);
    }

    /**
     * @Author: Tptogiar
     * @Description: 在主窗口中点击某一个标签进行分类后执行此函数以分类展示item
     * @Date: 2021/4/16-13:28
     */
    public static void classify(ItemLabel newItemLabel) throws IOException {
        ArrayList<Item> classifyResult=ItemsMgr.classifyBy(newItemLabel);
        controlOfMainWindow.upContent(classifyResult);
        controlOfMainWindow.upClassifyBox();
    }

    /**
     * @Author: Tptogiar
     * @Description: 打开编辑item的窗口
     * @Date: 2021/4/16-13:26
     */
    public static void openCorrectItemStage(Item item,ControlOfItemBox controlOfItemBox) throws IOException {
        ControlOfEditItemWindow controlOfEditItemWindow = EditItemWindow.openCorrectItemStage(item);
        controlOfEditItemWindow.setInfo(item);
        controlOfEditItemWindow.setControlOfItemBox(controlOfItemBox);
    }

    /**
     * @Author: Tptogiar
     * @Description: 在编辑窗口中点击修改后执行此函数，来修改item的信息
     * @Date: 2021/4/16-13:27
     */
    public static void correctItem(ControlOfEditItemWindow controlOfEditItemWindow,Item item,String title,String description, ItemLabel itemLabel,
                                   LocalDateTime deadline,ItemCricul itemCricul)
                                    throws NoSuchItemException, MalformedURLException {

        if(itemCricul.isCricul() ||controlOfEditItemWindow.getItem().getItemCricul().isCricul()){
            ItemsMgr.correntCriculItem(item,title,description,deadline,itemLabel,itemCricul);
        }else {
            ItemsMgr.correctItem(item,title,description,deadline,itemLabel,itemCricul);
        }
//            controlOfEditItemWindow.getControlOfItemBox().correctInfo(item);

    }

    /**
     * @Author: Tptogiar
     * @Description: 把item标记为已完成
     * @Date: 2021/4/16-13:25
     */
    //TODO 该为只在当前显示的列表内更新（界面的跳转有待优化）
    public static void done(Item item) throws NoSuchItemException, IOException {
        if(item.isFinish()){
            return;
        }
        ItemsMgr.fromNowToPast(item);
        item.getAboutTime().setFinshTime(LocalDateTime.now());
        SaveAndLoadDate.saveObject(ItemsMgr.getItems(),ItemsMgr.itemDateFile);
        EventList.controlOfMainWindow.upContent(ItemsMgr.getItems());
    }

    /**
     * @Author: Tptogiar
     * @Description: 删除普通的item
     * @Date: 2021/4/16-13:25
     */
    public static void deleteItem(Item item) throws NoSuchItemException, IOException {
        ItemsMgr.removeItem(item);
        SaveAndLoadDate.saveObject(ItemsMgr.getItems(),ItemsMgr.itemDateFile);
        EventList.controlOfMainWindow.upContent(ItemsMgr.getItems());
    }

    /**
     * @Author: Tptogiar
     * @Description: 母体item和普通的item是分开储存的，所有删除自然也有所不同，这里是删除母体的函数
     * @Date: 2021/4/24-15:01
     */
    public static void deleteCriculItem(Item item) throws IOException {
        ItemsMgr.removeCriculItem(item);
        ItemsMgr.deleteScheduleFuture(item);
        SaveAndLoadDate.saveObject(ItemsMgr.getCriculItems(),ItemsMgr.criculItemFile);
        EventList.controlOfMainWindow.upContent(ItemsMgr.getCriculItems());

    }

    /**
     * @Author: Tptogiar
     * @Description: 选择传入的图片
     * @Date: 2021/4/16-13:24
     */
    public static void chosePhoto(Item item){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择插入的图片");
        fileChooser.setInitialDirectory(new File("C:" + File.separator));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            File imageFile = CopyAndLoadImage.saveImage(file);
            item.setImageFile(imageFile);
            SaveAndLoadDate.saveObject(ItemsMgr.getItems(),ItemsMgr.itemDateFile);
        }
    }

    /**
     * @Author: Tptogiar
     * @Description: 作为重复项用来产生重复的Item的母体item（母体）和普通的item是分开显示的
     * @Date: 2021/4/24-14:56
     */
    public static void showCricul() throws IOException {
        controlOfMainWindow.upContent(ItemsMgr.getCriculItems());
    }

    /**
     * @Author: Tptogiar
     * @Description: 重复的item需要由“作为重复项用来产生重复的Item的Item（母体）”copy出来的
     * @Date: 2021/4/24-14:57
     */
    public static Item copyItemToCricul(Item indeedItem){
        //重新计算创建时间，截止时间等
        //creatTime=indeedItem.creat+(CirculCount-CurrrentLeftCount)*duration
        int count=indeedItem.getItemCricul().getCirculCount() - indeedItem.getItemCricul().getCurrrentLeftCount();
        long duration = indeedItem.getAboutTime().getDuration().getSeconds();
        long criculPeriod = indeedItem.getItemCricul().getCriculPeriodOfIntervalUnit();
        LocalDateTime creatTime=indeedItem.getAboutTime().getCreatTime().plusSeconds((count)*criculPeriod);
        //因为在倒计时计算的时候并没有更改Item.itemAboutTime中的duration,
        //所以此处不需要更改产生的副本的duration，反而可以拿来用于计算下一个截止日期
        //deadline=creatTime+duration*(CirculCount-CurrrentLeftCount)
        LocalDateTime deadline = creatTime.plusSeconds(duration);


        ItemAboutTime itemAboutTime = new ItemAboutTime(creatTime, deadline, indeedItem.getAboutTime().getDuration());

        ItemCricul itemCricul = new ItemCricul(indeedItem.getItemCricul().isCricul(), indeedItem.getItemCricul().getCountOfIntervalUnits(),
                indeedItem.getItemCricul().getCriculUnitType(), indeedItem.getItemCricul().getCirculCount());
        Item viceItem = new Item(indeedItem.getTitle(), indeedItem.getLabel(), indeedItem.getDescription(),
                itemAboutTime,itemCricul);
        viceItem.setId(indeedItem.getId());
        viceItem.setCopy(true);
        //记录这个item是在本次重复的item里面的第几个，有小bug，所以暂时弃用了
//        viceItem.setOrderNumber(indeedItem.getItemCricul().getCurrrentLeftCount());

        return viceItem;
    }


    /**
     * @Author: Tptogiar
     * @Description: 删除标签
     * @Date: 2021/4/24-15:09
     */
    public static void deleteLabel(ItemLabel value) {
        try {
            ItemLabelsMgr.removeItemLable(value);

        } catch (NoSuchItemLableException e) {
            e.printStackTrace();
        }
    }
}