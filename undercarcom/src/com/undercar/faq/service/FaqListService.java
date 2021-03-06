package com.undercar.faq.service;

import com.undercar.faq.dao.FaqDAO;
import com.undercar.main.Service;
import com.undercar.util.page.PageObject;

public class FaqListService implements Service {
  private FaqDAO dao;
public FaqListService(FaqDAO dao) {
	this.dao=dao;
	// TODO Auto-generated constructor stub
}
	@Override
	public Object service(Object[] objs) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Faq리스트 서비스");
		PageObject pageObject = (PageObject) objs[0];
		
		pageObject.setTotalRow(dao.getTotalRow(pageObject));
		
		System.out.println("Faq리스트 서비스페이지 오브젝트"+pageObject);
		return dao.list(pageObject);
	}

}
