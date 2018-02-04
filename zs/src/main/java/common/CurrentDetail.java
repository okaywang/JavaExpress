package common;

import java.time.LocalTime;

/**
 * Created by wangguojun01 on 2018/2/2.
 */
public class CurrentDetail {
    private LocalTime time;
    private double price;
    private int cjl;
    private double priceChange;

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCjl() {
        return cjl;
    }

    public void setCjl(int cjl) {
        this.cjl = cjl;
    }

    public double getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(double priceChange) {
        this.priceChange = priceChange;
    }

    @Override
    public String toString() {
        return "CurrentDetail{" +
                "time=" + time +
                ", price=" + price +
                ", cjl=" + cjl +
                ", priceChange=" + priceChange +
                '}';
    }
}
