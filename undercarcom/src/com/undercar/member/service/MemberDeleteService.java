package com.undercar.member.service;

import com.undercar.main.Service;
import com.undercar.member.dao.MemberDAO;

public class MemberDeleteService implements Service {

	private MemberDAO dao;
	public MemberDeleteService(MemberDAO dao) {
		// TODO Auto-generated constructor stub
		this.dao = dao;
	}
	@Override
	public Object service(Object[] objs) throws Exception {
		// TODO Auto-generated method stub
		String id = (String) objs[0];
		String pw = (String) objs[0];
		return dao.delete(id,pw);
	}

}
