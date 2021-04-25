package test;

import main.model.serve.FormatServe;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/04/12-12:51
 */


public class TestFormat {

    @Test
    public void testFormatLoaclDateTimeForFileName(){

        String s = FormatServe.formatLoaclDateTimeForFileName(LocalDateTime.now());
        System.out.println(s);


    }


}
