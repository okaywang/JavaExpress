package dp.state;

/**
 * Created by Administrator on 2017/6/25.
 */
public class App {
    public static void main(String[] args) {
        Light light = new Light(new LightState0());
        light.flap();
        light.pressSwitch();
        light.flap();
        light.pressSwitch();
        light.pressSwitch();
        light.pressSwitch();
        light.pressSwitch();
        light.pressSwitch();
        light.pressSwitch();
        light.pressSwitch();
    }
}
