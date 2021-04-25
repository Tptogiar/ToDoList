package main.model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Tptogiar
 * @creat 2021-03-24-12:00
 */
public class ItemAboutTime implements Serializable {

    private LocalDateTime creatTime;
    private LocalDateTime deadlineTime;
    private Duration duration;
    private LocalDateTime finshTime;



    public ItemAboutTime(LocalDateTime creatTime, LocalDateTime deadline, Duration leftTime) {
        this.creatTime = creatTime;
        this.deadlineTime = deadline;
        this.duration = leftTime;
    }

    public LocalDateTime getCreatTime() {
        return creatTime;
    }

    public LocalDateTime getDeadlineTime() {
        return deadlineTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getFinshTime() {
        return finshTime;
    }


    public void setFinshTime(LocalDateTime finshTime) {
        this.finshTime = finshTime;
    }

    public void setDeadlineTime(LocalDateTime deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setCreatTime(LocalDateTime creatTime) {
        this.creatTime = creatTime;
    }
}
