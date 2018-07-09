package tech.soulcoder.dao.pojo.generator;

import java.io.Serializable;

public class SoulUserDTO implements Serializable {
    private String id;

    private String pwd;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }
}