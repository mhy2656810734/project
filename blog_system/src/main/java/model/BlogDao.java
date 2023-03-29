package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 26568
 * @date 2023-03-21 11:02
 */
// 通过这个类 封装针对 博客表 的基本操作
public class BlogDao {
    // 1.新增一个博客
    public void add(Blog blog) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 1.和数据库建立连接
            connection = DBUtil.getConnection();
            // 2.构造sql语句
            String sql = "insert into blog values(null,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,blog.getTitle());
            statement.setString(2, blog.getContent());
           // statement.setTimestamp(3,blog.getPostTime());
            statement.setString(3,blog.getPostTime());
            statement.setInt(4,blog.getUserId());
            // 3.执行sql
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4.关闭资源
            DBUtil.close(connection,statement,null);
        }
    }
    // 2.根据博客id 来查询指定博客(用于博客详情页)
    public Blog selectById(int blogId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 1.和数据库建立连接
            connection = DBUtil.getConnection();
            // 2.构造sql语句
            String sql = "select * from blog where blogId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,blogId);
            // 3.执行sql语句
            resultSet = statement.executeQuery();
            // 4.循环遍历结果集合
            // 根据博客id查询的结果集合 id不重复 要么只有一条记录 要么没有记录
            if (resultSet.next()) {
                // 创建一个blog 类
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blog.setUserId(resultSet.getInt("userId"));
                return blog;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5.关闭资源
            DBUtil.close(connection,statement,resultSet);
        }
        return null;
    }
    // 3.直接查询出数据库中所有的博客列表(用于博客列表页)
    public List<Blog> selectAll() {
        List<Blog> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 1.和数据库建立连接
            connection = DBUtil.getConnection();
            // 2.构造sql语句
            String sql = "select * from blog order by postTime desc";
            statement = connection.prepareStatement(sql);
            // 3.执行sql语句
            resultSet = statement.executeQuery();
            // 4.循环遍历结果集合
            // 根据博客id查询的结果集合 id不重复 要么只有一条记录 要么没有记录
            while (resultSet.next()) {
                // 创建一个blog 类
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                // 博客正文太长,这里截取一部分
                String content = resultSet.getString("content");
                if (content == null) {
                    content = "";
                }
                if (content.length() >= 100) {
                    content = content.substring(0,100);
                }
                blog.setContent(content);
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blog.setUserId(resultSet.getInt("userId"));
                list.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5.关闭资源
            DBUtil.close(connection,statement,resultSet);
        }
        return list;
    }
    // 4.删除指定博客
    public void delete(int blogId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 1.和数据库建立连接
            connection = DBUtil.getConnection();
            // 2.构造sql语句
            String sql = "delete from blog where blog = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,blogId);
            // 3.执行sql语句
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 4.断开连接
            DBUtil.close(connection,statement,null);
        }
    }
}
