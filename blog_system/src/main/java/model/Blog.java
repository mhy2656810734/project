package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author 26568
 * @date 2023-03-21 10:30
 */
public class Blog {
    private int blogId;
    private String title;
    private String content;
    private Timestamp postTime;
    private int userId;

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public Timestamp getPostTime() {
//        return postTime;
//    }

    public String getPostTime() {
        // 把时间戳转化成格式化时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(postTime);
    }
    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
