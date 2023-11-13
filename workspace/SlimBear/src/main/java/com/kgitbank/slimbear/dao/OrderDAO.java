package com.kgitbank.slimbear.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kgitbank.slimbear.dto.MemberDTO;
import com.kgitbank.slimbear.dto.OrderDTO;

@Repository
public class OrderDAO {
	
	@Autowired
	protected SqlSessionTemplate template;
	
	public List<OrderDTO> getOrderListByMemberUID(long memberUID){
		MemberDTO info = new MemberDTO();
		info.setUid(memberUID);
		return template.selectList("com.slimbear.mapper.Order.SELECT_ORDER_LIST_BY_MEMBER_UID", info);
	}
	
	public int insertOrder(OrderDTO order) {
		return template.insert("com.slimbear.mapper.Order.INSERT_ORDER", order);
	}
	
	public int updateOrder(OrderDTO order) {
		return template.update("com.slimbear.mapper.Order.UPDATE_ORDER", order);
	}
	
	public int deleteOrder(OrderDTO order) {
		return template.delete("com.slimbear.mapper.Order.DELETE_ORDER", order);
	}
}
