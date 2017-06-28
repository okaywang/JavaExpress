package dp.state;

/**
 * Created by Administrator on 2017/6/25.
 */
public class LightState3 extends LightStateBase {
    @Override
    public void pressSwitch(Light light) {
        light.changeState(new LightState0());
        System.out.println("3" + "-->" + "");
    }
}
