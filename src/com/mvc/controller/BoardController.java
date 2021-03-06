package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.BoardService;



@WebServlet({"/boardList","/boardWrite","/boardDetail","/boardDel","/boardUpdateForm","/boardUpdate","/commentList","/commentWrite",
	"/commentUpdateForm","/commentUpdate","/commentDel","/boardSearch","/boardReportForm","/boardReport","/commReportForm","/commReport","/mainBoardList"})

public class BoardController  extends HttpServlet{
	

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			dual(req, resp);
		}

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			dual(req, resp);
		}

		private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			String uri = req.getRequestURI();
			String ctx = req.getContextPath();
			String sub = uri.substring(ctx.length());
			
			BoardService service = new BoardService(req, resp);
			
			//msg처리
			String msg = (String) req.getSession().getAttribute("msg");

			System.out.println("session msg:"+msg);
			
			if(msg != null) { 
				req.setAttribute("msg", msg);  
				req.getSession().removeAttribute("msg"); 
			}
		
			switch(sub) {
			
			case "/boardList":
				System.out.println("리스트 요청");
				service.list();
				break;
			
			case "/boardWrite":
				System.out.println("글쓰기 요청");
				service.write();
				break;
			
			case "/boardDetail":
				System.out.println("상세보기 요청");
				service.detail();
				break;
			
			case "/boardDel":
				System.out.println("삭제요청");
				service.del();
				break;
			
			case "/boardUpdateForm":
				System.out.println("수정보기 요청");
				service.updateForm();
				break;
				
			
			case "/boardUpdate":
				System.out.println("수정요청");
				service.update();
				break;
				
			case "/commentWrite":
				System.out.println("댓글쓰기 요청");
				service.commentWrite();
				break;
				
			case "/commentUpdateForm":
				System.out.println("댓글 수정폼 요청");
				service.commentUpdateForm();
				break;
				
			case "/commentUpdate":
				System.out.println("댓글 수정 요청");
				service.commentUpdate();
				break;
				
			case "/commentDel":
				System.out.println("댓글 삭제 요청");
				service.commentDel();
				break;
			
			case "/boardSearch":
				System.out.println("커뮤니티 검색요청");
				service.boardSearch();
				break;
				
			case "/boardReportForm":
				System.out.println("신고창 요청");
				service.boardReportForm();
				break;
				
			case "/boardReport":
				System.out.println("글신고요청");
				service.boardReport();
				break;
			
			case "/commReportForm":
				System.out.println("댓글 신고창요청");
				service.commReportForm();
				break;
			
			case "/commReport":
				System.out.println("댓글 신고 요청");
				service.commReport();
				break;
			
			case "/mainBoardList":
				System.out.println("메인에서 인기게시글 요청");
				service.mainBoardList();
				break;
			}
		}

}
