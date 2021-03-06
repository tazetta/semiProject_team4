package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.TripDetailService;

@WebServlet({"/tripDetail","/bookmarkUpdate"})
public class TripDetailController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		human(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		human(req, resp);
	}

	private void human(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		
		TripDetailService service = new TripDetailService(req,resp);
		
		switch (req.getRequestURI().substring(req.getContextPath().length())) {
		case "/tripDetail":
			System.out.println("상세보기");
			service.tripDetail();
			break;

		case "/bookmarkUpdate":
			System.out.println("북마크추가");
			service.addDel();
			break;

		}
		
	}
	

}
