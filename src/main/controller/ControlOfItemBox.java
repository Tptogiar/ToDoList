package main.controller;/**
 * @author Tptogiar
 * @creat 2021-03-27-17:22
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.model.Item;
import main.model.exception.NoSuchItemException;
import main.model.manager.ItemsMgr;
import main.model.serve.CopyAndLoadImage;
import main.model.serve.FormatServe;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * @author:Tptogiar
 * @Description:
 * @date: 2021/3/27 17:22
 *
 */
public class ControlOfItemBox implements Initializable {


    public LocalDateTime deadline;
    public LocalDateTime creatTime;
    public LocalDateTime finshTime =null;

    @FXML
    public AnchorPane itemRoot;

    public Label itemTitle;
    public Label itemDescirption;

    public ImageView isCriculIco;
    public ImageView isFinshIco;
    public ImageView isPastDeadlineIco;

    public Button doneBtn;
    public Button creatIco;
    public Button deadlineIco;
    public Button deleteBtn;
    public Button labelType;
    public Button itemImage;
    public Button edit;

    public ProgressBar progerssBar;

    public Tooltip imageTooltip =new Tooltip();
    public Tooltip creatTimeTooltip =new Tooltip();
    public Tooltip deadlineTooltip =new Tooltip();
    public Tooltip titleTooltip=new Tooltip();
    public Tooltip descriptionTooltip=new Tooltip();

    private String leftTimeLabelText;
    private String creatTimeLabelText;
    private String deadlineLabelText;

    private Item item;

    public AnchorPane getItemRoot() {
        return itemRoot;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        isCriculIco.setImage(new Image("File:src\\resource\\images\\repeat.png"));
        isCriculIco.setVisible(false);


        isPastDeadlineIco.setImage(new Image("File:src\\resource\\images\\missed.png"));
        isPastDeadlineIco.setVisible(false);

        isFinshIco.setImage(new Image("File:src\\resource\\images\\confirm.png"));
        isFinshIco.setVisible(false);

        setTooltipDelay(imageTooltip,100,10000,200);
        setTooltipDelay(creatTimeTooltip,100,10000,200);
        setTooltipDelay(deadlineTooltip,100,10000,200);
        Tooltip.install(itemImage, imageTooltip);
        Tooltip.install(creatIco,creatTimeTooltip);
        Tooltip.install(deadlineIco,deadlineTooltip);
        Tooltip.install(itemTitle,titleTooltip);
        Tooltip.install(itemDescirption,descriptionTooltip);



    }

    /**
     * @Author: Tptogiar
     * @Description: 用于给每个从模板itemVBox.fxml加载得来的vBoxLayout初始化
     * @Date: 2021/3/27-20:16
     */
    public void setInfo(Item item) throws MalformedURLException {

        this.item=item;

        itemTitle.setText(item.getTitle());
        titleTooltip.setText(item.getTitle());
        itemDescirption.setText(item.getDescription());
        descriptionTooltip.setText(item.getDescription());


        deadline=item.getAboutTime().getDeadlineTime();
        creatTime = item.getAboutTime().getCreatTime();
        finshTime =item.getAboutTime().getFinshTime();


        creatTimeLabelText = FormatServe.formatLocalDateTime(creatTime);
        deadlineLabelText = FormatServe.formatLocalDateTime(deadline);

        if(item.getLabel()!=null){
            if (item.getLabel().getItemLabelType()==null){
                labelType.setVisible(false);
            }else{
                labelType.setText(item.getLabel().getItemLabelType());
            }
        }


        if (item.isFinish()){
            leftTimeLabelText="完成于："+ FormatServe.formatLocalDateTime(item.getAboutTime().getFinshTime());
        } else {
            if (!item.isPastDeadline()){
                leftTimeLabelText="剩余："+ FormatServe.forDuration(item.getAboutTime().getDuration());
            }else{
                leftTimeLabelText="已超时";
                progerssBar.setProgress(0);
            }
        }



        creatTimeTooltip.setText("创建于："+creatTimeLabelText);
        deadlineTooltip.setText("截止日期："+deadlineLabelText+"\n"+leftTimeLabelText);


        //item 有三类，一类是作为重复项用来产生重复的Item的（暂且称之为母体吧），第二类是被重复项产生出来的重复的Item，第三类是普通的，不重复的item
        boolean itemType1=item.isCopy()==false && item.getItemCricul().isCricul()==true;
        boolean itemType2=item.isCopy()==true;
        boolean itemType3=item.isCopy()==false && item.getItemCricul().isCricul()==false;

        if(itemType1){
           doneBtn.setVisible(false);
           progerssBar.setVisible(false);
           isCriculIco.setVisible(false);
           isPastDeadlineIco.setVisible(false);
           isFinshIco.setVisible(false);
        }
        if(itemType2){
           isCriculIco.setVisible(true);
            doneBtn.setVisible(true);
            progerssBar.setVisible(true);
        }
        if(itemType3){
            isCriculIco.setVisible(false);
            doneBtn.setVisible(true);
            progerssBar.setVisible(true);
        }



//        //判断是否为重复ToDo项，是的话，把标志显示出来
//        if (item.isCopy()){
//            isCriculIco.setVisible(true);
//        }

        if (item.isPastDeadline() &&(itemType2 || itemType3)){
            isPastDeadlineIco.setVisible(true);
        }
//
        if (item.isFinish() &&(itemType2 || itemType3)){
            isFinshIco.setVisible(true);
        }


        if(item.getImageFile()!=null){
            imageTooltip.setGraphic(new ImageView(CopyAndLoadImage.loadImage(item.getImageFile())));
        }



        if (!item.isFinish() ){
            progerssBar.setProgress(1-(double) Duration.between(creatTime,LocalDateTime.now()).getSeconds()
                    / Duration.between(creatTime,deadline).getSeconds());
        }

//        if (! item.isCopy()){
//            progerssBar.setVisible(true);
//            doneBtn.setVisible(true);
//        }
    }


    /**
     * @Author: Tptogiar
     * @Description: 更新剩余时间（每秒）,用来给计算倒计时的CountDownThread类调用
     * @Date: 2021/3/28-19:58
     */
    public void updateLeftTiemLabel() throws NoSuchItemException, IOException {

        LocalDateTime now = LocalDateTime.now();

        //如果完成了显示完成时间
        if (item.isFinish()){
            deadlineTooltip.setText("截止日期："+deadlineLabelText+"\n"+leftTimeLabelText);
            progerssBar.setProgress(1);
            return;
        }


        //如果此时此item已经超时，那么下面的判断和标签内容不用设置了（第一次检查到超时的时候已经设置了），
        // 直接return掉即可
        if (item.isPastDeadline()){
            progerssBar.setProgress(-1);
            return;
        }

        //计算剩余时间
        Duration currentLeftTime=Duration.between(now,deadline);

        if(currentLeftTime.getSeconds()<0){
            item.setPastDeadline(true);
            EventList.controlOfMainWindow.upContent(ItemsMgr.getItems());
            return;
        }

        String currentLeftTimeString="剩余："+ FormatServe.forDuration(currentLeftTime);
        deadlineTooltip.setText(deadlineLabelText+"\n"+currentLeftTimeString);
        progerssBar.setProgress(1-(double) Duration.between(creatTime,LocalDateTime.now()).getSeconds()
                / Duration.between(creatTime,deadline).getSeconds());

    }
    
    
    public void handleButtonClicks(ActionEvent actionEvent) throws IOException, NoSuchItemException {
        if (actionEvent.getSource()==doneBtn){
            EventList.done(item);
        }
        else if(actionEvent.getSource()== deleteBtn){
            if (item.getItemCricul().isCricul() && item.isCopy()==false){
                EventList.deleteCriculItem(item);
            }else{
                EventList.deleteItem(item);
            }
        }
        else if(actionEvent.getSource()==itemImage){
            EventList.chosePhoto(item);
        }
        else if(actionEvent.getSource()==edit){
            EventList.openCorrectItemStage(item,this);
        }
    }

    /**
     * @Author: Tptogiar
     * @Description: 插入图片
     * @Date: 2021/4/24-14:33 
     */
    public void showImage(MouseEvent mouseEvent) throws MalformedURLException {
        if(item.getImageFile()!=null){
            imageTooltip.setText(null);
            imageTooltip.setGraphic(new ImageView(CopyAndLoadImage.loadImage(item.getImageFile())));
        }else{
            imageTooltip.setText("单击此处插入图片");
        }
    }


    
    /**
     * @Author: Tptogiar
     * @Description: ToolTip默认的延迟有点长，感觉使用体验不佳，项修改他的延迟功能，发现没有预设相关的函数。
     * 翻看源码可以发现ToolTip下的内部类TooltipBehavior是私有的，外面拿不到，而关于ToolTip的
     * 延迟功能又都此类下设置了，看注释有说到“Typically, the tooltip is "activated" when the mouse moves over
     * a Control.There is usually some delay between when the Tooltip becomes "activated" and when it
     * is actually shown. The details (such as the amount of delay, etc)is left to the Skin implementation.”
     * 延迟的相关功能留给“Skin”去实现？但是我看不到ToolTip里面是怎么给Skin或是Skin的父类留下接口的，所以没能用“Skin”去实现，
     * 所以最后用反射来实现修改ToolTip的延迟功能了，先拿到ToolTip类和BEHAVIOR属性，再拿到它的内部类和构造器，再对齐进行修改
     *
     * @Date: 2021/4/24-14:36
     */
    public  void setTooltipDelay(Tooltip tooltip, int openDelaly, int visibleDuration, int colseDelay){
        try {
            Class tipClass = tooltip.getClass();
            Field f = tipClass.getDeclaredField("BEHAVIOR");
            f.setAccessible(true);
            Class behavior = Class.forName("javafx.scene.control.Tooltip$TooltipBehavior");
            Constructor constructor = behavior.getDeclaredConstructor(javafx.util.Duration.class, javafx.util.Duration.class, javafx.util.Duration.class, boolean.class);
            constructor.setAccessible(true);
            f.set(behavior, constructor.newInstance(new javafx.util.Duration(openDelaly), new javafx.util.Duration(visibleDuration), new javafx.util.Duration(colseDelay), false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}