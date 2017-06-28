package dp.state;

/**
 * Created by Administrator on 2017/6/25.
 */
public class LightStateBase {
    public void pressSwitch(Light light) {
    }

    /**
     * 拍一拍的功能。
     * 黑暗的时候，按钮开关其实比不好找，为了使设计更加人性化，当灯还没亮的时候，
     * 只要拍一拍，就能直接使灯的亮度值调到2
     * 因此，只需state0重写就可以了。
     */
    public void flap(Light light) {

    }
}
