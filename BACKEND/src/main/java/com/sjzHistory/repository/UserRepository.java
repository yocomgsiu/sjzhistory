package com.sjzHistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sjzHistory.entity.User;

/**
 * 用户Dao层
 * 继承JapRepository，可以实现一些默认方法，如save/findAll/findOne/delete/count/exists 等
 * Created by Yocomg on 2019/7/16
 */
public interface UserRepository extends JpaRepository<User,Integer> {

	/**
	 * 按用户名——密码查找
	 * @param name
	 * @param password
	 * @return
	 */
    User findFirstByNameAndPassword(String name, String password);
    
    
    /**
     * 按id查找
     * @param id
     * @return
     */
    User findFirstById(long id);
    

    /**
     * 按email查找
     * @param email
     * @return
     */
	User findFirstByEmail(String email);

}
