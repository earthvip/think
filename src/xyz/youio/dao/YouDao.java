package xyz.youio.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;

/**
 * Created by admin@earthvip.xyz on 2017/1/24.
 * 数据库操作，结果封装
 */
public class YouDao {
    public static List<Object> execute(String sql) {
        Connection connection = YouPool.getConnection();
        List<Object> objects = new ArrayList<>();
        Map<Object, Object> map = null;
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();

                if (sql.startsWith("s")) {
                    ResultSet resultSet = statement.executeQuery(sql);

                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    while (resultSet.next()) {
                        map=new HashMap<>();
                        for (int i = 0; i < columnCount; i++) {
                            map.put(metaData.getColumnName(i+1),resultSet.getObject(i+1));
                        }
                        objects.add(map);
                    }
                } else {
                    statement.execute(sql);
                    if(statement.getUpdateCount()!=0){
                        objects.add(true);
                    }else {
                        objects.add(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                objects.add(false);
            } finally {
                YouPool.closeConnection(connection);
            }
        }else{
            System.out.println("pool:0");
            objects.add(false);
        }
        return objects;
    }
}
