package com.kgitbank.slimbear.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kgitbank.slimbear.common.SlimBearUtil;
import com.kgitbank.slimbear.dto.CartDTO;
import com.kgitbank.slimbear.dto.InquiryAnswerDTO;
import com.kgitbank.slimbear.dto.InquiryDTO;
import com.kgitbank.slimbear.dto.ProductDTO;
import com.kgitbank.slimbear.dto.ProductDetailDTO;
import com.kgitbank.slimbear.dto.ReviewDTO;
import com.kgitbank.slimbear.dto.WishDTO;
import com.kgitbank.slimbear.security.SecurityUser;
import com.kgitbank.slimbear.service.SangyhyukServiceImpl;
import com.kgitbank.slimbear.vo.InsertCartVO;

@Controller
public class SanghyukController {
	
	@Autowired
	private SangyhyukServiceImpl sanghService;
	
	/* 상품 상세 데이터 CONTROLLER */
	@RequestMapping("product")
	public String productPage(@RequestParam("p")long productUid, HttpSession session, Model model)  {
		
		// 제품 상세 정보 가져오기
		ProductDTO product = sanghService.getProductByUid(productUid);	
		System.out.println("product: " + product);
		// 잘못된 UID일 경우 메인페이지로
		if(product == null) {
			return "redirct:/";
		}
			
		// 모든 제품 리뷰 가져오기
		List<ReviewDTO> reviewList = sanghService.getReviewList();
		System.out.println("reviewList: " + reviewList);
		
		// 제품의 옵션 가져오기
		List<ProductDetailDTO> productDetailList = sanghService.getProductDetailList(productUid);
		
		// inquiry 가져오기
		List<InquiryDTO> inquiryList = sanghService.getInquiryListByProdUid(productUid);
		System.out.println("inquiryList: " + inquiryList);
		InquiryDTO Uid = inquiryList.get(0);
		long inquiryUid = Uid.getUid();
		System.out.println("inquiryUid: " + inquiryUid);
		
		// inquiryAnswer 가져오기
		List<InquiryAnswerDTO> inquiryAnswerList = sanghService.getInquiryAnswerList(inquiryUid);
		System.out.println("inquiryAnswerList: " + inquiryAnswerList);
		
		// Model에 데이터 추가
		model.addAttribute("product", product); // 상품정보
		model.addAttribute("reviewList", reviewList); // 리뷰리스트
		model.addAttribute("productDetailList", productDetailList); // 상품옵션리스트
		model.addAttribute("inquiryList", inquiryList); // 상품문의 리스트

		return "productInfo"; // .jsp 생략 
	}

	/* 장바구니 상품 추가 */
	@RequestMapping(value="insert/cart/{uid}", method=RequestMethod.POST)
	@ResponseBody
	public String insertInCart(@PathVariable("uid") long uid, InsertCartVO data, Authentication authentication) throws Exception {
		SecurityUser user = (SecurityUser)authentication.getPrincipal(); // 현재 로그인 되어 있는 사용자의 uid를 불러옴
		long mem_uid = user.getUid();
		// 테스트 코드
		//System.out.println("mem_uid: " + mem_uid);
		//System.out.println("data: " + data);

		String prod_code = null;
		ArrayList<HashMap<String, Object>> selectOptionList = data.getSelectOptionList();
		System.out.println("selectOptionList = " + selectOptionList);
		for(HashMap<String, Object> options : selectOptionList) {
			String color = options.get("color").toString();
			String size = options.get("size").toString();
			String cntValue = options.get("cnt").toString();
			int cnt = Integer.parseInt(cntValue);
			prod_code = SlimBearUtil.appendProductCode(mem_uid, color, size);
			
			CartDTO cartDTO = new CartDTO();
			cartDTO.setMem_uid(mem_uid); // Cart 테이블의 mem_uid == Member 테이블의 uid와 매칭 cartDTO 객체에 현재 로그인되어 있는 사용자의 정보 담기
			cartDTO.setProd_code(prod_code); // 상품 코드 설정
			cartDTO.setCnt(cnt);
			
			int isAreadyExited = sanghService.findCartProducts(cartDTO);
			System.out.println("isAreadyExited: " + isAreadyExited);
			if(isAreadyExited>=1) {
				return "already_existed";
			} else {
				sanghService.insertInCart(cartDTO);
				
			}
		}
		return "add_success"; 
	}
	
	/* 위시리스트 추가 */
	@RequestMapping(value="insert/wish/{uid}", method=RequestMethod.POST)
	@ResponseBody
	public String insertInWish(@PathVariable("uid") long uid, Authentication authentication) {
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
		long mem_uid = user.getUid();
		System.out.println("mem_uid: " + mem_uid);
		WishDTO wishDTO = new WishDTO();
		wishDTO.setMem_uid(mem_uid);
		wishDTO.setUid(uid);
		wishDTO.setProd_code("Test3");
		wishDTO.setReg_date(new Timestamp(System.currentTimeMillis()));
		System.out.println(wishDTO);
		int isAreadyExited = sanghService.findWishProduct(wishDTO);
		System.out.println("isAreadyExited: " + isAreadyExited);
		if(isAreadyExited>0) {
			return "already_existed";
		} else {
			sanghService.insertInWish(wishDTO);
		}
		return "add_success";
	}
}