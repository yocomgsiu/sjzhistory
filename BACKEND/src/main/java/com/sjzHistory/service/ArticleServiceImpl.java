package com.sjzHistory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sjzHistory.entity.Article;
import com.sjzHistory.entity.Statistics;
import com.sjzHistory.repository.ArticleRepository;

/**
 * 用户服务层实现类
 * Created by Yocomg on 2019/7/16
 */
@Service("ArticleService")
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    ArticleRepository articleRepository;
    
	@Override
	public Article saveArticle(Article article) {
		return articleRepository.saveAndFlush(article);
	}

	@Override
	public Page<Article> findAllArticle(long timeKey, String titleKey, String contentKey, Pageable pageable) {
		return articleRepository.findAllArticles(timeKey,titleKey, contentKey, pageable);
	}

}
