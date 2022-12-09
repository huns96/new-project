package com.demo.controller.customer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.domain.book.BookDto;
import com.demo.domain.customer.NoticeDto;
import com.demo.domain.customer.PageInfo;
import com.demo.domain.customer.QuestionDto;
import com.demo.service.book.BookService;
import com.demo.service.customer.NoticeService;
import com.demo.service.customer.QuestionService;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private NoticeService service;

	@Autowired
	private QuestionService questService;

	@Autowired
	private BookService bookService;

	@GetMapping("noticeRegister")
	public void ad_notice() {

	}

	/* 공지사항 추가 */
	@PostMapping("noticeRegister")
	public String noticeInsert(NoticeDto notice) {

		service.noticeRegister(notice);

		return "redirect:/admin/notice";

	}

	/* 공지사항 리스트 보여주기 */
	@GetMapping("notice")
	public void notice(@RequestParam(name = "page", defaultValue = "1") int page, PageInfo pageInfo, Model model) {
		// business logic
		List<NoticeDto> list = service.listNotice(page, pageInfo);

		// add attribute
		model.addAttribute("noticeList", list);
	}

	/* 공지사항 수정하기 */
	@GetMapping("modify")
	public void modify(int n_id, Model model) {
		NoticeDto notice = service.get(n_id);

		model.addAttribute("notice", notice);

	}

	@PostMapping("modify")
	public String modify(NoticeDto notice) {
		service.updateList(notice);
		return "redirect:/admin/notice";

	}

	/* 공지사항 삭제하기 */
	@PostMapping("remove")
	public String remove(int id) {
		service.remove(id);

		return "redirect:/admin/notice";
	}


	
	
	
	
	/* 1:1문의 보기 */
	@GetMapping("question")
	public void questList( @RequestParam(name = "page", defaultValue = "1") int page,
			  PageInfo pageInfo, Model model) { 

		List<QuestionDto> list = questService.questList(page, pageInfo);

		model.addAttribute("questionList", list);

	}

	

	/* 1:1문의 내용보기 */
	@GetMapping("answer")
	public void questContent(int q_number, Model model) {
		
		System.out.println(q_number);
		QuestionDto question = questService.ContentList(q_number);
		
		model.addAttribute("questContent", question);

	}
	
	
	@PostMapping("add")
	public void add(@RequestBody QuestionDto quest) {
		System.out.println(quest);
		questService.answerAdd(quest);
	}
	
	


	/* 책 등록 */
	@GetMapping("book")
	public void book() {

	}

	@PostMapping("book")
	public void bookRegister(BookDto bookDto) {
		int cnt = bookService.insertBook(bookDto);
		System.out.println(cnt);
	}

	@GetMapping("bookList")
	public List<BookDto> bookList(Model model) {
		List<BookDto> temp = bookService.getBookList();
		//System.out.println(temp);
		model.addAttribute("bookList", temp);
		return temp;
	}

	
	@GetMapping("modifyBook")
	public String modifyBook(@RequestParam int b_code,Model model) {
		System.out.println(b_code);
		BookDto temp = bookService.getByCode(b_code);
		System.out.println(temp);
		model.addAttribute("book",temp);
		return "admin/modifyBook";
	}
	 
	 @PostMapping("modifyBook")
	 public String modifyBookDto(BookDto bookDto) {
		 System.out.println(bookDto);
		 int cnt = bookService.modifyBook(bookDto);
		 System.out.println(cnt);
		 return "redirect:/admin/bookList";
	 }
	 
	 @DeleteMapping("deleteBook")
	 public String deleteBook(@RequestBody Map<String,String> map) {
		 System.out.println(map.get("b_code").getClass());
		 int cnt = bookService.removeBook(Integer.parseInt(map.get("b_code")));
		 return "redirect:/admin/bookList";
	 }

	 

}