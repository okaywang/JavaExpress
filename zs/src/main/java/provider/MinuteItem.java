package provider;

import common.AStockHelper;

public class MinuteItem {
    private String time;
    private float sprice;
    private float price;
    private float delta;
    private int volume;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public float getSprice() {
        return sprice;
    }

    public void setSprice(float sprice) {
        this.sprice = sprice;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "MinuteItem{" +
                "time='" + time + '\'' +
                ", price=" + AStockHelper.DecimalFormat.format(price) +
                ", sprice=" + AStockHelper.DecimalFormat.format(sprice) +
                ", delta=" + AStockHelper.DecimalFormat.format(delta) +
                ", volume=" + volume +
                '}';
    }
}
