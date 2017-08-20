package fastjsondemo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by guojun.wang on 2017/6/6.
 */
public class Person {
    private int id;
    private String name;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    private Date workBeginning;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getWorkBeginning() {
        return workBeginning;
    }

    public void setWorkBeginning(Date workBeginning) {
        this.workBeginning = workBeginning;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", workBeginning=" + workBeginning +
                '}';
    }
}
