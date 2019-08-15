package com.sjzHistory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjzHistory.entity.User;
import com.sjzHistory.repository.UserRepository;

/**
 * 用户服务层实现类
 * Created by Yocomg on 2019/7/16
 */
@Service("UserService")
public class UserServiceImpl implements IUserService{
    @Autowired
    UserRepository userRepository;
    
    /**
     * 通过id查找用户
     * @param id
     */
    @Override
    public User findUserById(long id) {
    	return userRepository.findFirstById(id);
    }
    
    /**
     * 通过email查找用户,发表文章、注册的时候会查看邮箱有没有被占用
     * @param email
     */
    @Override
    public User findUserByEmail(String email) {
    	return userRepository.findFirstByEmail(email);
    }

    /**
     * 保存（登记、注册）用户
	 * @param User
     */
    @Override
    public User saveUser(User user) {
    	user.setDisabled(false);
    	user.setMailVerifed(false);
    	user.setRegisterDate(System.currentTimeMillis());
        return userRepository.save(user);
    }

    /**
     * 检查用户名密码是否正确，第一版用不上，因为连注册都没有
	 * @param name     用户名
	 * @param password 密码
     */
    @Override
    public User checkLogin(String name, String password) {
        return userRepository.findFirstByNameAndPassword(name, password);
    }
}
