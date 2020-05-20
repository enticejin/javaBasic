package com.example.shiro.bean;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

/** 
* @version 创建时间：2020年5月20日 上午11:14:58
*/
@Data
@AllArgsConstructor
/*
User类
 */
public class User {
	private String id;
	private String userName;
	private String password;
	/**
	 * 用户对应角色集合
	 */
	private Set<Role> roles;
}
