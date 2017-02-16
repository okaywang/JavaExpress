import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * Created by wgj on 2017/1/21.
 */
public class App {
    public static void main(String[] args) {
        Arrays.asList("wgj","ljg","lw").forEach(System.out::println);
        
        System.out.println(  Stream.generate(Math::random).limit(10).sorted().reduce(10d,Double::sum));
        //Arrays.stream(App.class.getMethods()).map((x) -> x.getReturnType().getName()).forEach(App::P);
    }

    public int p2(){
return 3;
    }
    public static void P(String str)
    {
        System.err.println(str);
    }
}
