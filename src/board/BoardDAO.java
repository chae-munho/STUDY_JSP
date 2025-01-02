package board;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	DataSource ds;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//BoardDAO() 생성자를 이용한 DataSource의 객체 생성
	public BoardDAO() {
		try {
			//InitialContext는 JNDI 서비스를 통해 네이밍 및 디렉토리 리소스를 조회하는 기본적인 진입점 역할을 한다.
			InitialContext context = new InitialContext();
			//context.looup 메서드를 사용하여 JNDI 이름이 java:/comp/env/jdbc/mysql인 데이터 로스를 조회
			//조회된 리소스를 DataSource 타입으로 캐스팅하여 ds에 저장
			// 이 데이터 소스는 애플리케이션 서버 또는 컨테이너가 제공하는 데이터베이스 연결 풀을 의미
			ds = (DataSource)context.lookup("java:/comp/env/jdbc/mysql");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//totalRecord() : 전체 레코드 수 추출
	public int totalRecord(String col, String key) {
		//전체 레코드 수를 저장할 변수
		int totalRecord = 0;
		String Query = "";
		String Query_Sub = "";
		try {
			//Connection 객체 획득
			conn = ds.getConnection();
			
			//기본질으 : 검색 키워드가 입력되지 않은 질의
			Query = "select count(rcdNO) from board";
			
			// 추가질의 : 검색 키워드가 입력된다면 기본 질의에 추가되어야 하는 질의  만약 boardList.jsp에서 검색 submit을 하지 않으면 col, key에는 null값이 들어가므로 해당 조건문은 실행되지 않는다.
			if ((col != null) && (col.equals("rcdSubject"))) {
				Query_Sub = " where rcdSubject like '%"+key+"%'";
			} else if ((col != null) && col.equals("rcdContent")) {
				Query_Sub = " where rcdContent like '%"+key+"%'";
			}
			//최종 질의 완성과 질의 수행
			Query = Query + Query_Sub;
			pstmt = conn.prepareStatement(Query); 
			rs = pstmt.executeQuery();
			
			//전체 리코드의 수를 추출
			//결과 레코드가 존재한다면 rs.next() 첫번째 열의 값을 totalRecord에 저장
			if(rs.next()) totalRecord = rs.getInt(1);
			rs.close();
			pstmt.close();
			conn.close();
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return totalRecord;
	}
	
	//selectAllRecords() : boardList.jsp 문서에 출력할 모든 레코드의 일부 필드 추출
	public List<BoardVO> selectAllRecords(int nowPage, int pageRecords, String col, String key) {
		//BoardService의 boardList()에 전달할 recordsList 리스트 생성
		List<BoardVO> recordList = new ArrayList<BoardVO>();
		
		String Query = "";
		String Query_Sub1 = "";
		String Query_Sub2 = "";
		try {
			//각 페이지의 시작 인덱스 추출
			int pageIndex = pageRecords * (nowPage - 1);
			//DataSource로부터 Connection 객체 획득
			conn = ds.getConnection();
			
			//기본질의 : 검색 키워드가 입력되지 않은 질의
			Query = "select rcdNO, rcdSubject, userName, rcdDate, rcdRefer, rcdIndent from board";
			
			//추가 질의 : 검색 키워드가 입력된다면 기본 질의에 추가되어야 하는 질의
			if ((col != null) && (col.equals("rcdSubject"))) {
				Query_Sub1 = " where rcdSubject like '%"+key+"%'";
			} else if ((col != null) && col.equals("rcdContent")) {
				Query_Sub1 = " where rcdContent like '%"+key+"%'";
			}
			//정렬 방식 지정 질의
			//Query_Sub2 = " order by rcdNO desc";
			Query_Sub2 = " order by rcdGrpNO desc, rcdOrder asc";
			//페이징 쿼리
			Query_Sub2 += " limit "+ pageIndex + ", "+pageRecords;
			
			//최종 질의 완성과 질의 수행
			Query = Query + Query_Sub1 + Query_Sub2;
			
			pstmt = conn.prepareStatement(Query);
			rs = pstmt.executeQuery();
			
			//레코드 리스트 추출
			while (rs.next()) {
				BoardVO boardVO = new BoardVO();
				boardVO.setRcdNO(rs.getInt("rcdNO"));
				boardVO.setRcdSubject(rs.getString("rcdSubject"));
				boardVO.setUserName(rs.getString("userName"));
				
				boardVO.setRcdDate((java.sql.Date)rs.getDate("rcdDate"));
				boardVO.setRcdRefer(rs.getInt("rcdRefer"));
				boardVO.setRcdIndent(rs.getInt("rcdIndent"));
				recordList.add(boardVO);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//모든 레코드의 리스트를 반환
		return recordList;
		
	}
	//newRcdNO() : 새로 입력되는 레코드의 rcdNO 값 지정
	public int newRcdNO() {
		int newNO = 0;
		try {
			conn = ds.getConnection();
			//기존의 rcdNO 필드 값 중에서 가장 큰 값을 추출
			String Query = "select max(rcdNO) from board";
			pstmt = conn.prepareStatement(Query);
			rs = pstmt.executeQuery();
			//추출한 값보다 1만큼 더 큰 값을 새로운 레코드의 rcdNO 필드 값으로 지정
			if(rs.next()) newNO = rs.getInt(1) + 1;
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return newNO;
	}
	public void insertRecord(BoardVO boardVO) {
		String Query = "";
		try {
			conn = ds.getConnection();
			Query = "insert into board ";
			Query += "(rcdNO, rcdGrpNO, userName, userMail, rcdSubject, rcdContent, ";
			Query += "rcdPass, rcdRefer, rcdIndent, rcdOrder) ";
			Query += "values (?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(Query);
			
			pstmt.setInt(1, boardVO.getRcdNO());
			pstmt.setInt(2, boardVO.getRcdGrpNO());
			pstmt.setString(3, boardVO.getUserName());
			pstmt.setString(4, boardVO.getUserMail());
			pstmt.setString(5, boardVO.getRcdSubject());
			pstmt.setString(6, boardVO.getRcdContent());
			pstmt.setString(7, boardVO.getRcdPass());
			pstmt.setInt(8, boardVO.getRcdRefer());
			pstmt.setInt(9, boardVO.getRcdIndent());
			pstmt.setInt(10, boardVO.getRcdOrder());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	//incrementRcdRefer() : 출력할 레코드 조회 수를 1만큼 증가
	public void incrementRcdRefer(int rNo) {
		try {
			conn = ds.getConnection();
			String Query = "update board set rcdRefer=rcdRefer+1 where rcdNO=?";
			pstmt = conn.prepareStatement(Query);
			pstmt.setInt(1, rNo);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	//selectRecord() : boardContent.jsp 문서에 출력할 레코드를 추출
	public BoardVO selectRecord(int rNo) {
		BoardVO record = new BoardVO();
		String Query = "";
		try {
			conn = ds.getConnection();
			Query = "select rcdNO, userName, userMail, rcdSubject, rcdContent, rcdDate, rcdRefer ";
			Query += "from board where rcdNO=?";
			pstmt = conn.prepareStatement(Query);
			pstmt.setInt(1, rNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				record.setRcdNO(rs.getInt("rcdNO"));
				record.setUserName(rs.getString("userName"));
				record.setUserMail(rs.getString("userMail"));
				record.setRcdSubject(rs.getString("rcdSubject"));
				record.setRcdContent(rs.getString("rcdContent"));
				record.setRcdDate((java.sql.Date)rs.getDate("rcdDate"));
				record.setRcdRefer(rs.getInt("rcdRefer"));
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return record;
	}
	//selectParentRecord() : 답변 글을 작성하는 원글 레코드 추출
	public BoardVO selectParentRecord(int rNo) {
		BoardVO parent = new BoardVO();
		try {
			conn = ds.getConnection();
			String Query = "select rcdSubject, rcdContent from board where rcdNO=?";
			pstmt = conn.prepareStatement(Query);
			pstmt.setInt(1, rNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				parent.setRcdSubject(rs.getString("rcdSubject"));
				parent.setRcdContent(rs.getString("rcdContent"));
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return parent;
	}
	//getParentField() : 원글 레코드의 rcdGrpNO, rcdIndent, rcdOrder 필드 값 추출
	public BoardVO getParentField(int rNo) {
		BoardVO replyField = new BoardVO();
		try {
			conn = ds.getConnection();
			String Query = "select rcdGrpNO, rcdIndent, rcdOrder from board where rcdNO=?";
			pstmt = conn.prepareStatement(Query);
			pstmt.setInt(1, rNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				replyField.setRcdGrpNO(rs.getInt("rcdGrpNO"));
				replyField.setRcdIndent(rs.getInt("rcdIndent"));
				replyField.setRcdOrder(rs.getInt("rcdOrder"));
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return replyField;
	}
	//increRcdOrder() : 답변 레코드 입력을 위한 기존 레코드의 rcdOrder 필드 값 조정
	public void increRcdOrder(int rGrpNo, int rOrder) {
		try {
			conn = ds.getConnection();
			String Query = "update board set rcdOrder=rcdOrder+1 where ";
			Query += "rcdGrpNO=? and rcdOrder>?";
			
			pstmt = conn.prepareStatement(Query);
			pstmt.setInt(1,  rGrpNo);
			pstmt.setInt(2, rOrder);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//checkPassword() : 패스워드 비교 
	public boolean checkPassword(int rNo, String password) {
		try {
			conn = ds.getConnection();
			String realPassword = "";
			String Query = "select rcdPass from board where rcdNO=?";
			pstmt = conn.prepareStatement(Query);
			pstmt.setInt(1, rNo);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				realPassword = rs.getString("rcdPass");
			}
			if(!password.equals(realPassword)) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return true;
	}
	//modifyRecord() : 레코드 수정
	public void modifyRecord(int rNo, BoardVO boardVO) {
		try {
			String Query = "";
			conn = ds.getConnection();
			Query = "update board set userMail=?, rcdSubject=?, rcdContent=? ";
			Query += " where rcdNO=?";
			pstmt = conn.prepareStatement(Query);
			pstmt.setString(1, boardVO.getUserMail());
			pstmt.setString(2, boardVO.getRcdSubject());
			pstmt.setString(3, boardVO.getRcdContent());
			pstmt.setInt(4, boardVO.getRcdNO());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}
	//deleteRecord() : 레코드 삭제
	public void deleteRecord(int rNo) {
		try {
			conn = ds.getConnection();
			
			String Query = "delete from board where rcdNO=?";
			pstmt = conn.prepareStatement(Query);
			pstmt.setInt(1, rNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

}
