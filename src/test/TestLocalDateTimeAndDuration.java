package test;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.lang.Thread.sleep;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/04/21-21:03
 */


public class TestLocalDateTimeAndDuration {


    @Test
    public void testLocalDateTimeAndDuration() throws InterruptedException {

        LocalDateTime now1 = LocalDateTime.now();
        sleep(1000);
        LocalDateTime now2 = LocalDateTime.now();
        Duration between = Duration.between(now1, now2);
        System.out.println(now1+"  "+now2);
        now1 = now1.plusSeconds(between.getSeconds());
        System.out.println(now1+"  "+now2);


    }








}
