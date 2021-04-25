package main.model.manager;/**
 * @author Tptogiar
 * @creat 2021-03-24-12:55
 */

import main.controller.EventList;
import main.model.*;
import main.model.exception.IsAlreadyExistInItems;
import main.model.exception.NoSuchItemException;
import main.model.exception.NoToDoDateException;
import main.model.otherthread.CountDownThread;
import main.model.serve.ItemSortCompatator;
import main.model.serve.SaveAndLoadDate;
import main.model.serve.SortFactor;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ScheduledFuture;


/**
 * @author:Tptogiar
 * @Description: 实现对所有Item的管理
 * @date: 2021/3/24 12:55
 *
 */
public class ItemsMgr implements Serializable {

    //存储所有Item
    private static ArrayList<Item> items=new ArrayList<Item>();
    private static HashMap<Item,ScheduledFuture> item_ScheduleFuture=new HashMap<>();
    private static HashMap<LocalDateTime,String[]> indexMap =null;
    private static ArrayList<Item> criculItems =new ArrayList<>();

    public static File fileDir = new File("src\\UserData\\");
    public static File itemDateFile =new File("src\\UserData\\ToDo.data");
    public static File criculItemFile =new File("src\\UserData\\CriculItem.data");
    //ItemsManager采用单例模式，懒汉式实现
    private ItemsMgr() {

    }
    private static ItemsMgr itemsMgr =null;
    public synchronized static ItemsMgr getItemsMgr(){
        if (itemsMgr ==null){
            itemsMgr =new ItemsMgr();
        }
        return itemsMgr;

    }


    static {
        //将ToDo.data,Future.data中的数据加载到内存中
        try {
            ArrayList loadItems = (ArrayList) SaveAndLoadDate.loadObject(ItemsMgr.itemDateFile);
            ArrayList<Item> loadCriculItems =(ArrayList) SaveAndLoadDate.loadObject(criculItemFile);
            if(loadItems!=null){
                items=loadItems;
            }
            if(loadCriculItems!=null){
                criculItems =loadCriculItems;
                for (int i = 0; i < criculItems.size(); i++) {
                    criculItems.get(i).getItemCricul().setAddInThisRunning(false);
                }
            }
        } catch (NoToDoDateException e) {
            e.printStackTrace();
        }


        upCriculItemToFuture();


        if(indexMap ==null){
            indexMap =new HashMap<>();
            for (int i = 0; i < items.size(); i++) {
                String[] strIndex=new String[2];
                strIndex[0]=items.get(i).getTitle();
                strIndex[1]=items.get(i).getDescription();
                ItemsMgr.indexMap.put(items.get(i).getId(),strIndex);
            }
        }
    }


    public static ArrayList<Item>  getItems() {
        return items;
    }

    public static HashMap<LocalDateTime, String[]> getIndexMap() {
        return indexMap;
    }

    public static ArrayList<Item> getCriculItems() {
        return criculItems;
    }

    /**
     * @Author: Tptogiar
     * @Description: 新建Item，并添加到Items中,并保存到本地文件ToDo.data中
     * @Date: 2021/3/27-9:47
     */
    public static void newItem(String title, ItemLabel itemLabellabel, String description, ItemAboutTime itemAboutTime, ItemCricul itemCricul) throws NoSuchItemException, IsAlreadyExistInItems {

        Item item = new Item(title, itemLabellabel, description, itemAboutTime, itemCricul);
        if (isEqualSomeoneInItems(item)){
            throw new IsAlreadyExistInItems("已经存在相同的ToDo项");
        }
        items.add(item);
        SaveAndLoadDate.saveObject(items, itemDateFile);
    }

    public static void newItem(Item item){
        items.add(item);
        SaveAndLoadDate.saveObject(items,itemDateFile);
    }
    /**
     * @Author: Tptogiar
     * @Description: 用于循环的母体item和普通的item是分开储存的
     * @Date: 2021/4/24-15:15
     */
    public static void addCriculItem(String title, ItemLabel itemLabellabel, String description, ItemAboutTime itemAboutTime, ItemCricul itemCricul) throws NoSuchItemException, IsAlreadyExistInItems {
        Item item = new Item(title, itemLabellabel, description, itemAboutTime, itemCricul);
        item.getItemCricul().setItem(item);
        if (isEqualSomeoneInItems(item)){
            throw new IsAlreadyExistInItems("已经存在相同的ToDo项");
        }
        criculItems.add(item);
        upCriculItemToFuture();
        SaveAndLoadDate.saveObject(criculItems, criculItemFile);
    }

    /**
     * @Author: Tptogiar
     * @Description: 从Items中删除某个Item
     * @Date: 2021/3/24-13:36
     */
    public static void removeItem(Item item) throws NoSuchItemException {
        if (isExistInItems(item)){
            items.remove(item);
        }
    }

    /**
     * @Author: Tptogiar
     * @Description: 由于分开储存，删除自然也有所不同
     * @Date: 2021/4/24-15:16
     */
    public static void removeCriculItem(Item item) {
        criculItems.remove(item);
    }

    /**
     * @Author: Tptogiar
     * @Description: item完成操作，实现item从未完成状态到完成状态
     * @Date: 2021/3/24-13:57
     */
    public static void fromNowToPast(Item item) throws NoSuchItemException {
        if (isExistInItems(item)){
            int index=items.indexOf(item);
            item.setFinish(true);
            items.set(index,item);
        }
    }

    /**
     * @Author: Tptogiar
     * @Description: itme取消完成操作，实现item从完成状态到位未完成状态（暂时没有用）
     * @Date: 2021/3/24-13:57
     */
    public static void fromPastToNow(Item item) throws NoSuchItemException {
        if (isExistInItems(item)){
            int index=items.indexOf(item);
            item.setFinish(false);
            items.set(index,item);
        }
    }


    /**
     * @Author: Tptogiar
     * @Description: 考虑到ItemsManager类中许多中都需要一个判断某个Item是否在nowItems中的逻辑实现，
     * 所以单独把这个逻辑抽出来，写成一个独立的函数isExistInItems()
     * @Date: 2021/3/24-15:09
     */
    public static boolean isExistInItems(Item item) throws NoSuchItemException {
        int index=items.indexOf(item);
        if (-1!=index){
            return true;
        }else {
            throw new NoSuchItemException("没有找到该条ToDo项");
        }
    }

    public static boolean isEqualSomeoneInItems(Item item) throws NoSuchItemException {
        if(item.getItemCricul().isCricul()){
            for (int i = 0; i < criculItems.size(); i++) {
                if(item.equals(criculItems.get(i))){
                    return true;
                }
            }
        }else{
            for (int i = 0; i < ItemsMgr.getItems().size(); i++) {
                if (item.equals(ItemsMgr.getItems().get(i))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Author: Tptogiar
     * @Description: 对item进行排序，后显示排序后的顺序（只在内存层面进行排序）
     * @Date: 2021/4/8-22:06
     */
    public static void sortItemBy(SortFactor sortFactor) throws IOException {
        Collections.sort(items,new ItemSortCompatator(sortFactor));
    }

    /**
     * @Author: Tptogiar
     * @Description: 通过传进来的string进行查找，并返回所有符合条件的item
     * @Date: 2021/4/11-13:32
     */
    public static ArrayList<Item> searchByStr(String string){
        ArrayList<Item> searchResult = new ArrayList<>();
        Set<Map.Entry<LocalDateTime, String[]>> key_value = indexMap.entrySet();
        Iterator<Map.Entry<LocalDateTime, String[]>> iterator = key_value.iterator();
        while(iterator.hasNext()){
            Map.Entry<LocalDateTime, String[]> next = iterator.next();
            if(next.getValue()[0].contains(string) || next.getValue()[1].contains(string)){
                searchResult.add(searchItemById(next.getKey()));
            }
        }
        return searchResult;
    }

    /**
     * @Author: Tptogiar
     * @Description: 通过标签分类
     * @Date: 2021/4/15-23:29
     */
    public static ArrayList<Item> classifyBy(ItemLabel classifyItemLabel){

        ArrayList<Item> classifyResult=new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (classifyItemLabel!=null){
                if (classifyItemLabel.equals(items.get(i).getLabel())){
                    classifyResult.add(items.get(i));
                }
            }else {
                if (items.get(i).getLabel()==null){
                    classifyResult.add(items.get(i));
                }
            }
        }
        return classifyResult;

    }

    /**
     * @Author: Tptogiar
     * @Description: indexMap中只是用LocalDateTime作为key（考虑到用整个Item的话会比较废内存），建立这个函数
     * 使得localdatetime和Item关联起来
     * @Date: 2021/4/11-13:31
     */
    public static Item searchItemById(LocalDateTime localDateTime){
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getId().equals(localDateTime)){
                return items.get(i);
            }
        }
        return null;
    }


    /**
     * @Author: Tptogiar
     * @Description: 修改item的信心
     * @Date: 2021/4/24-15:18
     */
    public static void correctItem(Item item,String titleText,String descriptionText,
                                   LocalDateTime deadline,ItemLabel itemLabel,
                                   ItemCricul itemCricul) throws NoSuchItemException {
        if(isExistInItems(item)){
            Item shouldBeCorrentItem= items.get(items.indexOf(item));

            shouldBeCorrentItem.setTitle(titleText);
            shouldBeCorrentItem.setDescription(descriptionText);
            shouldBeCorrentItem.getAboutTime().setDeadlineTime(deadline);
            if(deadline.isAfter(LocalDateTime.now())){
                shouldBeCorrentItem.setPastDeadline(false);
            }

            shouldBeCorrentItem.getItemCricul().setCricul(itemCricul.isCricul());

            shouldBeCorrentItem.getItemCricul().setCirculCount(itemCricul.getCirculCount());
            shouldBeCorrentItem.getItemCricul().setCountOfIntervalUnits(itemCricul.getCountOfIntervalUnits());
            shouldBeCorrentItem.getItemCricul().setCriculUnitType(itemCricul.getCriculUnitType());

            shouldBeCorrentItem.setLabel(itemLabel);
            SaveAndLoadDate.saveObject(ItemsMgr.getItems(),ItemsMgr.itemDateFile);
        }
    }

    /**
     * @Author: Tptogiar
     * @Description: 修改母体item的信息
     * @Date: 2021/4/24-15:18
     */
    public static void correntCriculItem(Item item, String titleText, String descriptionText, LocalDateTime deadline, ItemLabel itemLabel, ItemCricul itemCricul) {

        Item shouldBeCorrentItem= criculItems.get(criculItems.indexOf(item));

        shouldBeCorrentItem.setTitle(titleText);
        shouldBeCorrentItem.setDescription(descriptionText);
        shouldBeCorrentItem.getAboutTime().setDeadlineTime(deadline);

        shouldBeCorrentItem.getItemCricul().setCricul(itemCricul.isCricul());
        shouldBeCorrentItem.getItemCricul().setCirculCount(itemCricul.getCirculCount());
        shouldBeCorrentItem.getItemCricul().setCountOfIntervalUnits(itemCricul.getCountOfIntervalUnits());
        shouldBeCorrentItem.getItemCricul().setCriculUnitType(itemCricul.getCriculUnitType());
        shouldBeCorrentItem.setLabel(itemLabel);


        SaveAndLoadDate.saveObject(ItemsMgr.getCriculItems(),ItemsMgr.criculItemFile);
    }

    /**
     * @Author: Tptogiar
     * @Description: 遍历所有母体item，将还没有将入定时任务的加入定时任务
     * @Date: 2021/4/24-15:19
     */
    public static void upCriculItemToFuture(){
        //给需要重复的item设置定时任务
        for (int i = 0; i < criculItems.size(); i++) {
            if( criculItems.get(i).getItemCricul().isAddInThisRunning()==false
                    && criculItems.get(i).getItemCricul().getCurrrentLeftCount()>0) {
                ItemCricul currentCricul = criculItems.get(i).getItemCricul();

                currentCricul.addTheMissItemInUnRunning();
                ScheduledFuture scheduledFuture = CountDownThread.addNewTask(currentCricul,
                        currentCricul.getFirstDelay(), currentCricul.getCriculPeriodOfIntervalUnit());


                //由于ScheduledFutureTask没有实现Serializable接口，所有不能将scheduledFuture直接作为Item或者
                //ItemCricul的属性，所以单独用hashMap将其与Item存起来
                item_ScheduleFuture.put(criculItems.get(i),scheduledFuture);
            }
        }
    }

    /**
     * @Author: Tptogiar
     * @Description: 将母体item从定时任务中删除
     * @Date: 2021/4/24-15:20
     */
    public static void deleteScheduleFuture(Item criculItem){
        Set<Map.Entry<Item, ScheduledFuture>> entries = item_ScheduleFuture.entrySet();
        Iterator<Map.Entry<Item, ScheduledFuture>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<Item, ScheduledFuture> next = iterator.next();
            if( criculItem.equals(next.getKey())){
                next.getValue().cancel(true);
            }
        }
    }



}