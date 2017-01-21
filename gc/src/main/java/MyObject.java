/**
 * Created by wgj on 2017/1/21.
 */
public class MyObject {
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("MyObject's finalize called");
    }

    @Override
    public String toString() {
        return  "I am MyObject";
    }
}
