package com.kgitbank.slimbear.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kgitbank.slimbear.common.SlimBearS3;
import com.kgitbank.slimbear.dao.CartDAO;
import com.kgitbank.slimbear.dao.InquiryAnswerDAO;
import com.kgitbank.slimbear.dao.InquiryDAO;
import com.kgitbank.slimbear.dao.OrderDAO;
import com.kgitbank.slimbear.dao.OrderDetailDAO;
import com.kgitbank.slimbear.dao.ProductDAO;
import com.kgitbank.slimbear.dao.ProductDetailDAO;
import com.kgitbank.slimbear.dao.ReviewDAO;
import com.kgitbank.slimbear.dao.WishDAO;
import com.kgitbank.slimbear.dto.CartDTO;
import com.kgitbank.slimbear.dto.InquiryAnswerDTO;
import com.kgitbank.slimbear.dto.InquiryDTO;
import com.kgitbank.slimbear.dto.MemberDTO;
import com.kgitbank.slimbear.dto.OrderDTO;
import com.kgitbank.slimbear.dto.OrderDetailDTO;
import com.kgitbank.slimbear.dto.ProductDTO;
import com.kgitbank.slimbear.dto.ProductDetailDTO;
import com.kgitbank.slimbear.dto.ReviewDTO;
import com.kgitbank.slimbear.dto.WishDTO;
import com.kgitbank.slimbear.vo.OrderListVO;

@Service
public class SangyhyukServiceImpl {
	
	/* 리뷰 데이터 SERVICE */
	@Autowired
	private ReviewDAO reviewDAO;
	
	// 모든 리뷰 리스트 받아오기
	public List<ReviewDTO> getReviewList() {
		return reviewDAO.getReviewList();
	}
	
	// 리뷰 넣기
	public void insertReview(ReviewDTO review) {
		reviewDAO.insertReview(review);
	}
	
	// 특정 상품 uid의 리뷰 조회
	public List<ReviewDTO> getReviewListByUid(Long uid) {
		
		ArrayList<ReviewDTO> review = new ArrayList<>();
		
		for(ReviewDTO i : review) {
			ReviewDTO list = new ReviewDTO();
			
			list.setProd_code("1:blue:2XL");
			list.setTitle("리뷰 제목 입니다.");
		}
		
		return reviewDAO.getReviewListByUid(uid);
	}
	
	// 최신순으로 리뷰 리스트 받아오기
	public List<ReviewDTO> getReviewListRecent() {
		return reviewDAO.getReviewListRecent();
	}

	// 주문 디테일 정보 (리뷰 쓰는데 필요함)
	@Autowired
	private OrderDetailDAO orderDetailDAO;
	
	public List<OrderDetailDTO> getOrderListByMemberUID(long orderUID) {
		return orderDetailDAO.getOrderListByMemberUID(orderUID);
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
	private ProductDetailDAO prodDetailDAO;
	
	public List<ProductDetailDTO> getProductDetailList(long uid) {
		return prodDetailDAO.getProductDetailList(uid);
	}
	
	/* 상품 색상 옵션 조회 */
	public List<String> getColorOptions(long productUID) {
		return prodDetailDAO.getColorOptions(productUID);
	}
	
	/* 상품 사이즈 옵션 조회 */
	public List<String> getSizeOptions(long productUID) {
		return prodDetailDAO.getSizeOptions(productUID);
	}
	
    // 문의게시판 등록
	public void insertInquiry(InquiryDTO inquiryDTO) {
		inquiryDAO.insertInquiry(inquiryDTO);
	}
	
	/* inquiry 데이터 받아오기 */
	@Autowired
	public InquiryDAO inquiryDAO;
	
	public List<InquiryDTO> getInquiryListByProdUid(Long prodUid) {
		return inquiryDAO.getInquiryListByProdUid(prodUid);
	}
	
	/* inquiryAnswer 데이터 받아오기 */
	@Autowired
	private InquiryAnswerDAO inquiryAnswerDAO;
	
	public List<InquiryAnswerDTO> getInquiryAnswerList(long inquiryUid){
		return inquiryAnswerDAO.getInquiryAnswerList(inquiryUid);
	}
	
	public List<InquiryAnswerDTO> getInquiryAnswerByInqrUid(long inquiryUid) {
		return inquiryAnswerDAO.getInquiryAnswerByInqrUid(inquiryUid);
	}
	
	
	/* 상품 장바구니 */
	@Autowired
	private CartDAO cartDAO;
	
	// 장바구니에 상품 추가
	public void addCartItem(long mem_uid, String prod_code, int cnt) {
		cartDAO.addCartItem(mem_uid, prod_code, cnt);
	}
	
	/* 동일상품 체크 */
	public int findCartProducts(CartDTO cart) {
		return cartDAO.selectCountInCart(cart);
	}
	
	/* 동일상품 갯수 */ 
	public int equalProdCnt(CartDTO cart) {
		return cartDAO.equalProdCnt(cart);
	}
	
	public CartDTO getCartByProdCode(String code) {
		return cartDAO.getCartByProdCode(code);
	}
	
	// 장바구니 목록 조회
	public List<CartDTO> getCartListByMemberUID(long memberUid) {
		MemberDTO info = new MemberDTO();
		info.setUid(memberUid);
		return cartDAO.getCartListByMemberUID(memberUid);
	}
	
	/* 위시리스트에 상품 추가 */
	@Autowired
	private WishDAO wishDAO;
	
	public void insertInWish(WishDTO wish) {
		wishDAO.insertInWish(wish);
	}
	
	/* uid로 위시리스트 불러오기 */
	public List<WishDTO> getWishListByMemberUID(long memberUid) {
		return wishDAO.getWishListByMemberUID(memberUid);
	}
	
	/* 동일상품 체크 */
	public int findWishProduct(WishDTO wish) {
		return wishDAO.selectCountInWish(wish);
	}
	
	//주문내역
	@Autowired
	private OrderDAO orderDAO;
	
	public List<OrderListVO> getOrderListInfo(long memberUID) {
		System.out.println("서비스멤바uid: " + memberUID);
		ArrayList<OrderListVO> list = new ArrayList<>();
		List<OrderDTO> orderlist = orderDAO.getOrderListByMemberUID(memberUID);

		for (OrderDTO i : orderlist) {
			OrderListVO vo = new OrderListVO();

			vo.setOrderDate(i.getOrder_date());
			vo.setOrderStatus(i.getStatus());
			vo.setOrderImage("이미지링크");
			vo.setOrderName("미친 특가상품 지렸다");
			vo.setOrderAmount(i.getTotal_price()); // 개별가격인데 일단 total넣어놈
			vo.setOrderCount(1);

			list.add(vo);
		}
		return list;
	}

}