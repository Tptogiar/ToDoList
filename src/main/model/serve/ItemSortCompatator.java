package main.model.serve;/**
 * @author Tptogiar
 * @creat 2021-04-09-19:49
 */

import main.model.Item;

import java.util.Comparator;

/**
 * @author:Tptogiar
 * @Description: 排序用的比较器
 * @date: 2021/4/9 19:49
 *
 */
public class ItemSortCompatator implements Comparator {

    private SortFactor sortFactor;


    public ItemSortCompatator(SortFactor sortFactor){
        this.sortFactor=sortFactor;
    }


    /**
     * @Author: Tptogiar
     * @Description: 创建此比较器时已经通过构造器给sortFactor赋值，调用Compare方法时根据不同的
     * 比较因素进行比较
     * @Date: 2021/4/9-20:09
     */
    @Override
    public int compare(Object o1, Object o2) {
        Item item1=(Item)o1;
        Item item2=(Item)o2;
        if (sortFactor.equals(SortFactor.CREAT_TIME_POSITIVE)){
            return item1.getAboutTime().getCreatTime().compareTo(item2.getAboutTime().getCreatTime());
        }
        if (sortFactor.equals(SortFactor.CREAT_TIME_INVERT)){
            return -item1.getAboutTime().getCreatTime().compareTo(item2.getAboutTime().getCreatTime());
        }
        if (sortFactor.equals(SortFactor.DEADLINE_POSITIVE)){
            return item1.getAboutTime().getDeadlineTime().compareTo(item2.getAboutTime().getDeadlineTime());
        }
        if (sortFactor.equals(SortFactor.DEADLINE_INVERT)){
            return -item1.getAboutTime().getDeadlineTime().compareTo(item2.getAboutTime().getDeadlineTime());
        }
        if (sortFactor.equals(SortFactor.TITLE_POSITIVE)){
            return item1.getTitle().compareTo(item2.getTitle());
        }
        if (sortFactor.equals(SortFactor.TITLE_INVERT)){
            return -item1.getTitle().compareTo(item2.getTitle());
        }
        return 0;
    }
}
