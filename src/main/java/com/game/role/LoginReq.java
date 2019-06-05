package com.game.role;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName LoginReq
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/3114:35
 * @Version 1.0
 */
public class LoginReq {
    private String username;

    private String password;

    public static void main(String[] args) {
        LoginReq req = JSON.parseObject("{\"username\":\"123\",\"password\":\"123\"}",LoginReq.class);
        System.out.println(req);
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

    @Override
    public String toString() {
        return "LoginReq{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
