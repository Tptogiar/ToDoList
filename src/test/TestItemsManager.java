package test;
/**
 * @author Tptogiar
 * @creat 2021-03-24-15:34
 */

import main.model.Item;
import main.model.exception.NoSuchItemException;
import main.model.manager.ItemsMgr;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author:Tptogiar
 * @Description: 测试
 * @date: 2021/3/24 15:34
 *
 */
public class TestItemsManager {

    @Test
    public void testItemManger() throws NoSuchItemException {
//        ItemsManager itemsManager =ItemsManager.getItemsManager();
//        Item item1 = new Item("hong1", new ItemLabel(), "xiao1", new ItemAboutTime(), new ItemCricul(), false, 1001);
//        Item item2 = new Item("hong2", new ItemLabel(), "xiao2", new ItemAboutTime(), new ItemCricul(), false, 1002);
//        Item item3 = new Item("hong3", new ItemLabel(), "xiao3", new ItemAboutTime(), new ItemCricul(), false, 1003);
//        Item item4 = new Item("hong4", new ItemLabel(), "xiao4", new ItemAboutTime(), new ItemCricul(), false, 1004);
//
//
//        itemsManager.addOneItem(item1);
//        itemsManager.addOneItem(item2);
//        itemsManager.addOneItem(item3);
//
//        Iterator<Item> iterator = itemsManager.getItems().iterator();
//        while (iterator.hasNext()){
//            Item tepm=iterator.next();
//            System.out.println(tepm.getTitle()+"  "+tepm.isFinish());
//        }
//
//
//        try {
////            itemsManager.removeOneItem(item4);
//
////            itemsManager.fromNowToPast(item4);
//            itemsManager.fromNowToPast(item3);
////            itemsManager.removeOneItem(item3);
//            System.out.println();
//            Iterator<Item> iterator1 = itemsManager.getItems().iterator();
//            while (iterator1.hasNext()){
//                Item temp=iterator1.next();
//                System.out.println(temp.getTitle()+"  "+temp.isFinish());
//            }
//
//
//
//
//        }catch (Exception e){
//           System.out.println(e.getMessage());
//        }
//
//
//
//
//
    }

    @Test
    public void testItemLablesManager(){






    }


    @Test
    public void testItemsManager(){
        ItemsMgr itemsMgr = ItemsMgr.getItemsMgr();
        ArrayList<Item> items = ItemsMgr.getItems();
        HashMap<LocalDateTime, String[]> indexMap = ItemsMgr.getIndexMap();

//        Set<Map.Entry<LocalDateTime, String>> entries = indexMap.entrySet();
//        Iterator<Map.Entry<LocalDateTime, String>> iterator = entries.iterator();
//        while (iterator.hasNext()){
//            Map.Entry<LocalDateTime, String> next = iterator.next();
//            System.out.println(next.getKey()+"   "+next.getValue());
//        }


//        for (int i = 0; i < items.size(); i++) {
//            System.out.println(items.get(i).getTitle());
//        }

        ArrayList<Item> items1 = ItemsMgr.searchByStr("1");
        System.out.println(items1.size());
        System.out.println(items1);
        for (int i = 0; i < items1.size(); i++) {
            System.out.println(items.get(i).getTitle());
        }


    }

    @Test
    public void testStringcontains(){

        String str="hong";
        if(str.contains("on")){
            System.out.println("yes");
        }


    }

}