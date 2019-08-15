package com.sjzHistory.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

/**
 * 每篇报道、新闻 Created by Yocomg on 2019/7/21.
 */
@Data
@Entity
@ToString
@Table(name = "article")
public class Article {
	// 主键 自增
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 主键 自增
	// 唯一 非空
	private String title;
	// 内容
	private String content;
	// 事件发生时间
	private long eventTime;
	// 事件发生时间精确程度，绿色:精确到天; 黄色:精确到月; 红色:精确到年
	private String accuracy;

	// 是否被删除（删除是假删除，只是打了标志位，数据无价）
	private boolean isDeleted;

	// 图片1URL
	private String pic1Url;
	// 图片1名
	private String pic1Name;
	// 图片2URL
	private String pic2Url;
	// 图片2名
	private String pic2Name;
	// 图片3URL
	private String pic3Url;
	// 图片4名
	private String pic3Name;

	// 初始作者id
	private long authorId;
	// 初始作者id
	private String authorName;
	// 初始作者id
	private String authorMail;
	// 创建时间
	//	用自1970年1月1日00:00:00 GMT以后time毫秒数设置时间和日期。
	private long createdTime;

	// 修改人者id
	private long modifierId;
	// 修改人者id
	private String modifierName;
	// 修改人者id
	private String modifierMail;
	// 修改时间
	private long modifyTime;

	// 删除人者id
	private long deletedId;
	// 删除人者id
	private String deletedName;
	// 删除人者id
	private String deletedMail;
	// 删除时间
	private long deletedTime;

}
