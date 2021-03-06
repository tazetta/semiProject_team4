package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.BoardDAO;
import com.mvc.dao.MemberDAO;
import com.mvc.dto.BoardDTO;
import com.mvc.dto.MemberDTO;
import com.google.gson.Gson;

public class MemberService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;

	MemberDAO dao = null;

	String msg = "";
	String page = "";

	RequestDispatcher dis = null;

	public MemberService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		dao = new MemberDAO();
	}

	/* 로그인 */
	public void login() throws ServletException, IOException {
		// index.jsp에서 보내는 param값 받기
		String id = req.getParameter("userId");
		String pw = req.getParameter("userPw");
		System.out.println(id + "/" + pw);
		MemberDTO dto = dao.login(id, pw); // dao에 id,pw 담아 보낸 후 로그인 메서드 실행

		msg = "아이디와 비밀번호를 확인해주세요";
		page = "login.jsp";
		System.out.println("서비스 dto: "+ dto);
		if (dto!=null) { // 로그인 성공시 (true 반환시)
			
			if(dto.getWithdraw()!=null && dto.getWithdraw().equals("TRUE")) {
			
				msg = "탈퇴한 회원입니다.";
				
			}else if(dto.getBlackCnt()>0) {
				msg="블랙리스트에 등록된 회원입니다.";							
			}else {
				msg = id + "님 로그인 되었습니다";
				
				req.getSession().setAttribute("loginId", id); // "loginId"라는 이름으로 session에 저장
				page = "index.jsp";
			}
			dao = new MemberDAO();
			if(dao.chkManager(id)) {
				req.getSession().setAttribute("isManager", "true");
			}
//			page = "/profile"; // 지금은 list컨트롤러 없어서 404에러 떨어짐
			
		}
		dao.resClose();
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	/* 회원정보 보기 */
	public void profile() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		System.out.println(loginId + "의 프로필");
		if (loginId != null) { // 로그인체크
			MemberDTO dto = dao.profile(loginId);
			System.out.println("dto:" + dto);
			msg="회원정보가 존재하지 않습니다";
			page="./";
			if (dto != null) {
				page = "profile.jsp";
				msg="";
				req.setAttribute("profile", dto);
			}
			dao.resClose();
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
	}

	/* 수정할 회원정보 보기 */
	public void updateDetail() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		System.out.println(loginId + "의 수정할 프로필");
		if (loginId != null) { // 로그인 체크
			MemberDTO dto = dao.profile(loginId);
			System.out.println("dto:" + dto);
			if (dto != null) {
				page = "updateForm.jsp";
				req.setAttribute("profile", dto);
			}
			dao.resClose();
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
	}

	/* 회원정보 수정 */
	public void update() throws IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String pw = req.getParameter("pw");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		System.out.println(loginId + "/" + pw + "/" + name + "/" + phone + "/" + email);
		if (loginId != null) {
			MemberDTO dto = new MemberDTO();
			dto.setId(loginId);
			dto.setPw(pw);
			dto.setName(name);
			dto.setPhone(phone);
			dto.setEmail(email);

			boolean success = false;
			success = dao.update(dto);
			System.out.println("회원정보수정:" + success);

			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("success", success);
			dao.resClose();
			Gson gson = new Gson();
			String json = gson.toJson(map);
			resp.getWriter().print(json);
		} else {
			msg="로그인이 필요한 서비스 입니다";
			req.setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
	}

	/* 비밀번호 변경 */

	public void pwUpdate() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");

		String pw = req.getParameter("userPw");
		String updatePw = req.getParameter("updatePw");
		System.out.println(loginId + ":" + pw + "/" + updatePw);

		if (loginId != null) { // 로그인체크

			boolean success = false;
			msg = "비밀번호를 확인해주세요";
			page = "pwUpdate.jsp";

			success = dao.updatePw(loginId, pw, updatePw);
			System.out.println("비밀번호수정:" + success);
			if (success) {
				msg = "비밀번호가 변경 되었습니다";
				page = "profile";
			}
			dao.resClose();
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);

		}else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
	}

	/* 내가 쓴 글 목록 */
	public void wroteList() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		System.out.println(loginId + "의 게시글 목록");

		String pageParam = req.getParameter("page"); // 이전|다음 링크로 들어온 param 받기
		System.out.println("page:" + pageParam);

		int page = 1; // 기본은 1

		if (pageParam != null) {
			page = Integer.parseInt(pageParam); 
		}
		if (loginId != null) { // 로그인 체크

			HashMap<String, Object> map = dao.wroteList(loginId, page);
			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("list", map.get("list")); // req에 저장
			req.setAttribute("currPage", page);
			System.out.println("list: "+map.get("list"));
			// 특정페이지로 보내기
			dao.resClose();
			dis = req.getRequestDispatcher("wroteList.jsp"); 
			dis.forward(req, resp);
		} else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
	}

	/* 중복 체크 */
	public void overlay() throws IOException {
		String id = req.getParameter("id");
		boolean success = false;
		System.out.println("id : " + id);
//		MemberDAO dao = new MemberDAO();// MemberDAO를 객체화
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			success = dao.overlay(id);
			System.out.println("아이디 사용여부 : " + success);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 여기서 자원을 반납하면, service 내에서 DAO 를 사용 할 만큼 사용하고 닫아줄 수 있다.
			dao.resClose();
			map.put("use", success);
			dao.resClose();
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			// resp.setContentType("text/html charset-UTF-8");//한글이 없으면 굳이 안해줘도된다.
			// jsp와 Controller 가 같은 서버에 존재 할 때는 아래 구문이 불필요하다.
			// resp.setHeader("Access-Control-Allow-origin, "*");
			resp.getWriter().print(json);// 페이지에 그려주는것.
		}

	}

	/* 회원가입 */
	public void join() throws IOException {
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String pw = req.getParameter("pw");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		System.out.println(id + "/" + name + "/" + pw + "/" + phone + "/" + email);

//		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setPw(pw);
		dto.setPhone(phone);
		dto.setEmail(email);

		HashMap<String, Object> map = new HashMap<String, Object>();
		String msg = "회원정보를 확인해주세요";
		int success = 0;
		if (dao.join(dto) > 0) {
			msg = "회원가입이 완료되었습니다.";
			success = 1;
		}
		map.put("msg", msg);
		map.put("success", success);
		//map -> json으로 바꾸기 위한 작업
		dao.resClose();
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);

		resp.setContentType("text/html; charset=UTF-8"); // 보낼 데이터 타입과 한글깨짐 방지를 위한 인코딩 타입 지정
		resp.setHeader("Access-Control-Allow", "*");// javascript 에서 다른 도메인으로 통신은 기본적으로 안된다.(cross domain issue) - 허용 처리
		resp.getWriter().print(json);// 페이지에 그려주는것

	}

	/*아이디 찾기*/
	public void findId() throws ServletException, IOException {
		
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		System.out.println(name + "/" + phone);
//		MemberDAO dao = new MemberDAO();				
		HashMap<String, Object> map = new HashMap<String, Object>();
		String id = "";
		try {
			id = dao.findId(name, phone);
			System.out.println("아이디찾기 : "+id);

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
			map.put("use", id);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			resp.getWriter().print(json);// 페이지에 그려주는것.
		}	
//		if(id!="") {
//			page = "findIdAfter.jsp";
//			
//			req.getSession().setAttribute("findId", id); // "findId"라는 이름으로 session에 저장
//		}else{
//			page = "findIdPw.jsp";
//			msg = "이름, 핸드폰번호를 다시 확인 후 입력해주세요.";
//			dis = req.getRequestDispatcher(page);
//			
//		}
//		req.setAttribute("msg", msg);
//		dis = req.getRequestDispatcher(page);
//		dis.forward(req, resp);

	}
	/*아이디찾기 두번째 요청*/
	public void findId1() throws ServletException, IOException{
		
		String id = req.getParameter("id");
		System.out.println(id);	
		page="findIdAfter.jsp";

		dao.resClose();
		req.setAttribute("findId", id);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		
	}
	/*비밀번호 찾기*/
	public void findPw() throws ServletException, IOException{
		
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		boolean pw = false;
		System.out.println(id+"/"+name+"/"+phone);
//		MemberDAO dao = new MemberDAO();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			pw = dao.findPw(id, name, phone);
			System.out.println("가입여부 : " + pw);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
			map.put("use", pw);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			resp.getWriter().print(json);// 페이지에 그려주는것.
		}	
	}
	/*비밀번호 찾기 두번째 요청*/
	public void findPw1() throws ServletException, IOException{
		
		String id = req.getParameter("userId");
		System.out.println(id);	
		page="findpwUpdate.jsp";

		dao.resClose();
		req.setAttribute("id", id);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		
	}
	
	/*비밀번호 찾기 후 수정*/
	public void findpwUpdate() throws ServletException, IOException {
		
		boolean success = false;
		String newPw = req.getParameter("newPw");
		String id = req.getParameter("id");
		System.out.println("새비밀번호: "+newPw);
		System.out.println("비밀번호 바꿀 대상 : "+id);
		msg="비밀번호를 다시 확인해주세요.";
		page = "findpwUpdate.jsp";
		
		success = dao.findpwUpdate(id,newPw);
		System.out.println("비밀번호 수정 : " + success);
		if(success) {
			msg="비밀번호가 수정 되었습니다.";
			page="login.jsp";
		}
		dao.resClose();
		req.setAttribute("msg", msg);
		dis=req.getRequestDispatcher(page);
		dis.forward(req, resp);
		
	}

	/* 회원 탈퇴 */
	public void memberWithdraw() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String pw = req.getParameter("userPw");
		System.out.println("탈퇴신청:" + loginId + "/" + pw);

		boolean success = false;
		if (loginId != null) {// 로그인 체크
			/* MemberDAO dao = new MemberDAO(); */
			success = dao.memberWithdraw(loginId, pw);
			System.out.println("회원탈퇴" + success);

			msg = "비밀번호를 확인해주세요";
			page = "memberWithdraw.jsp";

			if (success) {
				msg = "탈퇴되었습니다.";
				page = "index.jsp";
				req.getSession().removeAttribute("loginId");
			}
			dao.resClose();
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}

	}


	/* 가봤어요 리스트*/
	   public void visitedList() throws IOException, ServletException {
	      String loginId = (String) req.getSession().getAttribute("loginId");
	      System.out.println(loginId+"의 가봤어요 리스트");
	      
	      if (loginId != null) {// 로그인 체크
	         String pageParam = req.getParameter("page"); // 이전|다음 링크로 들어온 param 받기
	         System.out.println("page:" + pageParam);

	         int group = 1; // 기본은 1

	         if (pageParam != null) {
	            group = Integer.parseInt(pageParam); 
	         }
	         int type=2;
			/* MemberDAO dao = new MemberDAO(); */
	         HashMap<String, Object> map =dao.visitedList(loginId,group,type);
	         if(map !=null) {
	            req.setAttribute("maxPage", map.get("maxPage"));
	            req.setAttribute("list", map.get("list")); // req에 저장
	            req.setAttribute("currPage", group);
	            System.out.println("list: "+map.get("list"));
	         }
	 		dao.resClose();
	         dis = req.getRequestDispatcher("myVisited.jsp"); 
	         dis.forward(req, resp);
	      }else {
	         msg="로그인이 필요한 서비스 입니다";
	         req.getSession().setAttribute("msg", msg);
	         resp.sendRedirect("index.jsp"); 
	      }   
	   }

	   /* 즐겨찾기 리스트*/
	   public void bookmarkList() throws IOException, ServletException {
	      String loginId = (String) req.getSession().getAttribute("loginId");
	      System.out.println(loginId+"의 즐겨찾기 리스트");
	      
	      if (loginId != null) {// 로그인 체크
	         String pageParam = req.getParameter("page"); // 이전|다음 링크로 들어온 param 받기
	         System.out.println("page:" + pageParam);

	         int group = 1; // 기본은 1

	         if (pageParam != null) {
	            group = Integer.parseInt(pageParam); 
	         }
	         int type=1;
	         HashMap<String, Object> map =dao.visitedList(loginId,group,type);
	         if(map !=null) {
	            req.setAttribute("maxPage", map.get("maxPage"));
	            req.setAttribute("list", map.get("list")); // req에 저장
	            req.setAttribute("currPage", group);
	            System.out.println("list: "+map.get("list"));
	         }
	 		dao.resClose();
	         dis = req.getRequestDispatcher("myBookmark.jsp"); 
	         dis.forward(req, resp);
	      }else {
	         msg="로그인이 필요한 서비스 입니다";
	         req.getSession().setAttribute("msg", msg);
	         resp.sendRedirect("index.jsp"); 
	      }   
	   }


	/*내가 쓴 글 삭제*/
	public void wroteDel() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String boardIdx = (String) req.getParameter("boardIdx");
		System.out.println(loginId+"의 "+boardIdx+"삭제");
		
		if(loginId!=null) { //로그인체크

		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.detail(boardIdx);
		System.out.println("글작성자:"+dto.getId());
		
		msg="삭제 권한이 없습니다";
		page="wroteList";
		
		if(loginId.equals(dto.getId())) {
			dao = new BoardDAO();
			String newFileName = dao.getFileName(boardIdx);//파일명추출
			msg="삭제 실패했습니다.";
			dao = new BoardDAO();
			if(dao.del(boardIdx,newFileName)>0) {
				msg="삭제되었습니다.";
			}
			
		}
		dao.resClose();
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		}else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
	}

	/*마이페이지 북마크 업데이트*/
	public void myUpdate() throws ServletException, IOException {
	      String loginId = (String) req.getSession().getAttribute("loginId"); 
	      String myIdx = req.getParameter("myidx");
	      String type = req.getParameter("type");
	      System.out.println(loginId+"의 "+type+":"+myIdx);
		/* MemberDAO dao = new MemberDAO(); */
	      if(loginId!=null) { //로그인체크
	      boolean success =dao.myUpdate(myIdx,type);
	      System.out.println("북마크업데이트:"+success);
	      msg="삭제에 실패했습니다";
	      page="profile";
	      if(success) {
	         if(type.equals("1")) {
	         msg="즐겨찾기에서 삭제되었습니다.";
	         page="bookmarkList";
	         }else {
	         msg="가봤어요에서 삭제되었습니다.";
	         page="visitedList";
	         }
	      }
			dao.resClose();
	      req.getSession().setAttribute("msg", msg);
	      resp.sendRedirect(page); 
	      }
	      else {
	         msg="로그인이 필요한 서비스 입니다";
	         req.getSession().setAttribute("msg", msg);
	         resp.sendRedirect("index.jsp"); 
	      }
	   }



}
