package api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 26568
 * @date 2023-03-26 9:05
 */
@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 拿到之前创建的会话
        HttpSession session = req.getSession();
        if (session == null) {
            // 未登录 直接提示出错
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("当前未登录");
            return;
        }
        // 删除键值对
        session.removeAttribute("user");
        // 跳转到登录页
        resp.sendRedirect("blog.html");
    }
}
