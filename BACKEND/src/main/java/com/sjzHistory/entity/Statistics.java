package com.sjzHistory.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

/**
 * 每天凌晨的统计数据
 * Created by Yocomg on 2019/7/20.
 */
@Data
@Entity
@ToString
@Table(name="statistics")
public class Statistics {
	// 主键 自增
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; // 主键 自增
	// 唯一 非空
	private long date;
	// 用户总数
	private long userCount;
	// 今日新增用户数
	private long userIncreaseCount;
	// 总被禁 用户数
	private long userDisabledCount;
	// 文章总数
	private long articleCount;
	// 今日新增文章数
	private long articleIncreaseCount;
	// 总被删除文章
	private long articleDeleteCount;

}
