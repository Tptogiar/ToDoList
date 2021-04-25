package main.model.otherthread;/**
 * @author Tptogiar
 * @creat 2021-03-28-11:24
 */

import com.sun.org.apache.xerces.internal.impl.dtd.models.CMAny;
import main.controller.ControlOfItemBox;
import main.controller.ControlOfMainWindow;
import javafx.application.Platform;
import main.model.Item;
import main.model.ItemAboutTime;
import main.model.ItemCricul;
import main.model.ItemLabel;
import main.model.exception.NoSuchItemException;
import main.model.manager.ItemsMgr;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @author:Tptogiar
 * @Description: 创建此线程来实时计算那个ToDo项的剩余时间
 * @date: 2021/3/28 11:24
 *
 */
public class CountDownThread{

    public static ControlOfMainWindow mainWindowContorll;

    public static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * @Author: Tptogiar
     * @Description: 给线程分配任务
     * @Date: 2021/4/16-13:24
     */
    static {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                //运行javafx程序时，会自动生成FX application线程，且不予许在别的线程中修改界面上组件的信息，
                //所以此处应调用Platform.runLater，翻看源码可以知道，此方法会将参数runnable传递给Fx application线程执行
                Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        updateAllItemsLeftTime();
                    } catch (NoSuchItemException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            }
        },0,1, TimeUnit.SECONDS);






    }

    /**
     * @Author: Tptogiar
     * @Description: 更新普通item的倒计时
     * @Date: 2021/4/24-15:20
     */
    public static void updateAllItemsLeftTime() throws NoSuchItemException, IOException {
        ArrayList<ControlOfItemBox> itemboxs=mainWindowContorll.getItemboxControlOfItemBoxes();
        for (int i = 0; i < itemboxs.size(); i++) {
            itemboxs.get(i).updateLeftTiemLabel();
        }
    }

    /**
     * @Author: Tptogiar
     * @Description: 添加定时任务
     * @Date: 2021/4/24-15:20
     */
    public static ScheduledFuture addNewTask(TimerTask timerTask, long firstDelay, long period){
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(timerTask, firstDelay, period,TimeUnit.DAYS);
        //调试时一天等太久，可以改为秒作为单位，三个地方要改：这里，下面while语句内，CountDownThread类下的addNewTask函数内
//        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(timerTask, firstDelay, period,TimeUnit.SECONDS);
        return scheduledFuture;
    }

}