package data;

/**
 * Created by Administrator on 2017/9/25.
 */
public class ComputerInfo {
    private String name = "wgjpc";
    private String description = "asdfasdfadsfasdf";

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    private byte[] data = new byte[500 * 1024 * 1024];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "data.ComputerInfo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
