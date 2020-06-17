package com.example.jpa.model;

import javax.persistence.*;

@Entity// @Entity: 实体类, 必须
// @Table: 对应数据库中的表, 必须, name=表名, Indexes是声明表里的索引, columnList是索引的列, 同时声明此索引列是否唯一, 默认false
@Table(name = "sys_user",indexes = {@Index(name = "id", columnList = "id",unique = true),
                                    @Index(name = "name", columnList = "name", unique = true)})
public class SysUser {
    @Id // @Id: 指明id列, 必须
    @GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue： 表明是否自动生成, 必须, strategy也是必写, 指明主键生成策略, 默认是Oracle
    private long id;

    @Column(name = "name", nullable = false)// @Column： 对应数据库列名,可选, nullable 是否可以为空, 默认true
    private String name;

    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
