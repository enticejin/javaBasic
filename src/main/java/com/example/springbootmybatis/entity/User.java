/*
 *用户：少爷辉
 *邮箱：www.403367632@qq.com
 *时间：2019/10/13 18:06
 *作者：葛进进
 */
package com.example.springbootmybatis.entity;/*
 *用户：少爷辉
 *邮箱：www.403367632@qq.com
 *时间：2019/10/13 18:06
 *作者：葛进进
 */
public class User {

    private Integer user_id;
    private String user_name;
    private String user_password;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                '}';
    }
}
