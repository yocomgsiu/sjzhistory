package com.sjzHistory.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjzHistory.entity.RespResult;
import com.sjzHistory.entity.Statistics;
import com.sjzHistory.entity.User;
import com.sjzHistory.service.IStatisticsService;
import com.sjzHistory.service.IUserService;
import com.sjzHistory.utils.ResultGenerator;

/**
 * 用户controller
 * . @RestController 该类下所有返回值默认以json格式进行返回
 * . @RequestMapping 匹配url地址 /user
 * Created by Yocomg on 2019/7/16
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final IUserService userService;

    private final ResultGenerator generator;
    
    private final IStatisticsService satisticsServices;

    @Autowired  //自动装配
    public UserController(IUserService userService, IStatisticsService satisticsServices, ResultGenerator generator) {
        this.userService = userService;
        this.generator = generator;
        this.satisticsServices = satisticsServices;
    }

    /**
     * 第一版本没有登记，更没有注册，发文章仅需要留下邮箱和昵称(无需校验)。
     * 第二版才有邮箱和昵称通过校验.
     * 没有注册，登记时仅需要留下邮箱和昵称通过校验。本来就没啥用户。
     */
    @RequestMapping("/register")
    public RespResult register(User user) {
    	if(user.getEmail().isEmpty()|| null == user.getEmail()) {
    		return generator.getFailResult("请填写邮箱");
    	}
    	if(user.getName().isEmpty()|| null == user.getName()) {
    		return generator.getFailResult("请填写昵称");
    	}
//    	没有注册，登记时仅需要留下邮箱和昵称。
//    	if(user.getPassword().isEmpty()|| null == user.getPassword()) {
//    		return generator.getFailResult("请填写密码");
//    	}
    	
        return generator.getSuccessResult("登记成功",userService.saveUser(user));
    }

    /**
     * 用户登录，
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public RespResult login(@NotNull(message = "用户名不能为空") String name,@NotNull(message = "密码不能为空") String password, HttpSession session) {
        User user = userService.checkLogin(name, password);
        if(user != null) {
            //储存到session中
            session.setAttribute("user",user);
            return generator.getSuccessResult("登陆成功",user);
        }
        return generator.getFailResult("用户名/密码错误");
    }

	@RequestMapping(value = "/login2",method = RequestMethod.POST)
    public ResponseEntity<User> login2(@NotNull String name,@NotNull String password, HttpSession session) {
    	User user = userService.checkLogin(name, password);
    	if(user != null) {
    		log.info("find user {} and login",user.toString());
    		//储存到session中
    		session.setAttribute("user", user);
    		return new ResponseEntity<>(user, HttpStatus.OK);
    	}
    	return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }
	
	@RequestMapping(value = "/test",method = RequestMethod.GET)
	public void test(@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.ASC) 
    Pageable pageable) {

		
		
		
	}

	
	
	
}
