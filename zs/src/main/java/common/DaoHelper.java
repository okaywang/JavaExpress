package common;

import provider.MinuteItem;

import java.sql.*;

/**
 * Created by wangguojun01 on 2018/2/5.
 */
public class DaoHelper {
    static final String DB_URL = "jdbc:mysql://111.230.147.33:3306/ptb";
    private static Connection conn;

    static {
        init();
    }

    public static void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, "root", "12345678");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertBatch(String code, MinuteItem[] minuteItem) {
        try {
            String sql = "INSERT into minute (code,time,price,sprice,delta,volume) values (?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            int i = 0;
            for (MinuteItem item : minuteItem) {
                statement.setString(1, code);
                statement.setString(2, item.getTime());
                statement.setFloat(3, item.getPrice());
                statement.setFloat(4, item.getSprice());
                statement.setFloat(5, item.getDelta());
                statement.setInt(6, item.getVolume());
                statement.addBatch();
                i++;
                if (i % 20 == 0) {
                    statement.executeBatch();
                    statement.clearBatch();
                }
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert(String code, MinuteItem minuteItem) {
        try {
            String sql = "INSERT into minute (code,time,price,sprice,delta,volume) values (?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, code);
            statement.setString(2, minuteItem.getTime());
            statement.setFloat(3, minuteItem.getPrice());
            statement.setFloat(4, minuteItem.getSprice());
            statement.setFloat(5, minuteItem.getDelta());
            statement.setInt(6, minuteItem.getVolume());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
