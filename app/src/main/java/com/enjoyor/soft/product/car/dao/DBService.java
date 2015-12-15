package com.enjoyor.soft.product.car.dao;

import java.util.List;

/**
 * Copyright (c) by hutuanle
 * All right reserved.
 * email:354777464@qq.com
 * Create Author: 胡团乐
 * Create Date: 2013-6-26下午07:14:31
 * File Name: 数据库接口
 * Last version: 1.0
 * Last Update Date: 2013-6-26
 * Change Log:
 */
public interface DBService<T> {
	
	//单个查询
	public T query(String beanCode);
	//批量查询
	public List<T> queryList();
	//插入
	public void insert(T cic);
	//更新
	public void update(T cic);
}
