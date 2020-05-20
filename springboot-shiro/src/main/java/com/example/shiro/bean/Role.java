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
Role类
 */
public class Role {
	private String id;
	private String roleName;
	/**
	 * 角色对应权限集合
	 */
	private Set<Permissions> permissions;
}
