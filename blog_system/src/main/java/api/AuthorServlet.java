package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 26568
 * @date 2023-03-25 22:03
 */
@WebServlet("/author")
public class AuthorServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 拿到这次详细的blogId
        String blogId = req.getParameter("blogId");
        if (blogId == null) {
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("参数非法, 缺少blogId");
            return;
        }
        // 根据 blogId 拿到blog
        BlogDao blogDao = new BlogDao();
        Blog blog = blogDao.selectById(Integer.parseInt(blogId));
        // 判空
        if (blog == null) {
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("没有找到指定博客:"+blogId);
            return;
        }
        // 根据博客查找用户信息
        UserDao userDao = new UserDao();
        User author = userDao.selectById(blog.getUserId());
        String respJson = objectMapper.writeValueAsString(author);
        resp.setContentType("application/json;charset=utf8");
        resp.getWriter().write(respJson);
    }
}
