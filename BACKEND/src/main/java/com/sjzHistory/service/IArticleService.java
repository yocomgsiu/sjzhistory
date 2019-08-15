package com.sjzHistory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sjzHistory.entity.Article;

/**
 * 用户服务层接口
 * Created by Yocomg on 2019/7/16
 */
public interface IArticleService {
    /**
     * 保存文章
     * @param Article
     * @return 保存成功将统计记录返回，否则返回null
     */
	Article saveArticle(Article article);

	/**
	 * 查所有Article,查找title或者content重包含关键词的
	 */
	Page<Article> findAllArticle(long eventTime,String title, String content, Pageable pageable);
	
}
