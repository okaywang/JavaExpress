
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.api.scripting.JSObject;
import model.Person;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * foo...Created by wgj on 2017/3/11.
 */
public class App {
    int i1 = 100;
    String s1 = null;
    String s2 = "ab";
    int i2 = 200;

    public static void main(String[] args) throws Exception {

        System.out.println("git test via idea");

        System.out.println(System.getProperty("user.dir"));

        long x = 3000000000L;
        Long y = 3000000000L;

        System.out.println((Long)x == y);


        int score[] = {67, 69, 75, 87, 89, 90, 99, 100};
        for (int i = 0; i < score.length -1; i++){    //最多做n-1趟排序
            for(int j = 0 ;j < score.length - i - 1; j++){    //对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
                if(score[j] < score[j + 1]){    //把小的值交换到后面
                    int temp = score[j];
                    score[j] = score[j + 1];
                    score[j + 1] = temp;
                }
            }
            System.out.print("第" + (i + 1) + "次排序结果：");
            for(int a = 0; a < score.length; a++){
                System.out.print(score[a] + "\t");
            }
            System.out.println("");
        }
        System.out.print("最终排序结果：");
        for(int a = 0; a < score.length; a++){
            System.out.print(score[a] + "\t");
        }
    }

    static int getInt(App test, String name) throws Exception {
        Unsafe u = getUnsafe();
        long offset = u.objectFieldOffset(App.class.getDeclaredField(name));
        return u.getInt(test, offset);
    }

    static Unsafe getUnsafe() throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }
}
