package board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BoardService boardService;
	BoardVO boardVO;
	
	//init() init 메서드를 이용한 boardService 객체와 boardVO 객체 생성
	public void init(ServletConfig config) throws ServletException {
		boardService = new BoardService();
		boardVO = new BoardVO();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handle(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handle(request, response);
	}
	protected void Handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String viewPage = "";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//전달된 URL로부터 요청 정보 추출
		
		String uri = request.getRequestURI(); //http://localhost:8080/app/boardList.do 요청 시 uri는 /app/boardList.do
		String conPath = request.getContextPath();
		String actCode = uri.substring(conPath.length());
		
		try { 
			//전체 레코드의 수와 모든 레코드의 필드 리스트를 boardList.jsp 문서에 전달해 출력
			//actCode가 /boardList.do와 일치하는 경우에만 요청을 처리
			if(actCode.equals("/boardList.do")) {
				//boardVO 객체를 담을 수 있는 리스트 recordList를 선언하고 초기화
				List<BoardVO> recordList = new ArrayList<BoardVO>();
				
				//검색을 위한 파라미터 출력
				//col은 제목, 내용 select 옵션의 내용이고 key는 검색란의 텍스트를 의미  만약 검색 키워드를 사용하지 않으면 col, key에는 기본적으로 null값이 들어있다.
				String col = request.getParameter("col");   
				String key = request.getParameter("key");
				
				//페이지 당 레코드 수, 페이지집합 당 레코드 수
				int pageRecords = 10;
				int pageSets = 10;
				request.setAttribute("pageRecords", pageRecords);
				request.setAttribute("pageSets", pageSets);
				
				//현재 페이지 지정
				int nowPage = 0;
				if (request.getParameter("nowPage") == null) {
					nowPage = 1;
				} else {
					nowPage = Integer.parseInt(request.getParameter("nowPage"));
				}
				request.setAttribute("nowPage", nowPage);
				
				//전체 레코드의 수를 추출해 request 내장 객체의 속성을 바인딩
				int totalRecord = boardService.recordCount(col, key);  //
				request.setAttribute("totalRecord", totalRecord); // select count(rcdNO) from board의 값을 반환함
				
				//모든 레코드 리스트를 추출하고 request 내장 객체의 속성으로 바인딩
				recordList = boardService.list(nowPage, pageRecords, col, key);  // 디비의 board 데이터들이 내림차순으로 저장되어 있음
				
				if (recordList.isEmpty()) recordList = null;
				request.setAttribute("recordList", recordList);
				System.out.println("Requested actCode : " + actCode);
				//RequestDispatcher를 이용해 출력할 문서 지정
				viewPage = "/boardList.jsp?col="+col+"&key="+key;
			}
			//레코드 입력 양식 문서 출력
			if (actCode.equals("/boardWriteForm.do")) {
				String col = request.getParameter("col");
				String key = request.getParameter("key");
				viewPage = "/boardWrite.jsp?col="+col+"&key="+key;
			}
			//레코드 입력 
			if (actCode.equals("/boardWrite.do")) {
				boardVO.setUserName(request.getParameter("userName"));
				boardVO.setUserMail(request.getParameter("userMail"));
				boardVO.setRcdSubject(request.getParameter("rcdSubject"));
				boardVO.setRcdContent(request.getParameter("rcdContent"));
				boardVO.setRcdPass(request.getParameter("rcdPass"));
				boardService.insert(boardVO);
				viewPage = "/boardList.do";
			}
			//레코드 내용 출력
			if (actCode.equals("/boardContent.do")) {
				String col = request.getParameter("col");
				String key = request.getParameter("key");
				String nowPage = request.getParameter("nowPage");
				
				int rNo = Integer.parseInt(request.getParameter("rcdNO"));
				boardVO = boardService.content(rNo);
				request.setAttribute("record", boardVO);
				viewPage = "/boardContent.jsp?rcdNO="+rNo+"&col="+col+"&key="+key + "&nowPage="+nowPage;
			}
			//답변 레코드 양식 문서 출력
			if (actCode.equals("/boardReplyForm.do")) {
				String col = request.getParameter("col");
				String key = request.getParameter("key");
				String nowPage = request.getParameter("nowPage");
				int rNo = Integer.parseInt(request.getParameter("rcdNO"));
				boardVO = boardService.parentRecord(rNo);
				request.setAttribute("parent", boardVO);
				viewPage = "/boardReply.jsp?rcdNO="+rNo+"&col="+col+"&key="+key+"&nowPage="+nowPage;
			}
			//답변 레코드 입력
			if (actCode.equals("/boardReply.do")) {
				String col = request.getParameter("col");  //제목 또는 내용 키워드 반환
				String key = request.getParameter("key"); // 검색어에 입력된 텍스트 반환
				String nowPage = request.getParameter("nowPage");
				
				int rNo = Integer.parseInt(request.getParameter("rcdNO"));  //
				
				boardVO.setUserName(request.getParameter("userName"));
				boardVO.setUserMail(request.getParameter("userMail"));
				boardVO.setRcdSubject(request.getParameter("rcdSubject"));
				boardVO.setRcdContent(request.getParameter("rcdContent"));
				boardVO.setRcdPass(request.getParameter("rcdPass"));
				
				boardService.replyRecord(boardVO, rNo);
				
				viewPage = "/boardList.do?col="+col+"&key="+key+"&nowPage="+nowPage;
			}
			//레코드 수정 양식 추출
			if(actCode.equals("/boardModifyForm.do")) {
				String col = request.getParameter("col");
				String key = request.getParameter("key");
				String nowPage = request.getParameter("nowPage");
				
				int rNo = Integer.parseInt(request.getParameter("rcdNO"));
				boardVO = boardService.modifyForm(rNo);
				request.setAttribute("record", boardVO);
				
				viewPage = "/boardModify.jsp?rcdNO="+rNo+"&col="+col+"&key="+key+"&nowPage="+nowPage;
			}
			//레코드 수정
			if(actCode.equals("/boardModify.do")) {
				String col = request.getParameter("col");
				String key = request.getParameter("key");
				String nowPage = request.getParameter("nowPage");
				
				int rNo = Integer.parseInt(request.getParameter("rcdNO"));
				String password = request.getParameter("rcdPass");
				
				boardVO.setUserMail(request.getParameter("userMail"));
				boardVO.setRcdSubject(request.getParameter("rcdSubject"));
				boardVO.setRcdContent(request.getParameter("rcdContent"));
				boardService.modify(rNo, password, boardVO);
				viewPage = "/boardContent.do?rcdNO="+rNo+"&col="+col+"&key="+key+"&nowPage="+nowPage;
			}
			// 레코드 삭제 양식 출력
			if (actCode.equals("/boardDeleteForm.do")) {
				String col = request.getParameter("col");
				String key = request.getParameter("key");
				String nowPage = request.getParameter("nowPage");
				
				int rNo = Integer.parseInt(request.getParameter("rcdNO"));
				
				boardVO = boardService.deleteForm(rNo);
				request.setAttribute("record", boardVO);
				
				viewPage = "/boardDelete.jsp?rcdNO="+rNo+"&col="+col+"&key="+key+"&nowPage="+nowPage;
			}
			if (actCode.equals("/boardDelete.do")) {
				String col = request.getParameter("col");
				String key = request.getParameter("key");
				String nowPage = request.getParameter("nowPage");
				
				int rNo = Integer.parseInt(request.getParameter("rcdNO"));
				String password = request.getParameter("rcdPass");
				
				boardService.delete(rNo, password);
				viewPage = "/boardList.do?col="+col+"&key="+key+"&nowPage="+nowPage;
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
		
	}

}
