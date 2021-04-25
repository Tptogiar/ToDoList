package main.model.serve;/**
 * @author Tptogiar
 * @creat 2021-03-28-11:03
 */

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author:Tptogiar
 * @Description: 格式化时间
 * @date: 2021/3/28 11:03
 *
 */
public class FormatServe {


    /**
     * @Author: Tptogiar
     * @Description: 将LoaclDateTime输出为固定格式的字符串
     * @Date: 2021/3/30-13:56
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime){
        DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd    HH:mm:ss");
        String formatTime = localDateTimeFormat.format(localDateTime.plusYears(1));
        return formatTime;
    }

    /**
     * @Author: Tptogiar
     * @Description: 将duration转换为比较好看的格式
     * @Date: 2021/4/24-15:26
     */
    public static String forDuration(Duration duration){
        long getseconds = duration.getSeconds();

        long seconds=getseconds%60;
        long minute=(getseconds%3600)/60;
        long hour=(getseconds/3600);
        long day=hour/24;


        String formatDuration=null;
        if (day>=1){
            formatDuration= String.format( "%d天 %d:%02d:%02d", day, hour%24, minute,seconds);
        }else{
            formatDuration= String.format( "%d:%02d:%02d", hour, minute,seconds);
        }
        return formatDuration;
    }

    /**
     * @Author: Tptogiar
     * @Description: 将localdateTime转换为文件名格式
     * @Date: 2021/4/24-15:27
     */
    public static String formatLoaclDateTimeForFileName(LocalDateTime localDateTime){
        DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmSS");
        String fileName=localDateTime.format(localDateTimeFormat);
        return fileName;
    }

}
