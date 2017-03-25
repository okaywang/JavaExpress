package Mybatis;

/**
 * foo...Created by wgj on 2017/3/25.
 */
public class ResumeEntity {
    private int id;
    private String version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ResumeEntity{" +
                "id=" + id +
                ", version='" + version + '\'' +
                '}';
    }
}
