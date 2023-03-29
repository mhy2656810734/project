package model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 26568
 * @date 2023-03-21 10:46
 */
// 用这个来封装一下数据库的连接
// 提供一些静态方法  供其他类使用
public class DBUtil {
    private static DataSource dataSource = new MysqlDataSource();
    static {
        ((MysqlDataSource)dataSource).setURL("jdbc:mysql://127.0.0.1:3306/blog_system?characterEncoding=utf8&useSSL=false");
        ((MysqlDataSource)dataSource).setUser("root");
        ((MysqlDataSource)dataSource).setPassword("weiaizuqiu1314");
    }
    // 提供建立连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    // 关闭资源
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
