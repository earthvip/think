package xyz.youio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin@earthvip.xyz on 2017/1/24.
 * 数据库连接池
 */
public class YouPool {
    private static List list=new ArrayList<>();
    private static List daolist=new ArrayList<>();
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            for (int i=0;i<10;i++){
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/youio", "root", "root");
                list.add(connection);
                YouDao youDao = new YouDao();
                daolist.add(youDao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //分配连接
    public static Connection getConnection() {
        //没有空闲连接直接拒绝访问
        Connection connection=null;
        if(list.size()!=0){
            connection=(Connection) list.remove(0);
        }
        return connection;
    }

    //释放连接
    public static void closeConnection(Connection connection) {
        list.add(connection);
    }

}
