package demo;

/**
 * foo...Created by wgj on 2017/3/25.
 */
public enum BlogTypeEnum {
    Finacial(1, "财经"),
    Constellation(2, "星座");
    private int value;
    private String text;

    private BlogTypeEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue(){
        return value;
    }

    public static BlogTypeEnum parse(int value){
        if (value == 1){
            return BlogTypeEnum.Finacial;
        }
        return  BlogTypeEnum.Constellation;
    }
}
