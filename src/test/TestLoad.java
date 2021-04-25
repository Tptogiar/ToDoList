package test;

import main.model.exception.NoToDoDateException;
import main.model.serve.SaveAndLoadDate;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/04/15-22:09
 */


public class TestLoad {




    @Test
    public void testloadItemLabel() throws NoToDoDateException {
//        ItemLabelsMgr.newItemLable("fdf");
//        ItemLabelsMgr.newItemLable("fdf234");
//        ItemLabelsMgr.newItemLable("fdf2");
//
//        System.out.println(ItemLabelsMgr.getItemLabels());

        SaveAndLoadDate.saveObject(new ArrayList(),new File("src\\UserData\\LabelData.data"));
//        ArrayList o = (ArrayList) SaveAndLoadDate.loadItems(ItemLabelsMgr.itemLabelDateFile);
//        System.out.println(o.size());

    }


}
