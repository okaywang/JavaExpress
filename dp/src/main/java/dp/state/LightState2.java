package dp.state;

/**
 * Created by Administrator on 2017/6/25.
 */
public class LightState2 extends LightStateBase {
    @Override
    public void pressSwitch(Light light) {
        light.changeState(new LightState3());
        System.out.println("2" + "-->" + "");
    }
}
