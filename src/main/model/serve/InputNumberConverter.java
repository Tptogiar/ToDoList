package main.model.serve;/**
 * @author Tptogiar
 * @creat 2021-04-09-21:46
 */





import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.regex.Pattern;

/**
 * @author:Tptogiar
 * @Description: 文本过滤，用于对输入框进行文本过滤，避免引起异常
 * @date: 2021/4/9 21:46
 *
 */



public class InputNumberConverter extends StringConverter<String> {


    //能输入的最大数字
    private int inputNumberMax;
    private int inputNumberMin;

    public InputNumberConverter(int inputNumberMax, int inputNumberMin){
        this.inputNumberMax=inputNumberMax;
        this.inputNumberMin=inputNumberMin;

    }

    /**
     * @Author: Tptogiar
     * @Description: 过滤非数字字符
     * @Date: 2021/4/24-15:27
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }



    /**
     * @Author: Tptogiar
     * @Description: 对输入进来的内容进行过滤，且转换为数字后不能大于最大能输入的数字
     * @Date: 2021/4/9-23:47
     */
    @Override
    public String toString(String string) {
        return string;
    }

    @Override
    public String fromString(String string) {
        //只保留数字
        string=Pattern.compile("[^0-9]").matcher(string).replaceAll("");
        if(string.length()>2){
            string=string.substring(0,2);
        }
        //设置最大最小数
        if(string.length()>0&& Integer.valueOf(string)>inputNumberMax ){
            string=String.valueOf(inputNumberMax);
        }
        if(string.length()>0&& Integer.valueOf(string)<inputNumberMin ){
            string=String.valueOf(inputNumberMin);
        }

        return string;
    }

}



