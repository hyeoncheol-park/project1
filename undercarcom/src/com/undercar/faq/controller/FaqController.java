package com.undercar.faq.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.undercar.faq.dto.FaqDTO;
import com.undercar.main.Controller;
import com.undercar.main.Execute;
import com.undercar.main.Service;
import com.undercar.member.dto.LoginDTO;
import com.undercar.util.page.PageObject;

public class FaqController implements Controller{

	
	private Service listService;
	private Service viewService;
	private Service writeService;
	private Service updateService;
	private Service deleteService; 
	
	public FaqController(Service listService,Service viewService,
			Service writeService,Service updateService, Service deleteService) {
	
	
		this.listService = listService;
		this.viewService = viewService;
		this.writeService = writeService;
		this.updateService = updateService;
		this.deleteService = deleteService;
	
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, String uri) throws Exception {
		// TODO Auto-generated method stub
		
		String jsp = "";
		PageObject pageObject = PageObject.getInstance(request.getParameter("page"),
				request.getParameter("perPageNum"));
		
	String key = request.getParameter("key");	
	String word = request.getParameter("word");
	if(word != null) {
		pageObject.setKey(key);
		pageObject.setWord(word);
	}//if end
		
		switch (uri) {
		case "/faq/list.do":
			System.out.println("faq 실행");
			request.setAttribute("pageObject", pageObject);
			request.setAttribute("list",Execute.service(listService, pageObject));
			jsp = "faq/list";
			break;
		case "/faq/view.do":
			int no = Integer.parseInt(request.getParameter("no"));
			
			request.setAttribute("dto",Execute.service(viewService, no) );
			jsp="faq/view";
			break;
		case "/faq/faq1.do":
			
			jsp="faq/faq1";
			break;
		case "/faq/writeForm.do":
			
			jsp="faq/writeForm";
			break;
		case "/faq/write.do":
			
			Execute.service(writeService, getDTO(request.getParameter("title"), request.getParameter("content")));
			
			jsp="redirect:list.do";
			break;
			
		case "/faq/answerForm.do":			
			no = Integer.parseInt(request.getParameter("no"));
			request.setAttribute("dto", Execute.service(viewService, no));
			jsp="faq/answerForm";
			
			break;
		case "/faq/updateForm.do":
			
			 no = Integer.parseInt(request.getParameter("no"));
			 request.setAttribute("dto", Execute.service(viewService, no));
			 

			jsp="faq/updateForm";
			break;
		case "/faq/update.do":
			
			no = Integer.parseInt(request.getParameter("no"));
			Execute.service(updateService, getDTO(no,request.getParameter("title"), request.getParameter("content")));
			
			jsp ="redirect:view.do?no=" + no 
					+ "&page=" + pageObject.getPage()
					+ "&perPageNum=" + pageObject.getPerPageNum()
					+ ((pageObject.getWord() != null && !pageObject.getWord().equals(""))
					  ? "&key="+pageObject.getKey() 
					  	+ "&word=" 
					  	// 검색어가 한글이 경우 엔코딩해서 넘긴다.
					    + URLEncoder.encode(pageObject.getWord(),"utf-8"):"");
			break;
		case "/faq/delete.do":
			System.out.println(deleteService);

			Execute.service(deleteService, Integer.parseInt(request.getParameter("no")));
			jsp ="redirect:list.do"; 			
			break;

		default:
			break;
		}
		
		return jsp;
	}
	
private FaqDTO getDTO(String title,String content) {
	FaqDTO dto = new FaqDTO();
	dto.setTitle(title);
	dto.setContent(content);
	return dto;
}
private FaqDTO getDTO(int no,String title,String content) {
	FaqDTO dto = new FaqDTO();
	dto.setNo(no);
	dto.setTitle(title);
	dto.setContent(content);
	return dto;
}


}
