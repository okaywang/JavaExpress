package common;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class InitHelper {
    static final String DB_URL = "jdbc:mysql://111.230.147.33:3306/ptb";

    public static void initStocks() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException();
        }


        try {
            List<String> items = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("stock.txt"),"utf-8");
            Connection conn = DriverManager.getConnection(DB_URL, "root", "12345678");
            Statement stmt = conn.createStatement();
//            String sql = "SELECT code, name FROM stocks";
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                // 通过字段检索
//                String code = rs.getString("code");
//                String name = rs.getString("name");
//
//                System.out.print(code);
//                System.out.print(name);
//                System.out.print("\n");
//            }
            for (String item : items) {
                String[] subItems = item.split("\t");
                String sql = "INSERT into stocks (code,name) values ('" + subItems[0].substring(2) + "','" + subItems[1] + "')";
                stmt.execute(sql);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
