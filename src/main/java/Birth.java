import java.lang.reflect.Constructor;
import java.text.NumberFormat;

/**
 * foo...Created by wgj on 2017/2/20.
 */
public class Birth {
    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println(NumberFormat.getInstance().format(System.nanoTime()));
        Employee employee = new Employee();
        Constructor<Employee> aa = Employee.class.getConstructor();

    }
}
