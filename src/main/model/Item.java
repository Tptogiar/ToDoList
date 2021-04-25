package main.model;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import main.view.itemvbox.ItemAnchorPane;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Tptogiar
 * @creat 2021-03-24-12:03
 */
public class Item implements Serializable {

    //记录是否已经完成
    private boolean isFinish=false;
    //记录是否已经超时
    private boolean isPastDeadline=false;

    private String title;
    private String description;

    private ItemLabel label;
    private ItemAboutTime aboutTime;
    private ItemCricul itemCricul;
    private boolean isCopy=false;

    private LocalDateTime id;
    private int orderNumber;//记录这个item是在本次重复的item里面的第几个，有小bug，所以暂时弃用了

    private File imageFile=null;

    public Item(Item indeedItme, ItemAboutTime itemAboutTime) {
        this(indeedItme.title,indeedItme.getLabel(),indeedItme.getDescription(),itemAboutTime,indeedItme.getItemCricul());
    }

    public Item(String title, ItemLabel label, String description, ItemAboutTime aboutTime, ItemCricul itemCricul) {
        this.title = title;
        this.label = label;
        this.description = description;
        this.aboutTime = aboutTime;
        this.itemCricul = itemCricul;
        this.id=LocalDateTime.now();

    }

    //记录这个item是在本次重复的item里面的第几个，有小bug，所以暂时弃用了
//    public int getOrderNumber() {
//        return orderNumber;
//    }
//
//    public void setOrderNumber(int orderNumber) {
//        this.orderNumber = orderNumber;
//        System.out.println("setOrderNumber="+this.orderNumber);
//    }

    public File getImageFile() {
        return imageFile;
    }

    public boolean isCopy() {
        return isCopy;
    }

    public void setCopy(boolean copy) {
        isCopy = copy;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }


    public void setId(LocalDateTime id) {
        this.id = id;
    }


    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ItemLabel getLabel() {
        return label;
    }

    public ItemAboutTime getAboutTime() {
        return aboutTime;
    }


    public LocalDateTime getId() {
        return id;
    }

    public void setPastDeadline(boolean pastDeadline) {
        isPastDeadline = pastDeadline;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public boolean isPastDeadline() {
        return isPastDeadline;
    }


    public ItemCricul getItemCricul() {
        return itemCricul;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLabel(ItemLabel label) {
        this.label = label;
    }

    /**
     *
     * @Author: Tptogiar
     * @Description: 重写equals和hashCode方法，以方便后面此类的HashSet集合等调用查找方法等
     * @Date: 2021/3/24-14:13
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return title.equals(item.title) &&
                description.equals(item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", label=" + label +
                ", description='" + description + '\'' +
                ", aboutTime=" + aboutTime +
                ", itemCricul=" + itemCricul +
                ", isFinish=" + isFinish +
                ", id=" + id +
                '}';
    }


}
