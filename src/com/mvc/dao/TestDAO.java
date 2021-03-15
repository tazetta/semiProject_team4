package com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.dto.RepDTO;
import com.mvc.dto.TestBookDTO;
import com.mvc.dto.TripDTO;



public class TestDAO {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps  = null;

public TestDAO() {		
		
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void resClose() {
		try {
			if(conn!=null) {conn.close();}
			if(rs!=null) {rs.close();}
			if(ps!=null) {ps.close();}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public TripDTO tripDetail(String conIdx) {
		TripDTO dto = null;
		//지역코드 가져와야함.
		
		try {
			
			String sql = "SELECT title,reg_date,firstimage,deactivate,overview,LATITUDE,LONGITUDE" + 
					"    ,(SELECT name FROM area WHERE areacode=(SELECT areacode FROM trip WHERE contentid=?)) AS area" + 
					"    ,(SELECT COUNT(myidx) FROM bookmark WHERE contentid=? AND deactivate='FALSE' AND type=1) AS bookmark" + 
					"    FROM trip WHERE contentid=? AND deactivate='FALSE'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, conIdx);
			ps.setString(3, conIdx);
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println(rs.getString("firstimage"));
				dto = new TripDTO();
				dto.setTitle(rs.getString("title"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setFirstImage(rs.getString("firstimage"));
				dto.setDeactivate(rs.getString("deactivate"));
				dto.setArea(rs.getString("area"));
				dto.setBookmark(rs.getInt("bookmark"));
				dto.setOverview(rs.getString("overview"));
				dto.setLatitude(rs.getString("LATITUDE"));
				dto.setLongitude(rs.getString("LONGITUDE"));
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public TestBookDTO bookmark(String conIdx, String id) {
		//id 받아와야함.
		System.out.println(conIdx);
		String sql ="SELECT * FROM bookmark WHERE contentid=? AND id=? AND type=1";
		TestBookDTO dto =null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new TestBookDTO();
				dto.setMyidx(rs.getInt("myidx"));
				dto.setContentid(rs.getInt("contentid"));
				dto.setId(rs.getString("id"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setType(rs.getInt("type"));
				dto.setDeactivate(rs.getString("deactivate"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(dto);		
		return dto;
	}

	public TestBookDTO visit(String conIdx, String id) {
		//id 받아와야함.
		String sql ="SELECT * FROM bookmark WHERE contentid=? AND id=? AND type=2";
		TestBookDTO dto =null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new TestBookDTO();
				dto.setMyidx(rs.getInt("myidx"));
				dto.setContentid(rs.getInt("contentid"));
				dto.setId(rs.getString("id"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setType(rs.getInt("type"));
				dto.setDeactivate(rs.getString("deactivate"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(dto);
		return dto;
	}



	public int addDel(TestBookDTO bdto) {
				int suc = 0;
				String sql = "INSERT INTO bookmark (myIdx, contentId, id, type) VALUES (bookmark_seq.NEXTVAL,?,?,?)";
				try {
					if(bdto.getMyidx()>0) {
						System.out.println("업데이트");
						 sql = "UPDATE bookmark SET deactivate='FALSE' WHERE myidx=?  ";
						if(bdto.getDeactivate().equals("FALSE")) {
							sql = "UPDATE bookmark SET deactivate='TRUE' WHERE myidx=? ";				
						}			
						ps = conn.prepareStatement(sql);
						ps.setInt(1, bdto.getMyidx());
					}else {
						ps = conn.prepareStatement(sql);
						ps.setInt(1, bdto.getContentid());;
						ps.setString(2, bdto.getId());;
						ps.setInt(3, bdto.getType());
					}
					suc=ps.executeUpdate();
					
					System.out.println("추가,수정 : " + suc);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				return suc;
	}

	public ArrayList<RepDTO> reportBBS() {
//		String sql="SELECT boardidx,id,deactivate FROM bbs WHERE reportcnt>0 AND deactivate='FALSE'";
		String sql = "SELECT b.boardidx,b.id,r.reason,b.deactivate, r.bbsrepidx FROM bbsrep r, bbs b "
				+ "WHERE r.boardidx=b.boardidx ORDER BY r.reg_date DESC";
		ArrayList<RepDTO> list = null;
		
		try {
			ps= conn.prepareStatement(sql);
			rs=ps.executeQuery();
			 list = new ArrayList<RepDTO>();
			while(rs.next()) {

				RepDTO dto = new RepDTO();
				dto.setBoardIdx(rs.getInt("boardIdx"));
				dto.setId(rs.getString("id"));
				dto.setReason(rs.getString("reason"));
				dto.setDeactivate(rs.getString("deactivate"));
				dto.setBbsRepIdx(rs.getInt("bbsrepidx"));
				list.add(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public String repReason(String bbsRepIdx) {
		String reason="";
		String sql = "SELECT reason FROM  bbsrep WHERE bbsrepidx=?";
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, bbsRepIdx);
			rs = ps.executeQuery();
			if(rs.next()) {
				reason=rs.getString("reason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reason;
	}

	public String repCnt(String boardIdx) {
		String repCnt = "";
		String sql = "SELECT count(bbsrepidx) AS repCnt FROM bbsrep  WHERE boardIdx=?";
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, boardIdx);
			rs = ps.executeQuery();
			if(rs.next()) {
				repCnt = rs.getString("repCnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return repCnt;
	}

	public int updateYN(String updateYN, String boardIdx) {
		String sql = "UPDATE bbs SET deactivate=? WHERE boardidx=?";
		int suc=0;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, updateYN);
			ps.setString(2, boardIdx);
			suc=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("블라인드 처리 확인 : "+suc);
		return suc;
	}

}
