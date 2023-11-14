package com.kgitbank.slimbear.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgitbank.slimbear.dao.InquiryDAO;
import com.kgitbank.slimbear.dao.ProductDAO;
import com.kgitbank.slimbear.dao.ProductDetailDAO;
import com.kgitbank.slimbear.dao.ReviewDAO;
import com.kgitbank.slimbear.dto.InquiryDTO;
import com.kgitbank.slimbear.dto.ProductDTO;
import com.kgitbank.slimbear.dto.ProductDetailDTO;
import com.kgitbank.slimbear.dto.ReviewDTO;

@Service
public class SangyhyukServiceImpl {
	
	/* 리뷰 데이터 SERVICE */
	@Autowired
	private ReviewDAO reviewDAO;
	
	// 모든 리뷰 리스트 받아오기
	public List<ReviewDTO> getReviewList() {
		return reviewDAO.getReviewList();
	}
	
	// 최신순으로 리뷰 리스트 받아오기
	public List<ReviewDTO> getReviewListRecent() {
		return reviewDAO.getReviewListRecent();
	}
	
	// 리뷰 데이터 넣기
	public void insertReview(ReviewDTO review) {
		reviewDAO.insertReview(review);
	}
	
	
	/* 상품 상세 데이터 SERVICE */
	@Autowired
	private ProductDAO prodDAO;
	
	public List<ProductDTO> getHotProductList() {
		return prodDAO.getProductList();
	}
	
	public ProductDTO getProductByUid(long uid) {
		return prodDAO.getProductByUid(uid);
	}
	
	
	/* 상품 옵션 데이터 받아오기 */
	@Autowired
	public ProductDetailDAO prodDetailDAO;
	
	public List<ProductDetailDTO> getProductDetailList(long uid) {
		return prodDetailDAO.getProductDetailList(uid);
	}
	
	/* inquiry 데이터 받아오기 */
	@Autowired
	public InquiryDAO inquiryDAO;
	
	
}