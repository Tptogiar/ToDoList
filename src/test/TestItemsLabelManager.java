package test;/**
 * @author Tptogiar
 * @creat 2021-03-24-18:04
 */

import main.model.exception.NoSuchItemLableException;
import main.model.manager.ItemLabelsMgr;
import org.junit.Test;

/**
 * @author:Tptogiar
 * @Description: 测试ItemLabelsManager
 * @date: 2021/3/24 18:04
 *
 */
public class TestItemsLabelManager {

    @Test
    public void testItemLablesManager() throws NoSuchItemLableException {
        ItemLabelsMgr itemLabelsMgr = ItemLabelsMgr.getItemLabelsMgr();
//        itemLabelsManager.addItemLable(new ItemLabel("hong1"));
//        itemLabelsManager.addItemLable(new ItemLabel("hong2"));
//        itemLabelsManager.addItemLable(new ItemLabel("hong3"));
//        itemLabelsManager.removeItemLable(new ItemLabel("hong4"));



//        Iterator<ItemLabel> iterator = itemLabelsManager.getItemLabels().iterator();
//        while (iterator.hasNext()){
//            ItemLabel next = iterator.next();
//            System.out.println(next.getItemLabelType()+  "   "+next.getId());
//        }

    }





}
