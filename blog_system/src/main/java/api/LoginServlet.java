package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 26568
 * @date 2023-03-23 11:16
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 按照什么格式来解析 避免输入中文乱码
        req.setCharacterEncoding("utf8");
        // 设置响应的字符格式
        resp.setContentType("text/html;charset=utf8");
        // 1.读取参数中的用户名和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            String html = "<h3>登录失败! 缺少username 或者 password 字段 <h3>";
            resp.getWriter().write(html);
            return;
        }
        // 2.查看数据库 看用户是否真的存在
        UserDao userDao = new UserDao();
        User user = userDao.selectByUsername(username);
        if (user == null) {
            // 说明数据库没有这个用户
            String html = "<h3>登录失败! 用户名或者密码错误<h3>";
            resp.getWriter().write(html);
            return;
        }
        // 登录成功
        // 1.创建一个会话 保存用户的身份信息
        HttpSession session = req.getSession(true);
        session.setAttribute("user",user);
        // 2.跳转到博客列表页
        resp.sendRedirect("blog_list.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("applocation/json;charset=utf8");
        // 收到get 请求以后,开始判断用户是否已经登录了
        HttpSession session = req.getSession(false);
        if (session == null) {
            // 说明用户没登录 返回一个空的user对象
            User user = new User();
            String respJson = objectMapper.writeValueAsString(user);
            resp.getWriter().write(respJson);
            return;
        }
        // 不为空 返回这个会话中存的用户信息
        User user = (User)session.getAttribute("user");
        // 判断拿到的是不是为空 当服务器重启以后拿到的就是空的
        if (user == null) {
            User temp = new User();
            String respJson = objectMapper.writeValueAsString(temp);
            resp.getWriter().write(respJson);
            return;
        }
        // 成功取出了user对象,直接返回即可
        String respJson = objectMapper.writeValueAsString(user);
        resp.getWriter().write(respJson);
    }
}
