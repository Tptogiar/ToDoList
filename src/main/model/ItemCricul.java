package main.model;

import main.controller.EventList;
import main.model.serve.CriculUnit;

import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Tptogiar
 * @creat 2021-03-24-12:05
 */
public class ItemCricul extends TimerTask implements Serializable {

    private boolean isCricul=false;
    private int countOfIntervalUnits;
    private CriculUnit criculUnit;
    private int circulCount;
    private Item item;
    public ScheduledFuture scheduledFuture;
    private int currrentLeftCount;
    private boolean isAddInThisRunning=false;



    public boolean isAddInThisRunning() {
        return isAddInThisRunning;
    }

    public void setAddInThisRunning(boolean addInThisRunning) {
        isAddInThisRunning = addInThisRunning;
    }


    public boolean isCricul() {
        return isCricul;
    }

    public void setCricul(boolean cricul) {
        isCricul = cricul;
    }


    public int getCirculCount() {
        return circulCount;
    }

    public int getCountOfIntervalUnits() {
        return countOfIntervalUnits;
    }


    public void setCountOfIntervalUnits(int countOfIntervalUnits) {
        this.countOfIntervalUnits = countOfIntervalUnits;
    }

    public void setCriculUnitType(CriculUnit criculUnit) {
        this.criculUnit = criculUnit;
    }

    public void setCirculCount(int circulCount) {
        this.circulCount = circulCount;
        this.currrentLeftCount=circulCount;
    }

    public CriculUnit getCriculUnitType() {
        return criculUnit;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCurrrentLeftCount() {
        return currrentLeftCount;
    }


    public ItemCricul(boolean isCricul, int countOfIntervalUnits, CriculUnit criculUnit, int circulCount) {
        this.isCricul = isCricul;
        this.circulCount = circulCount;
        this.countOfIntervalUnits =countOfIntervalUnits;
        this.criculUnit=criculUnit;
        currrentLeftCount=circulCount;
    }




    public long getCriculPeriodOfIntervalUnit(){
        long circulCountOfIntervalPeriod=countOfIntervalUnits;
        if(CriculUnit.WEEK.equals(criculUnit)){
            circulCountOfIntervalPeriod=countOfIntervalUnits*7;
        }
        if(CriculUnit.MONTH.equals(criculUnit)){
            //TODO ????????????????????????  ??????
            circulCountOfIntervalPeriod=countOfIntervalUnits*30;
        }
        if(CriculUnit.YEAR.equals(criculUnit)){
            //TODO ????????????????????????????????????   ??????
            circulCountOfIntervalPeriod=countOfIntervalUnits*365;
        }
        return circulCountOfIntervalPeriod;
    }


    /**
     * @Author: Tptogiar
     * @Description: ?????????????????????????????????????????????
     * @Date: 2021/4/24-15:35
     */
    public boolean isNoLeft(){
        if(currrentLeftCount<=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @Author: Tptogiar
     * @Description: ??????????????????????????????????????????????????????????????????????????????item????????????????????????????????????????????????????????????firstDelay
     * @Date: 2021/4/24-15:36
     */
    public long getFirstDelay(){
        return addTheMissItemInUnRunning();
    }



    @Override
    public  void  run() {
        //??????ScheduledExecutorService?????????????????????????????????????????????????????????
        if(isNoLeft()){
            scheduledFuture.cancel(true);
        }
        try {
            EventList.saveCriculItem(item);
            currrentLeftCount--;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @Author: Tptogiar
     * @Description: ?????????????????????????????????????????????????????????????????????ItemsMgr.items???
     * @Date: 2021/4/24-15:38
     */
    public long addTheMissItemInUnRunning(){
        Duration duration = item.getAboutTime().getDuration();
        int count=circulCount-currrentLeftCount;
        Duration betweenToNext = Duration.between(LocalDateTime.now(), item.getAboutTime().getCreatTime().plusDays(count * getCountOfIntervalUnits()));
        //?????????????????????????????????????????????????????????????????????????????????????????????while????????????CountDownThread?????????addNewTask?????????
//        Duration betweenToNext = Duration.between(LocalDateTime.now(), item.getAboutTime().getCreatTime().plusSeconds(count * getCountOfIntervalUnits()));
        while (betweenToNext.getSeconds()<0 && currrentLeftCount>0){

            System.out.print("ToNext="+betweenToNext.getSeconds());
            System.out.print("  criculCount="+circulCount);
            System.out.println("  currentleftCount="+currrentLeftCount);

            try {
                EventList.saveCriculItem(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
            currrentLeftCount--;

            count=circulCount-currrentLeftCount;
        betweenToNext = Duration.between(LocalDateTime.now(), item.getAboutTime().getCreatTime().plusDays(count * getCountOfIntervalUnits()));

//            betweenToNext = Duration.between(LocalDateTime.now(), item.getAboutTime().getCreatTime().plusSeconds(count * getCountOfIntervalUnits()));
        }
        return betweenToNext.getSeconds();

    }




}
