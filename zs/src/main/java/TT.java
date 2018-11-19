public class TT {
    public static void main(String[] args) {
        System.out.println(1111);
        checkYear(1999);
        checkYear(2000);
        checkYear(2004);
        checkYear(1900);
    }
    private static void checkYear(int a){
        if((a%4==0&&a%100!=0)||a%400==0)
        {
            System.out.println(a+"是闰年");
        }else
        {
            System.out.println(a+"不是闰年");
        }
    }

}
