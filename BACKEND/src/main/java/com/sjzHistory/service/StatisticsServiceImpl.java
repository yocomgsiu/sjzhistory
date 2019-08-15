package com.sjzHistory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sjzHistory.entity.Statistics;
import com.sjzHistory.repository.StatisticsRepository;

@Service("StatisticsService")
public class StatisticsServiceImpl implements IStatisticsService {
	@Autowired
	StatisticsRepository statisticsRepository;
	
	/**
	 * 每天23点59分30秒计算，先查出当前所有用户和文章数，再查出最近一条记录，两者相差即为增量，然后存储
	 */
	@Scheduled(cron="30 59 23 * * ?")
	public Statistics autoStatistics() {
		Statistics statistics = new Statistics();
		statistics.setDate(System.currentTimeMillis());
		long countUsers = countUsers();
		long countArticles = countArticles();
		long countDisabledUsers =countDisabledUsers();
		long countDeletedArticles = countDeletedArticles();
		statistics.setUserCount(countUsers);
		statistics.setArticleCount(countArticles);
		statistics.setUserDisabledCount(countDisabledUsers);
		statistics.setArticleDeleteCount(countDeletedArticles);
		//date 字段排序无效果，暂且用id，待解决
		Pageable pageable = new PageRequest(0, 5, Direction.DESC, "id"); 
		Page<Statistics> pageStatistics = statisticsRepository.findAll(pageable);
		//查不到说明是第一条，刚开始统计，有多少就是增加多少
		if(null == pageStatistics.getContent() || pageStatistics.getContent().isEmpty()) {
			statistics.setUserIncreaseCount(countUsers);
			statistics.setArticleIncreaseCount(countArticles);
		} else {
			statistics.setUserIncreaseCount(countUsers - pageStatistics.getContent().get(0).getUserCount());
			statistics.setArticleIncreaseCount(countArticles - pageStatistics.getContent().get(0).getArticleCount());
		}
		 
		System.out.println("saveStatistics===="+statistics);
		return saveStatistics(statistics);
	}
	
	
	
	
	
	
	

	/**
	 * 保存统计Statistics
	 * 没有id，程序会自动加；有不重复id，id会自动增加（自行设置无效）；id重复则更新。即saveOrUpdate
	 */
	@Override
	public Statistics saveStatistics(Statistics statistics) {
		return statisticsRepository.saveAndFlush(statistics);
	}

	/**
	 * 查所有统计Statistics
	 */
	@Override
	public Page<Statistics> findAllStatistics(Pageable pageable) {
		return statisticsRepository.findAll(pageable);
	}

	/**
	 * 查User总数
	 */
	@Override
	public long countUsers() {
		return statisticsRepository.countUsers();
	}
	
	/**
	 * 查被禁User总数
	 */
	@Override
	public long countDisabledUsers() {
		return statisticsRepository.countDisabledUsers();
	}

	/**
	 * 查Article总数
	 */
	@Override
	public long countArticles() {
		return statisticsRepository.countArticles();
	}
	
	/**
	 * 查被删除Article总数
	 */
	@Override
	public long countDeletedArticles() {
		return statisticsRepository.countDeletedArticles();
	}

}
