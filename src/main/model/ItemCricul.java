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
            //TODO 此处月有大小之分  待改
            circulCountOfIntervalPeriod=countOfIntervalUnits*30;
        }
        if(CriculUnit.YEAR.equals(criculUnit)){
            //TODO 此处年有闰年和非闰年之分   待改
            circulCountOfIntervalPeriod=countOfIntervalUnits*365;
        }
        return circulCountOfIntervalPeriod;
    }


    /**
     * @Author: Tptogiar
     * @Description: 判断设定的重复的次数是否有剩余
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
     * @Description: 在软件关闭后重新打开的时候，需要计算一下离下一次创建item的时间还有多久，计算后将结果作为定时器的firstDelay
     * @Date: 2021/4/24-15:36
     */
    public long getFirstDelay(){
        return addTheMissItemInUnRunning();
    }



    @Override
    public  void  run() {
        //由于ScheduledExecutorService本身已经是线程安全的了，所以同步器省了
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
     * @Description: 在程序打开后将本应该软件关闭时添加的任务添加到ItemsMgr.items中
     * @Date: 2021/4/24-15:38
     */
    public long addTheMissItemInUnRunning(){
        Duration duration = item.getAboutTime().getDuration();
        int count=circulCount-currrentLeftCount;
        Duration betweenToNext = Duration.between(LocalDateTime.now(), item.getAboutTime().getCreatTime().plusDays(count * getCountOfIntervalUnits()));
        //调试时一天等太久，可以改为秒作为单位，三个地方要改：这里，下面while语句内，CountDownThread类下的addNewTask函数内
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
