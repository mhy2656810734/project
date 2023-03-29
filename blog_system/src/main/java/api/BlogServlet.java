package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 26568
 * @date 2023-03-21 12:52
 */
@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BlogDao blogDao = new BlogDao();
        // 从get请求读取字符串 判断是获取博客列表页还是博客详情页
        String blogId = req.getParameter("blogId");
        if (blogId == null) {
            // 获取博客列表页
            // 拿到数据库所有的博客
            List<Blog> blogs = blogDao.selectAll();
            // 需要将 blogs 转成符合要求的 json 格式的字符串
            String respJson = objectMapper.writeValueAsString(blogs);
            resp.setContentType("application/json;charset=utf8");
            resp.getWriter().write(respJson);
        } else {
            // 获取的是博客详情页 找到blogId 对应的博客
            Blog blog = blogDao.selectById(Integer.parseInt(blogId));
            if (blog == null) {
                System.out.println("当前 blogId = "+blogId+"对应的博客不存在!");
            }
            // 然后将这个博客写回到响应
            resp.setContentType("application/json;charset=utf8");
            String respJson = objectMapper.writeValueAsString(blog);
            resp.getWriter().write(respJson);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("当前未登录,无法发布博客");
            return;
        }
        // 获取当前用户
        User user = (User)session.getAttribute("user");
        if (user == null) {
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("当前未登录,无法发布博客");
            return;
        }
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if (title == null || "".equals(title) || content == null || "".equals(content)) {
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("当前提交数据有误!标题或者正文有误");
            return;
        }
        // 构造博客 插入数据库
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setUserId(user.getUserId());
        // 发布时间 在数据库/idea中生成都行
        blog.setPostTime(new Timestamp(System.currentTimeMillis()));
        BlogDao blogDao = new BlogDao();
        blogDao.add(blog);
        // 跳转到博客详情页
        resp.sendRedirect("blog_list.html");
    }
}
