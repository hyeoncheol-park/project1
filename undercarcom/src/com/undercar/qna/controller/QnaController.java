package com.undercar.qna.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.undercar.main.Controller;
import com.undercar.main.Execute;
import com.undercar.main.Service;
import com.undercar.member.dto.LoginDTO;
import com.undercar.qna.dto.QnaDTO;
import com.undercar.util.page.PageObject;

public class QnaController implements Controller{

	
	private Service listService;
	private Service viewService;
	private Service writeService;
	private Service updateService;
	private Service deleteService; 
	private Service answerService; 
	private Service adminListService; 
	
	public QnaController(Service listService,Service viewService,
			Service writeService,Service updateService, Service deleteService ,Service answerService ,Service adminListService) {
	
	
		this.listService = listService;
		this.viewService = viewService;
		this.writeService = writeService;
		this.updateService = updateService;
		this.deleteService = deleteService;
		this.answerService = answerService;
		this.adminListService = adminListService;
	
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, String uri) throws Exception {
		// TODO Auto-generated method stub
		
		String jsp = "";
		PageObject pageObject = PageObject.getInstance(request.getParameter("page"),
				request.getParameter("perPageNum"));
		HttpSession session = request.getSession(true);
		LoginDTO dto = (LoginDTO) session.getAttribute("login");
				
		
	String key = request.getParameter("key");	
	String word = request.getParameter("word");
	if(word != null) {
		pageObject.setKey(key);
		pageObject.setWord(word);
		
		
		
	}//if end
		
		switch (uri) {
		case "/qna/list.do":
			System.out.println("list.do()");
			String sessionId = dto.getId();
			request.setAttribute("pageObject", pageObject);
			request.setAttribute("list",Execute.service(listService, pageObject,sessionId) );
			jsp="qna/list";
			break;
		case "/qna/adminlist.do":
			 sessionId = dto.getId();
			request.setAttribute("pageObject", pageObject);
			request.setAttribute("adminlist",Execute.service(adminListService, pageObject,sessionId) );
			jsp="qna/adminlist";
			break;
		case "/qna/view.do":
			int no = Integer.parseInt(request.getParameter("no"));
			
			request.setAttribute("dto",Execute.service(viewService, no) );
			jsp="qna/view";
			break;
		case "/qna/writeForm.do":
			
			jsp="qna/writeForm";
			break;
		case "/qna/write.do":
			
			String id=dto.getId();
			Execute.service(writeService, getDTO(request.getParameter("title"), request.getParameter("content"),id,id));
			
			jsp="redirect:list.do";
			break;
			
		case "/qna/answerForm.do":			
			no = Integer.parseInt(request.getParameter("no"));
			request.setAttribute("dto", Execute.service(viewService, no));
			jsp="qna/answerForm";
			
			break;
		case "/qna/answer.do":	
			 session = request.getSession(true);
			 dto = (LoginDTO) session.getAttribute("login");
			 id=dto.getId();
			Execute.service(answerService, getDTO(request.getParameter("title"),request.getParameter("content"),id
					,(Integer.parseInt(request.getParameter("refNo"))),(Integer.parseInt(request.getParameter("ordNo"))),(Integer.parseInt(request.getParameter("levNo"))),(Integer.parseInt(request.getParameter("parentNo"))),request.getParameter("id1"))
					);
			jsp="redirect:list.do";	
			
			break;
		case "/qna/updateForm.do":
			
			no = Integer.parseInt(request.getParameter("no"));
			request.setAttribute("dto", Execute.service(viewService, no));
			
			
			jsp="qna/updateForm";
			break;
		case "/qna/update.do":
			
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
		case "/qna/delete.do":
			System.out.println(deleteService);

			Execute.service(deleteService, Integer.parseInt(request.getParameter("no")));
			jsp="redirect:list.do";	
			break;

		default:
			break;
		}
		
		return jsp;
	}
	
private QnaDTO getDTO(String title,String content ,String id ,String id1) {
	QnaDTO dto = new QnaDTO();
	dto.setTitle(title);
	dto.setContent(content);
	dto.setId(id);
	dto.setId1(id);
	return dto;
}
private QnaDTO getDTO(int no,String title,String content) {
	QnaDTO dto = new QnaDTO();
	dto.setNo(no);
	dto.setTitle(title);
	dto.setContent(content);
	return dto;
}
public QnaDTO getDTO(String title, String content,String id,int refNo,int ordNo,int levNo,int parentNo ,String id1 ) {
	QnaDTO dto = new QnaDTO();
	
	dto.setTitle(title);
	dto.setContent(content);
	dto.setId(id);
	dto.setRefNo(refNo);
	dto.setOrdNo(ordNo);
	dto.setLevNo(levNo);
	dto.setParentNo(parentNo);
	dto.setId1(id1);
	return dto;
}

}
