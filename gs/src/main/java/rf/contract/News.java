package rf.contract;

/**
 * Created by wangguojun01 on 2017/12/4.
 */
public class News {
    private int newsID;
    private String title;

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsID=" + newsID +
                ", title='" + title + '\'' +
                '}';
    }
}
