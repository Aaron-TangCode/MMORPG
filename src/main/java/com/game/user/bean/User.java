package com.game.user.bean;

/**
 * @ClassName User
 * @Description 用户User实体类
 * @Author DELL
 * @Date 2019/5/2716:47
 * @Version 1.0
 */
public class User {
    /**
     * 用户Id
     */
    private Integer id;
    /**
     * 用户名username
     */
    private String username;
    /**
     * 密码password
     */
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
