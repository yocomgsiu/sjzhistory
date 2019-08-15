package com.sjzHistory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sjzHistory.entity.Statistics;
import com.sjzHistory.entity.User;

/**
 * 用户Dao层 继承JapRepository，可以实现一些默认方法，如save/findAll/findOne/delete/count/exists 等
 * Created by Yocomg on 2019/7/16
 */
public interface StatisticsRepository extends JpaRepository<Statistics,Integer> {

	/**
	 * 查寻Statistics分析数据
	 */
	Page<Statistics> findAll(Pageable pageable);
	
//	/**
//	 * 查寻最近的一条数据
//	 */
//	@Query(value = "select s from Statistics s order by s.date DESC limit 1")
//	Statistics findLastOneStatistics();

	/**
	 * 保存Statistics分析数据
	 */
	Statistics saveAndFlush(Statistics statistics);
	
	/**
	 * 查User总数
	 */
	@Query(value = "select count(*) from User")
	long countUsers();
	
	/**
	 * 查被禁User总数
	 */
	@Query(value = "select count(*) from User u where u.isDisabled = true")
	long countDisabledUsers();
	
	/**
	 * 查Article总数
	 */
	@Query(value = "select count(*) from Article")
	long countArticles();

	/**
	 * 查Article总数
	 */
	@Query(value = "select count(*) from Article a where a.isDeleted = true")
	long countDeletedArticles();

}
