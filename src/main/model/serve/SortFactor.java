package main.model.serve;/**
 * @author Tptogiar
 * @creat 2021-04-08-21:49
 */

import java.util.ArrayList;

/**
 * @author:Tptogiar
 * @Description: 枚举类，用来定义枚举的方式
 * @date: 2021/4/8 21:49
 *
 */
public class SortFactor {

    //排序类型的名称
    private final String sortType;


    /**
     * @Author: Tptogiar
     * @Description: 用于给属性赋值
     * @Date: 2021/4/8-21:52
     */
    private SortFactor(String sortType) {
        this.sortType = sortType;
    }


    //可选的枚举对象
    public static final SortFactor CREAT_TIME_POSITIVE=new SortFactor("创建日期正序");
    public static final SortFactor CREAT_TIME_INVERT=new SortFactor("创建日期倒序");
    public static final SortFactor DEADLINE_POSITIVE=new SortFactor("截止日期正序");
    public static final SortFactor DEADLINE_INVERT=new SortFactor("截止日期倒序");
    public static final SortFactor TITLE_POSITIVE=new SortFactor("标题正序");
    public static final SortFactor TITLE_INVERT=new SortFactor("标题倒序");


    public static ArrayList<SortFactor> sortFactors=new ArrayList<>();


    static {
        sortFactors.add(CREAT_TIME_POSITIVE);
        sortFactors.add(CREAT_TIME_INVERT);
        sortFactors.add(DEADLINE_POSITIVE);
        sortFactors.add(DEADLINE_INVERT);
        sortFactors.add(TITLE_POSITIVE);
        sortFactors.add(TITLE_INVERT);
    }

    /**
     * @Author: Tptogiar
     * @Description: 重写toString方法，使得在将其加入ChoiceBox时免去的转化为String的麻烦,
     * 将此类的对象直接加入ChoiceBox修改起来也方便
     * @Date: 2021/4/9-21:05
     */
    @Override
    public String toString() {
        return sortType;
    }

}
