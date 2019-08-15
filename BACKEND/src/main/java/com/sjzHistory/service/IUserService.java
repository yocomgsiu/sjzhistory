package com.sjzHistory.service;

import com.sjzHistory.entity.User;

/**
 * 用户服务层接口 Created by Yocomg on 2019/7/16
 */
public interface IUserService {
	/**
	 * 保存（登记、注册）用户
	 * 
	 * @param User
	 * @return 注册成功将用户信息返回，否则返回null
	 */
	User saveUser(User User);

	/**
	 * 按id查找
	 * 
	 * @param id
	 * @return 成功将用户信息返回，否则返回null
	 */
	User findUserById(long id);

	/**
	 * 检查用户名密码是否正确
	 * 
	 * @param name     用户名
	 * @param password 密码
	 * @return 验证通过则将用户信息返回，否则返回null
	 */
	User checkLogin(String name, String password);

	/**
	 * 通过email查找用户,发表文章、注册的时候会查看邮箱有没有被占用
	 * 
	 * @param email
	 * @return 成功将用户信息返回，否则返回null
	 */
	User findUserByEmail(String email);

}
