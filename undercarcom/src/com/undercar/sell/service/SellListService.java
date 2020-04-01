package com.undercar.sell.service;

import java.util.List;

import com.undercar.main.Service;
import com.undercar.sell.dao.SellDAO;
import com.undercar.util.page.PageObject;

public class SellListService implements Service{

	private final SellDAO dao;
	
	public SellListService(SellDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Object> service(Object[] objs)throws Exception{
		
		PageObject pageObject = (PageObject) objs[0];
		
		pageObject.setTotalRow(dao.getTotalRow(pageObject));
		System.out.println("SellListService.service().pageObject:" + pageObject);
		
		return dao.list(pageObject);
		
	}
}
