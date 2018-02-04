package domain;

import java.time.LocalTime;

/**
 * Created by wangguojun01 on 2018/2/2.
 */
public class StandardDetailItem {
    private String time;
    private int volume;
    private double priceDelta;

    public StandardDetailItem(String time, int volume, double priceDelta) {
        this.time = time;
        this.volume = volume;
        this.priceDelta = priceDelta;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getPriceDelta() {
        return priceDelta;
    }

    public void setPriceDelta(double priceDelta) {
        this.priceDelta = priceDelta;
    }

    @Override
    public String toString() {
        return "StandardDetailItem{" +
                "time='" + time + '\'' +
                ", volume=" + volume +
                ", priceDelta=" + priceDelta +
                '}';
    }
}
