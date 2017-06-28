package dp.state;

/**
 * Created by Administrator on 2017/6/25.
 */
public class LightState0 extends LightStateBase {
    @Override
    public void pressSwitch(Light light) {
        light.changeState(new LightState1());
        System.out.println("0" + "-->" + "");
    }

    @Override
    public void flap(Light light) {
        light.changeState(new LightState2());
        System.out.println("0" + "-->" + "");
    }
}
