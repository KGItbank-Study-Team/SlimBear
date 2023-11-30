package com.kgitbank.slimbear.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgitbank.slimbear.common.SlimBearUtil;
import com.kgitbank.slimbear.dao.CouponDAO;
import com.kgitbank.slimbear.dao.InquiryDAO;
import com.kgitbank.slimbear.dao.MemberCouponDAO;
import com.kgitbank.slimbear.dao.MemberDAO;
import com.kgitbank.slimbear.dao.MemberOrderAddressDAO;
import com.kgitbank.slimbear.dao.OrderDAO;
import com.kgitbank.slimbear.dao.OrderDetailDAO;
import com.kgitbank.slimbear.dao.ProductDAO;
import com.kgitbank.slimbear.dao.ReviewDAO;
import com.kgitbank.slimbear.dao.WishDAO;
import com.kgitbank.slimbear.dto.CouponDTO;
import com.kgitbank.slimbear.dto.InquiryDTO;
import com.kgitbank.slimbear.dto.MemberCouponDTO;
import com.kgitbank.slimbear.dto.MemberDTO;
import com.kgitbank.slimbear.dto.MemberMileageRecordDTO;
import com.kgitbank.slimbear.dto.MemberOrderAddressDTO;
import com.kgitbank.slimbear.dto.OrderDTO;
import com.kgitbank.slimbear.dto.OrderDetailDTO;
import com.kgitbank.slimbear.dto.ProductDTO;
import com.kgitbank.slimbear.dto.ReviewDTO;
import com.kgitbank.slimbear.dto.WishDTO;
import com.kgitbank.slimbear.vo.AddrVO;
import com.kgitbank.slimbear.vo.CouponVO;
import com.kgitbank.slimbear.vo.MemberBoardVO;
import com.kgitbank.slimbear.vo.MileageVO;
import com.kgitbank.slimbear.vo.ModifyVO;
import com.kgitbank.slimbear.vo.MyPageVO;
import com.kgitbank.slimbear.vo.OrderDetailVO;
import com.kgitbank.slimbear.vo.OrderListVO;
import com.kgitbank.slimbear.vo.WishListVO;
import com.kgitbank.slimbear.vo.reviewListVO;

@Service
public class HunServiceImpl {

	@Autowired
	private MemberDAO memDAO;

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private OrderDetailDAO detailDAO;

	@Autowired
	private CouponDAO couponDAO;

	@Autowired
	private MemberCouponDAO membercouponDAO;

	@Autowired
	private WishDAO wishDAO;
	
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private InquiryDAO inquiryDAO;
	
	@Autowired
	private ReviewDAO reviewDAO;

	@Autowired
	private MemberOrderAddressDAO addressDAO;

	public MyPageVO getMyPageInfo(long uid) {
		MyPageVO vo = new MyPageVO();
		MemberDTO member = memDAO.getMemberByUID(uid);

		vo.setUsername(member.getName());
		vo.setReserve(12000);
		vo.setTotalReserve(17000);
		vo.setUseReserve(5000);
		vo.setTotalOrderM(980000);
		vo.setTotalOrderN(13);
		vo.setCoupon(1);
		vo.setBeforeDeposit(1);
		vo.setPreparingDelivery(2);
		vo.setTransit(3);
		vo.setDelivered(4);
		vo.setCancel(5);
		vo.setTurn(7);

		return vo;
	}

	//주문내역
	public List<OrderListVO> getOrderListInfo(long memberUID) {

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
	
	//주문상세내역
	public List<OrderDetailVO> getOrderDetailInfo(long memberUID) {
		
		ArrayList<OrderDetailVO> list = new ArrayList<>();
		List<OrderDetailDTO> orderlist = detailDAO.getOrderListByMemberUID(memberUID);
		
		for (OrderDetailDTO i : orderlist) {
			OrderDetailVO vo = new OrderDetailVO();
			
			vo.setOrderCount(0);
			
			list.add(vo);
		}
		
		return list;
	}

	//프로필
	public ModifyVO getModifyInfo(long uid) {
		ModifyVO vo = new ModifyVO();
		MemberDTO member = memDAO.getMemberByUID(uid);

		String[] address = SlimBearUtil.splitAddress(member.getAddress());
		String[] phone = SlimBearUtil.splitPhoneNumber(member.getPhone());

		vo.setUsername(member.getName());
		vo.setUserID(member.getId());
		vo.setPostcode(address[0]);
		vo.setDefaultAddr(address[1]);
		vo.setRemainAddr(address[2]);
		vo.setPhone0(phone[0]);
		vo.setPhone1(phone[1]);
		vo.setPhone2(phone[2]);
		vo.setMobile0(phone[0]);
		vo.setMobile1(phone[1]);
		vo.setMobile2(phone[2]);
		vo.setEmail(member.getEmail());

		return vo;
	}

	//적립금
	public MileageVO getMileageInfo(long mem_uid) {
		MileageVO vo = new MileageVO();
		int i = memDAO.getMemberMileageRecordListByMemberUID(mem_uid);
		
		vo.setTotalReserve(5000);
		vo.setReserve(2000);
		vo.setUseReserve(3000);

		return vo;
	}
	
	//적립금 리스트
	public List<MileageVO> getMileageListInfo(long memberUID){
		ArrayList<MileageVO> list = new ArrayList<>();
		List<MemberMileageRecordDTO> membermile = memDAO.getMemberMileageRecordListByMemUID(memberUID);
		
		for(MemberMileageRecordDTO i : membermile) {
			MileageVO vo = new MileageVO();
			
			vo.setOrderDate(i.getDate());
			vo.setAddReserve(i.getMileage());
			vo.setRelatedOrder(i.getOrder_code());
			vo.setSubstance(i.getType());
			
			list.add(vo);
		}
		return list;
	}

	//쿠폰리스트
	public List<CouponVO> getCouponListInfo(long memberUID) {
	    ArrayList<CouponVO> list = new ArrayList<>();
	    List<MemberCouponDTO> membercouponlist = membercouponDAO.getCouponListByMemberUID(memberUID);

	    int index = 1;
	    for (MemberCouponDTO i : membercouponlist) {
	        CouponVO vo = new CouponVO();
	        CouponDTO c = couponDAO.getCouponByUID(i.getCoup_uid());

	        vo.setCoup_uid(i.getUid());
	        vo.setType(c.getType());
	        vo.setValue(c.getValue());

	        vo.setCouponNumber(index++);
	        vo.setCouponName(c.getName());
	        vo.setMinimumAmount(c.getMin_price());
	        if ("PRICE".equals(c.getType())) {
	            vo.setCouponBenefit(c.getValue() + "원 할인");
	        } else if ("PERCENT".equals(c.getType())) {
	            vo.setCouponBenefit(c.getValue() + "% 할인");
	        } else {
	            // 다른 타입에 대한 처리
	        }
	        vo.setCouponPeriod(i.getExpi_date());
	        vo.setUse_date(i.getUse_date());

	        // 사용날짜가 null인 경우에만 추가
	        if (i.getUse_date() == null) {
	            list.add(vo);
	        }
	    }

	    // 새로운 리스트를 만들어 유효한 쿠폰만 추가
	    List<CouponVO> validCoupons = new ArrayList<>();
	    for (CouponVO vo : list) {
	        if (vo.getUse_date() == null) {
	            validCoupons.add(vo);
	        }
	    }

	    return validCoupons;
	}

	//위시리스트
	public List<WishListVO> getWishListInfo(long memberUID) {
		ArrayList<WishListVO> list = new ArrayList<>();
		List<WishDTO> wishlist = wishDAO.getWishListByMemberUID(memberUID);
		ProductDTO p = productDAO.getProductByUid(memberUID);
		
		for (WishDTO i : wishlist) {
			WishListVO vo = new WishListVO();
			// 이거 링크가 다 같은거만 나오네? 해결해야함
			vo.setProductURL("http://localhost:9090/app/product?p=" + p.getUid());
			vo.setProductImage(p.getMain_image());
			vo.setProductName(i.getProd_code());
			vo.setOrderAmount(p.getPrice());
			vo.setOrderDiscount(p.getPrice()-p.getSale_price());
			
			vo.setUid(i.getUid());

			list.add(vo);
		}
		return list;
	}
	
//	위시리스트 삭제
	public void deleteWish(long uid) {
	    wishDAO.deleteWish(uid);
	}

//  문의게시판
	public List<MemberBoardVO> getMemberBoardInfo(String writer) {
		ArrayList<MemberBoardVO> list = new ArrayList<>();
		List<InquiryDTO> boardlist = inquiryDAO.getUserInquiryList(writer);

		for (InquiryDTO i : boardlist) {
			MemberBoardVO vo = new MemberBoardVO();
			if("PRODUCT".equals(i.getType())) {
				vo.setBoardGroup("상품문의");
			} else if("DELIVERY".equals(i.getType())) {
				vo.setBoardGroup("배송문의");
			} else if("DELIVERY_C".equals(i.getType())) {
				vo.setBoardGroup("기타문의");
			} else {
				vo.setBoardGroup("오류다 초비상!");
			}
			
			vo.setBoardTitle(i.getTitle());
			vo.setBoardWriter(i.getWriter_id()); //이름이 없넹
			vo.setBoardDate(i.getReg_date());
			vo.setUid(i.getUid());

			list.add(vo);
		}
		return list;
	}

	//리뷰게시판
	public List<reviewListVO> getReviewListInfoPaging(String userID, int start, int end) {
	    List<ReviewDTO> reviewList = reviewDAO.getReviewListByUserIdPaging(userID, start, end);
	    List<reviewListVO> list = new ArrayList<>();

	    for (ReviewDTO i : reviewList) {
	        reviewListVO vo = new reviewListVO();
	        vo.setImage("대충사진");
	        vo.setTitle(i.getTitle());
	        vo.setContent(i.getContent());
	        vo.setScore(i.getScore());
	        vo.setReg_date(i.getReg_date());
	        list.add(vo);
	    }

	    return list;
	}

	// 추가: 총 리뷰 수를 반환하는 메서드
	public int getTotalReviewCount(String userID) {
	    return reviewDAO.getTotalReviewCount(userID);
	}




	
	//배송지
	public List<AddrVO> getAddrInfo(long memberUID) {
		ArrayList<AddrVO> list = new ArrayList<>();
		List<MemberOrderAddressDTO> addrlist = addressDAO.getAddressListByMemberUID(memberUID);

		for (MemberOrderAddressDTO i : addrlist) {
			AddrVO vo = new AddrVO();
			vo.setAddrName(i.getName());
			vo.setRecipient(i.getRecipient());
			vo.setPhone(i.getNomal_phone());
			vo.setMobile(i.getPhone());

			String[] address = SlimBearUtil.splitAddress(i.getAddress());
			vo.setPostcode(address[0]);
			vo.setDefaultAddr(address[1]);
			vo.setRemainAddr(address[2]);
			vo.setAddr_uid(i.getUid());

			list.add(vo);
		}
		return list;
	}
	
	//배송지 수정
	public AddrVO getAddrFixInfo(long addressUID) {
		AddrVO vo = new AddrVO();
		MemberOrderAddressDTO fix = addressDAO.getAddressByUID(addressUID);
		
		vo.setAddrName(fix.getName());
		vo.setRecipient(fix.getRecipient());
		vo.setAddr_uid(fix.getUid());
		
		String[] phone = SlimBearUtil.splitPhoneNumber(fix.getNomal_phone());
		vo.setPhone1(phone[0]);
		vo.setPhone2(phone[1]);
		vo.setPhone3(phone[2]);
				
		String[] mobile = SlimBearUtil.splitPhoneNumber(fix.getPhone());
		vo.setMobile1(mobile[0]);		
		vo.setMobile2(mobile[1]);		
		vo.setMobile3(mobile[2]);		
		
		String[] address = SlimBearUtil.splitAddress(fix.getAddress());
		vo.setPostcode(address[0]);
		vo.setDefaultAddr(address[1]);
		vo.setRemainAddr(address[2]);
		
		return vo;
	}
	
	
	public void updateAddress(long memberUID, MemberOrderAddressDTO address) {
		addressDAO.updateAddress(address);
	}
	
	public void insertAddress(long memberUID, MemberOrderAddressDTO address) {
		 addressDAO.insertAddress(address);
	}
	
	//배송지 삭제
	public void deleteAddress(long addressUID) {
        addressDAO.deleteAddress(addressUID);
    }


}
