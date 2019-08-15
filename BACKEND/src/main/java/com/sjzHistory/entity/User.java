package com.sjzHistory.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

/**
 * 用户表实体类 Entity 代表这是实体类，要交给Hibernate管理
 * 
 * @Size @NotNull 都是validation框架的注解，更多验证请参见网络资料 Created by Yocomg on 2019/7/20.
 */
@Data
@Entity
@ToString
@Table(name="user")
public class User {
	// 主键 自增
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; // 主键 自增
	// 唯一 非空
	private String name;
	//密码 
	private String password;
	//邮箱 
	private String email;
	//验证码 
	private String verifyCode;
	//邮箱是否验证过 
	private boolean isMailVerifed;
	//用户是否被禁用
	private boolean isDisabled;
	//注册时间
	private long registerDate;
	//贡献数
	private int contribution;

}
