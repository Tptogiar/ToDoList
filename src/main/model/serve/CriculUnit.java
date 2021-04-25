package main.model.serve;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Tptogiar
 * @Descripiton: 循环的单位的枚举类
 * @creat 2021/04/21-00:05
 */


public class CriculUnit implements Serializable {

    private final String criculUnitType;

    public static final CriculUnit DAY=new CriculUnit("天");
    public static final CriculUnit WEEK=new CriculUnit("周");
    public static final CriculUnit MONTH=new CriculUnit("月");
    public static final CriculUnit YEAR=new CriculUnit("年");


    public static ArrayList<CriculUnit> criculUnits=new ArrayList<>();

    public CriculUnit(String criculUnitType) {
        this.criculUnitType = criculUnitType;
    }
    static {
        criculUnits.add(DAY);
        criculUnits.add(WEEK);
        criculUnits.add(MONTH);
        criculUnits.add(YEAR);
    }

    @Override
    public String toString() {
        return criculUnitType ;
    }
}
