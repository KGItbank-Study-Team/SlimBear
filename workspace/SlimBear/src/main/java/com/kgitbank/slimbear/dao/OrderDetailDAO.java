package com.kgitbank.slimbear.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailDAO {
	
	@Autowired
	protected SqlSessionTemplate template;
}
