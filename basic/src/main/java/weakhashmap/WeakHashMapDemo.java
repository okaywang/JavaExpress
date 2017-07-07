package weakhashmap;

import java.util.WeakHashMap;

/**
 * foo...Created by wgj on 2017/7/7.
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {
        // -- Fill a weak hash map with one entry
        WeakHashMap<Data, String> map = new WeakHashMap<Data, String>();
        Data someDataObject = new Data("foo");
        map.put(someDataObject, someDataObject.value);
        System.out.println("map contains someDataObject ? " + map.containsKey(someDataObject));

        // -- now make someDataObject elligible for garbage collection...
        someDataObject = null;

        for (int i = 0; i < 10000; i++) {
            byte[] bytes = new byte[100000];
            if (map.size() != 0) {
                System.out.println("At iteration " + i + " the map still holds the reference on someDataObject");
            } else {
                System.out.println("somDataObject has finally been garbage collected at iteration " + i + ", hence the map is now empty");
                break;
            }
        }
    }

    static class Data {
        String value;

        Data(String value) {
            this.value = value;
        }
    }
}
