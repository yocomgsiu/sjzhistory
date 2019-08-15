package com.sjzHistory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjzHistory.entity.Article;
import com.sjzHistory.entity.RespResult;
import com.sjzHistory.entity.User;
import com.sjzHistory.service.IArticleService;
import com.sjzHistory.service.IUserService;
import com.sjzHistory.utils.ResultGenerator;

/**
 * 用户控制层 . @RestController 该类下所有返回值默认以json格式进行返回 . @RequestMapping 匹配url地址
 * /article . @Validated 代表该类启用参数验证，通过添加注解可以验证参数 Created by Yocomg on 2019/7/26
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private final IUserService userService;

	private final ResultGenerator generator;

	private final IArticleService articleService;

	@Autowired // 自动装配
	public ArticleController(IUserService userService, IArticleService articleService, ResultGenerator generator) {
		this.userService = userService;
		this.generator = generator;
		this.articleService = articleService;
	}

	/**
	 * 发表文章,
	 * 第一版无需登录，留下昵称和邮箱就行，程序自动判断邮箱是否登记
	 * （存在缺陷是邮箱如跟别人的重复，看不出来是谁的，前端需要提醒）
	 */
	@RequestMapping(value = "/record", method = RequestMethod.POST)
	public RespResult recode(Article article) {
		if (article.getTitle().isEmpty() || null == article.getTitle()) {
			return generator.getFailResult("请填写标题");
		}
		if (article.getContent().isEmpty() || null == article.getContent()) {
			return generator.getFailResult("请填写内容");
		}
		if (0 == article.getEventTime()) {
			return generator.getFailResult("请填写时间");
		}
		if (article.getAuthorName().isEmpty() || null == article.getAuthorName()) {
			return generator.getFailResult("请填写昵称");
		}
		if (article.getAuthorMail().isEmpty() || null == article.getAuthorMail()) {
			return generator.getFailResult("请留下邮箱");
		} else {
			// 第一版业务需要，很多逻辑放在controller，以后移到service
			// 有邮箱，则查看该用户是否注登记没有
			User finduserUser = userService.findUserByEmail(article.getAuthorMail());
			if (null != finduserUser) {
				// 用户已经有的就直接设作者id
				article.setAuthorId(finduserUser.getId());
			} else {
				// 没有的就先把用户保存至在数据库，再设作者id
				User newUser = new User();
				newUser.setEmail(article.getAuthorMail());
				newUser.setName(article.getAuthorName());
				newUser = userService.saveUser(newUser);
				article.setAuthorId(newUser.getId());
			}
		}
		article.setDeleted(false);
		article.setCreatedTime(System.currentTimeMillis());
		//暂时没有dto，但也不能直接返回article（暴露太多），暂时以是否返回null为判断成功条件
		Article savedArticle = articleService.saveArticle(article);
		
		if (null!= savedArticle) {			
			return generator.getSuccessResult();
		} else {
			return generator.getFailResult("保存失败");
		}
	}

	/**
	 * 修改文章，
	 * 第一版本没有此功能，待有登陆后，管理员（专业用户）可修改
	 */
	@RequestMapping(value = "/admin/setdelete", method = RequestMethod.POST)
	public RespResult delete(Article article) {
		return null;
	}
	
	/**
	 * 删除文章，不是真从硬盘删除，仅仅打个标记
	 * 第一版本没有此功能，待有登陆后，管理员（专业用户）可删除
	 */
	@RequestMapping(value = "/pro/modifyArticle", method = RequestMethod.POST)
	public RespResult modify(Long id) {
		return null;
	}

	/**
	 * get 时间轴数据，关键词是差标题或者内容存在的,不是bug
	 * 
	 */
	@RequestMapping(value = "/timeline", method = RequestMethod.GET)
	public RespResult timeline(Long timeKey, String keyWord, Pageable pageable) {
		if (0 == timeKey || null == timeKey) {
			timeKey = System.currentTimeMillis();
		}

		return generator.getSuccessResult("成功", articleService.findAllArticle(timeKey, keyWord, keyWord, pageable));
	}

}
