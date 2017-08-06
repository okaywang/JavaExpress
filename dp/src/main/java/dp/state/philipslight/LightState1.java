package dp.state.philipslight;

/**
 * Created by Administrator on 2017/6/25.
 */
public class LightState1 extends LightStateBase {
    @Override
    public void pressSwitch(Light light) {
        light.changeState(new LightState2());
        System.out.println("1" + "-->" + "");
    }
}
