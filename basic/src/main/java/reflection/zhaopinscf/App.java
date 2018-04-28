package reflection.zhaopinscf;

/**
 * Created by wangguojun01 on 2018/4/24.
 */
public class App {
    public static void main(String[] args) {
        PositionController controller = new PositionController();
        String positionList = controller.getPositionListV1();
        System.out.println(positionList);


        String positionList2 = controller.getPositionListV2();
        System.out.println(positionList2);
    }
}
