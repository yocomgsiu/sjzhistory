package com.sjzHistory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjzHistory.entity.Statistics;
import com.sjzHistory.service.IStatisticsService;
import com.sjzHistory.utils.ResultGenerator;

/**
 * 统计数据
 * . @RestController 该类下所有返回值默认以json格式进行返回
 * . @RequestMapping 匹配url地址 /statistics
 * Created by Yocomg on 2019/7/30
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ResultGenerator generator;
    
    private final IStatisticsService satisticsServices;
    
    @Autowired  //自动装配
    public StatisticsController(IStatisticsService satisticsServices, ResultGenerator generator) {
        this.generator = generator;
        this.satisticsServices = satisticsServices;
    }
    
    
    
    
	
	@RequestMapping(value = "/test",method = RequestMethod.GET)
	public void test(@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.ASC) 
    Pageable pageable) {

		long articlesCount = satisticsServices.countArticles();
		System.out.println("articlesCount===="+articlesCount);		
		
		long usersCount = satisticsServices.countUsers();
		System.out.println("usersCount===="+usersCount);
		
		Page<Statistics> staPage = satisticsServices.findAllStatistics(pageable);
		System.out.println("staPage===="+staPage.getContent());
		
		Statistics statistics = new Statistics();
		statistics.setId(2346);
		statistics.setDate(12);
		statistics.setUserCount(3);
		statistics.setUserIncreaseCount(1);
		statistics.setArticleCount(33);
		statistics.setArticleIncreaseCount(4);
		Statistics saveStatistics = satisticsServices.saveStatistics(statistics);
		Statistics autoStatistics = satisticsServices.autoStatistics();
		
		System.out.println("saveStatistics===="+saveStatistics);
		
	}

}
