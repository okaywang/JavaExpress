/**
 * foo...Created by wgj on 2017/2/16.
 */
public class SyhProger {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("........................finalize");
        super.finalize();
    }

    @Override
    public String toString() {
        return "syh";
    }
}
