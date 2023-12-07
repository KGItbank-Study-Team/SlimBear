package com.kgitbank.slimbear.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kgitbank.slimbear.admin.dto.BannerTextDTO;
import com.kgitbank.slimbear.admin.dto.BannerTopDTO;
import com.kgitbank.slimbear.admin.dto.RSYAdminDTO;
import com.kgitbank.slimbear.admin.service.RSYAdminServiceImpl;
import com.kgitbank.slimbear.dto.FaqDTO;
import com.kgitbank.slimbear.dto.InquiryAnswerDTO;
import com.kgitbank.slimbear.dto.InquiryDTO;
import com.kgitbank.slimbear.dto.NoticeDTO;
import com.kgitbank.slimbear.dto.ReviewDTO;
import com.kgitbank.slimbear.service.SangyhyukServiceImpl;
import com.kgitbank.slimbear.service.YangBoardServiceImpl;
import com.kgitbank.slimbear.vo.ReviewVO;

@Controller
public class RSYAdminController {

	@Autowired
	private SangyhyukServiceImpl sanghService;

	@Autowired
	private YangBoardServiceImpl yangService;
	@Autowired
	private RSYAdminServiceImpl rsyAdminService;

	// 리뷰관리페이지
	@RequestMapping("home/board/review")
	public String boardReview(Model model) {

		model.addAttribute("reviewList", sanghService.getReviewList());
		return "tables-board-review";
	}

	@GetMapping("review/list")
	@ResponseBody
	public List<ReviewVO> boardReviewList() {
		List<ReviewVO> reviewList = rsyAdminService.getReviewList();
		return reviewList;
	}

	@RequestMapping("review/getComment")
	@ResponseBody
	public RSYAdminDTO boardReviewGetComment(@RequestParam(name = "reviewUID", required = false) Long reviewUID) {
		RSYAdminDTO comment = rsyAdminService.getComment(reviewUID);
		return comment;
	}

	@RequestMapping("review/addOrUpdateComment")
	@ResponseBody
	public ResponseEntity<Map<String, String>> addOrUpdateComment(@RequestParam Long reviewUID,
			@RequestParam String commentTitle, @RequestParam String commentContent, @RequestParam String mem_id) {
		try {
			rsyAdminService.addOrUpdateComment(reviewUID, commentTitle, commentContent, mem_id);
			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "공지사항이 성공적으로 수정되었습니다.");
			return ResponseEntity.ok(successResponse);
		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", "공지사항 수정 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	// 문의관리페이지
	@RequestMapping("home/board/inquiry")
	public String boardInquiry(Model model) {

		model.addAttribute("inquiryList", rsyAdminService.getInquiryList());
		return "tables-board-inquiry";
	}

	@GetMapping("inquiry/list")
	@ResponseBody
	public List<InquiryDTO> boardInquiryList() {
		List<InquiryDTO> inquiryList = rsyAdminService.getInquiryList();
		return inquiryList;
	}

	@RequestMapping("inquiry/getAnswer")
	@ResponseBody
	public InquiryAnswerDTO boardinquiryGetAnswer(@RequestParam(name = "uid", required = false) Long uid) {
		InquiryAnswerDTO annswer = yangService.getAnswerDetail(uid);
		return annswer;
	}

	@RequestMapping("inquiry/addOrUpdateAnswer")
	@ResponseBody
	public ResponseEntity<Map<String, String>> addOrUpdateAnswer(@RequestParam Long inqr_uid,
			@RequestParam String answerTitle, @RequestParam String answerContent, @RequestParam String mem_id) {
		Date reg_date = new Date();
		try {
			System.out.println(inqr_uid);
			System.out.println(answerTitle);
			System.out.println(answerContent);
			System.out.println(mem_id);
			System.out.println(reg_date);
			rsyAdminService.addOrUpdateAnswer(inqr_uid, answerTitle, answerContent, mem_id, reg_date);
			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "공지사항이 성공적으로 수정되었습니다.");
			return ResponseEntity.ok(successResponse);
		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", "공지사항 수정 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

//    @PostMapping("inquiry/hideData")
//    @ResponseBody
//    public ResponseEntity<String> hideData(@RequestBody InquiryDTO data) {
//        // 클라이언트로부터 전달받은 데이터를 이용하여 숨김 작업 수행
//        rsyAdminService.hideData(data);
//        return ResponseEntity.ok("숨김이 완료되었습니다.");
//    }

	@DeleteMapping("inquiry/deleteData")
	@ResponseBody
	public ResponseEntity<String> deleteData(@RequestBody InquiryDTO data) {
		// 클라이언트로부터 전달받은 데이터를 이용하여 삭제 수행
		System.out.println(data);
		rsyAdminService.deleteInquiry(data);
		return ResponseEntity.ok("삭제가 완료되었습니다.");
	}

	@DeleteMapping("review/deleteData")
	@ResponseBody
	public ResponseEntity<String> deleteData(@RequestBody ReviewDTO data) {
		// 클라이언트로부터 전달받은 데이터를 이용하여 삭제 수행
		System.out.println(data);
		rsyAdminService.deleteReview(data);
		return ResponseEntity.ok("삭제가 완료되었습니다.");
	}

	// 공지사항관리
	@RequestMapping("home/board/notice")
	public String boardNotice(Model model) {

		model.addAttribute("noticeList", rsyAdminService.getNoticeList());
		return "tables-board-notice";
	}

	@GetMapping("notice/list")
	@ResponseBody
	public List<NoticeDTO> boardNoticeList() {
		List<NoticeDTO> noticeList = rsyAdminService.getNoticeList();
		return noticeList;
	}

	@RequestMapping("notice/getNotice")
	@ResponseBody
	public NoticeDTO getNotice(Long uid) {
		NoticeDTO notice = rsyAdminService.getNotice(uid);
		return notice;
	}

	@RequestMapping("notice/addNotice")
	@ResponseBody
	public ResponseEntity<Map<String, String>> addNotice(@RequestParam String title, @RequestParam String content,
			@RequestParam int priority, @RequestParam String type,
			@RequestParam(name = "mainImage", required = false) MultipartFile mainImage) {

		try {
			System.out.println(title);
			System.out.println(content);
			System.out.println(priority);
			System.out.println(type);
			System.out.println(mainImage);
			rsyAdminService.addNotice(title, content, priority, type, mainImage);
			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "공지사항이 성공적으로 수정되었습니다.");
			return ResponseEntity.ok(successResponse);
		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", "공지사항 수정 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@DeleteMapping("notice/deleteData")
	@ResponseBody
	public ResponseEntity<String> deleteData(@RequestBody NoticeDTO data) {
		// 클라이언트로부터 전달받은 데이터를 이용하여 삭제 수행
		System.out.println(data);
		rsyAdminService.deleteNotice(data);
		return ResponseEntity.ok("삭제가 완료되었습니다.");
	}

	@RequestMapping("notice/editNotice")
	@ResponseBody
	public ResponseEntity<Map<String, String>> editNotice(@RequestParam Long uid, @RequestParam String title,
			@RequestParam String content, @RequestParam int priority, @RequestParam String type,
			@RequestParam(name = "main_image", required = false) MultipartFile main_image) {

		try {
			System.out.println(uid);
			System.out.println(title);
			System.out.println(content);
			System.out.println(priority);
			System.out.println(type);
			System.out.println(main_image);

			rsyAdminService.updateNotice(uid, title, content, priority, type, main_image);

			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "공지사항이 성공적으로 수정되었습니다.");
			return ResponseEntity.ok(successResponse);

		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", "공지사항 수정 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	// faq관리
	@RequestMapping("home/board/faq")
	public String boardFaq(Model model) {

		model.addAttribute("faqList", rsyAdminService.getFaqList());
		return "tables-board-faq";
	}

	@GetMapping("faq/list")
	@ResponseBody
	public List<FaqDTO> boardFaqList() {
		List<FaqDTO> faqList = rsyAdminService.getFaqList();
		return faqList;
	}

	@DeleteMapping("faq/deleteData")
	@ResponseBody
	public ResponseEntity<String> deleteData(@RequestBody FaqDTO data) {
		// 클라이언트로부터 전달받은 데이터를 이용하여 삭제 수행
		System.out.println(data);
		rsyAdminService.deleteFaq(data);
		return ResponseEntity.ok("삭제가 완료되었습니다.");
	}

	@RequestMapping("faq/getFaq")
	@ResponseBody
	public FaqDTO getFaq(Long uid) {
		FaqDTO faq = rsyAdminService.getFaq(uid);
		return faq;
	}

	@RequestMapping("faq/addFaq")
	@ResponseBody
	public ResponseEntity<Map<String, String>> addFaq(@RequestParam String question, @RequestParam String answer,
			@RequestParam String type) {

		try {
			System.out.println(question);
			System.out.println(answer);
			System.out.println(type);

			rsyAdminService.addFaq(question, answer, type);

			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "FAQ가 성공적으로 등록되었습니다.");
			return ResponseEntity.ok(successResponse);

		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", "FAQ 등록 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@RequestMapping("faq/editFaq")
	@ResponseBody
	public ResponseEntity<Map<String, String>> editFaq(@RequestParam Long uid, @RequestParam String question,
			@RequestParam String answer, @RequestParam String type) {

		try {
			System.out.println(uid);
			System.out.println(question);
			System.out.println(answer);
			System.out.println(type);

			rsyAdminService.updateFaq(uid, question, answer, type);

			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "FAQ가 성공적으로 수정되었습니다.");
			return ResponseEntity.ok(successResponse);

		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", "FAQ 수정 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	// 배너이미지관리페이지
	@RequestMapping("home/board/bannerTop")
	public String boardBannerTop(Model model) {

		model.addAttribute("bannerTopList", rsyAdminService.getBannerTopList());
		return "bannerTop";
	}

	@GetMapping("bannerTop/list")
	@ResponseBody
	public List<BannerTopDTO> boardBannerTopList() {
		List<BannerTopDTO> bannerTopList = rsyAdminService.getBannerTopList();
		return bannerTopList;
	}

	@RequestMapping("bannerTop/getBannerTop")
	@ResponseBody
	public BannerTopDTO getBannerTop(Long uid) {
		BannerTopDTO bannerTop = rsyAdminService.getBannerTop(uid);
		return bannerTop;
	}

	@RequestMapping("bannerTop/editBannerTop")
	@ResponseBody
	public ResponseEntity<Map<String, String>> editBannerTop(@RequestParam Long uid, @RequestParam MultipartFile image,
			@RequestParam Long prod_uid) {

		try {
			System.out.println(uid);
			System.out.println(image);
			System.out.println(prod_uid);

			rsyAdminService.updateBannerTop(uid, image, prod_uid);

			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "배너 이미지가 성공적으로 수정되었습니다.");
			return ResponseEntity.ok(successResponse);

		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", " 수정 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	// 배너텍스트관리페이지
	@RequestMapping("home/board/bannerText")
	public String boardBannerText(Model model) {

		model.addAttribute("bannerTextList", rsyAdminService.getBannerTextList());
		return "bannerText";
	}

	@GetMapping("bannerText/list")
	@ResponseBody
	public List<BannerTextDTO> boardBannerTextList() {
		List<BannerTextDTO> bannerTextList = rsyAdminService.getBannerTextList();
		return bannerTextList;
	}

	@RequestMapping("bannerText/getBannerText")
	@ResponseBody
	public BannerTextDTO getBannerText(Long uid) {
		BannerTextDTO bannerText = rsyAdminService.getBannerText(uid);
		return bannerText;
	}

	@RequestMapping("bannerText/editBannerText")
	@ResponseBody
	public ResponseEntity<Map<String, String>> editBannerText(@RequestParam Long uid, @RequestParam String bannerText) {

		try {
			System.out.println(uid);
			System.out.println(bannerText);

			rsyAdminService.updateBannerText(uid, bannerText);

			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "배너 이미지가 성공적으로 수정되었습니다.");
			return ResponseEntity.ok(successResponse);

		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", " 수정 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@DeleteMapping("top/deleteData")
	@ResponseBody
	public ResponseEntity<Map<String, String>> deleteData(@RequestBody BannerTopDTO data) {
		try {
			rsyAdminService.deleteTop(data);

			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "배너 이미지가 성공적으로 삭제되었습니다.");
			return ResponseEntity.ok(successResponse);

		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", " 수정 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@DeleteMapping("text/deleteData")
	@ResponseBody
	public ResponseEntity<Map<String,String>> deleteData(@RequestBody BannerTextDTO data) {
		try {
			rsyAdminService.deleteText(data);

			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "배너 텍스트가 성공적으로 삭제되었습니다.");
			return ResponseEntity.ok(successResponse);

		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", " 삭제 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@RequestMapping("top/addTop")
	@ResponseBody
	public ResponseEntity<Map<String, String>> addTop(@RequestBody MultipartFile image, @RequestBody Long prod_uid) {

		try {
			rsyAdminService.addTop(image, prod_uid);

			// 성공일 경우
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "배너이미지가 성공적으로 등록되었습니다.");
			return ResponseEntity.ok(successResponse);

		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", "배너이미지가 등록 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@RequestMapping("text/addText")
	@ResponseBody
	public ResponseEntity<Map<String, String>> addText(@RequestParam String text) {

		try {
			rsyAdminService.addText(text);

			// 성공일 경우
			System.out.println(text);
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("status", "SUCCESS");
			successResponse.put("message", "배너텍스트가 성공적으로 등록되었습니다.");
			return ResponseEntity.ok(successResponse);

		} catch (Exception e) {
			// 실패일 경우
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "ERROR");
			errorResponse.put("message", "배너텍스트 등록 중에 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}
}
