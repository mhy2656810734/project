package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 26568
 * @date 2023-03-21 12:34
 */
// 通过这个类 封装 针对用户表 的一些操作
public class UserDao {
    // 1.根据userId 来查用户信息
    public User selectById(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from user where useId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("useId"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,null);
        }
        return null;
    }
    // 2. 根据名字来查询用户 (登录的时候)
    public User selectByUsername(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from user where username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("useId"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,null);
        }
        return null;
    }
}
