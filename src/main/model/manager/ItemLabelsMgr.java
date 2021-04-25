package main.model.manager;/**
 * @author Tptogiar
 * @creat 2021-03-24-13:59
 */

import main.model.ItemLabel;
import main.model.exception.NoSuchItemLableException;
import main.model.exception.NoToDoDateException;
import main.model.serve.SaveAndLoadDate;

import java.io.File;
import java.util.ArrayList;

/**
 * @author:Tptogiar
 * @Description: 实现对所有ItemLabel的管理
 * @date: 2021/3/24 13:59
 *
 */
public class ItemLabelsMgr {

    //用来存储目前已经新建的标签
    private static ArrayList<ItemLabel> itemLabels=new ArrayList<>();

    public static File itemLabelDateFile =new File("src\\UserData\\LabelData.data");

    //采用单例模式，饿汉式实现
    private ItemLabelsMgr(){ }

    private static ItemLabelsMgr itemLabelsMgr =new ItemLabelsMgr();

    public static ItemLabelsMgr getItemLabelsMgr(){
        return itemLabelsMgr;
    }

    public static ArrayList<ItemLabel> getItemLabels() {
        return itemLabels;
    }




    static {
        itemLabels.add(new ItemLabel("       "));
        try {
            ArrayList<ItemLabel> loadItemLabels = (ArrayList<ItemLabel>) SaveAndLoadDate.loadObject(itemLabelDateFile);
            if (loadItemLabels!=null){
                setItemLabels(loadItemLabels);
            }
        } catch (NoToDoDateException e) {
            e.printStackTrace();
        }
    }


    public static void setItemLabels(ArrayList<ItemLabel> itemLabels) {
        ItemLabelsMgr.itemLabels = itemLabels;
    }

    /**
     * @Author: Tptogiar
     * @Description: 新建itmeLable,并向itemLabels中添加新建出来的标签
     * @Date: 2021/3/27-9:36
     */
    public static void addItemLabel(String itemLableType) throws NoSuchItemLableException {
        ItemLabel itemLabel = new ItemLabel(itemLableType);
        if (! isExistInItemsLable(itemLabel)){
            itemLabels.add(itemLabel);
        }
    }


    /**
     * @Author: Tptogiar
     * @Description: 删除itemLabel中对应的标签
     * @Date: 2021/3/24-14:09
     */
    public static void removeItemLable(ItemLabel itemLabel) throws NoSuchItemLableException {
        if(isExistInItemsLable(itemLabel)) {
            itemLabels.remove(itemLabel);
        }
        SaveAndLoadDate.saveObject(itemLabels,itemLabelDateFile);
    }

    /**
     * @Author: Tptogiar
     * @Description: 修改itemLables中的某一个
     * @Date: 2021/3/24-18:19
     */
    public static void correctItemLable(ItemLabel itemLabel) throws NoSuchItemLableException {
        if (isExistInItemsLable(itemLabel)){
            int index=itemLabels.indexOf(itemLabel);
            itemLabels.set(index,itemLabel);
        }
    }

    /**
     * @Author: Tptogiar
     * @Description: 将判断数组中是否存在item的逻辑单独抽出来
     * @Date: 2021/3/24-21:38
     */
    public static boolean isExistInItemsLable(ItemLabel itemLabel) throws NoSuchItemLableException {
        int index=itemLabels.indexOf(itemLabel);
        if (-1!=index){
            return true;
        }else {
            return false;
        }
    }

}
