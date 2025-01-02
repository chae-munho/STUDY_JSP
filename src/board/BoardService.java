package board;
import java.util.List;
//BoardService 클래스는 BoardDAO 클래스의 메서드를 호출하기 때문에 생성자를 사용해 boardDAO 객체를 생성한다.
public class BoardService {
	BoardDAO boardDAO;
	
	//BoardService() 생성자를 이용한 BoardDAO의 객체 생성
	public BoardService() {
		boardDAO = new BoardDAO();
	}
	//recordCount() : boardDAO의 totalRecord()를 호출해 전체 레코드 수를 전달 받음
	//col : 제목, 내용 선택 key : 검색 텍스트 입력란
	public int recordCount(String col, String key) {
		int totalRecord = boardDAO.totalRecord(col, key);
		return totalRecord;
	}
	//list() : boardDAO의 selectAllRecords()를 호출해 레코드 필드의 리스트를 전달 받음
	public List<BoardVO> list(int nowPage, int pageRecords, String col, String key) {
		List<BoardVO> recordList = boardDAO.selectAllRecords(nowPage, pageRecords, col, key);
		return recordList;
	}
	//boardWirte.jsp 문서에 입력되는 파라미터는 userName, userMail, rcdSubject, rcdContent, rcdPass 필드에 입력될 값. 이 값들은 BoardController를 통해서 전달되지만, 나머지의 rcdNO, rcdGrpNO, rcdRefer, rcdDate, rcdIndent, rcdOrder 필드에 저장될 값은 BoardController에서 전달되지 않으므로 boardService()의 insert() 메서드에서 결정
	//insert() : 새로 입력되는 레코드의 나머지 필드 값을 지정해 insertRecord()에 전달
	public void insert(BoardVO boardVO) {
		int newNO = boardDAO.newRcdNO();
		
		boardVO.setRcdNO(newNO);
		boardVO.setRcdGrpNO(newNO);
		boardVO.setRcdRefer(0);
		boardVO.setRcdIndent(0);
		boardVO.setRcdOrder(1);
		
		boardDAO.insertRecord(boardVO);
	}
	//content() : boardDAO의 incrementRcdRefer()와 selectRecord()를 호출
	public BoardVO content(int rNo) {
		boardDAO.incrementRcdRefer(rNo);
		BoardVO record = boardDAO.selectRecord(rNo);
		return record;
	}
	//parentRecord() : 답변 글 작성 문서 상단에 출력할 원글 레코드 제목과 내용 추출
	public BoardVO parentRecord(int rNo) {
		BoardVO parent = boardDAO.selectParentRecord(rNo);
		return parent;
	}
	//replyRecord() : 답변 글 입력
	public void replyRecord(BoardVO boardVO, int rNo) {
		int newNO = boardDAO.newRcdNO();
		
		BoardVO replyInfo = boardDAO.getParentField(rNo);
		int rGrpNo = replyInfo.getRcdGrpNO();
		int rIndent = replyInfo.getRcdIndent();
		int nOrder = replyInfo.getRcdOrder();
		
		int newRcdIndent = rIndent + 1;
		int newRcdOrder = nOrder + 1;
		
		boardDAO.increRcdOrder(rGrpNo, nOrder);
		boardVO.setRcdNO(newNO);
		boardVO.setRcdGrpNO(rGrpNo);
		boardVO.setRcdRefer(0);
		boardVO.setRcdIndent(newRcdIndent);
		boardVO.setRcdOrder(newRcdOrder);
		
		boardDAO.insertRecord(boardVO);
	}
	//modifyRecord() : 수정할 레코드의 내용 추출
	public BoardVO modifyForm(int rNo) {
		//boarDAO의 selectRecord() 메서드를 호출해 수정할 레코드를 반환받은 다음 BoardController에 반환한다.
		BoardVO record = boardDAO.selectRecord(rNo);
		return record;
	}
	//modify() : 패스워드가 일치하는지 조사하고 일치할 경우 레코드를 수정
	public void modify(int rNo, String password, BoardVO boardVO) {
		//checkPassword() 메서드를 실행해 패스워드가 일치하는지 조사. 패스워드가 서로 일치할 경우 BoardDAO의 modifyRecord() 메서드를 호출
		if (boardDAO.checkPassword(rNo, password)) {
			boardDAO.modifyRecord(rNo, boardVO);
		}
	}
	//deleteForm() : 삭제할 레코드를 출력하고 패스워드를 입력 받음
	public BoardVO deleteForm(int rNo) {
		// BoardDAO의 selectRecord() 메서드를 호출해 삭제하고자 하는 레코드를 추출하고 BoardController에 반환
		BoardVO record = boardDAO.selectRecord(rNo);
		return record;
	}
	//delete() : 패스워드가 일치하는지 조사하고 일치할 경우 레코드 삭제
	public void delete(int rNo, String password) {
		//BoardDAO의 checkPassword() 메서드로 조사한 패스워드가 일치할 경우 deleteRecord() 메서드를 호출해 레코드를 삭제
		if (boardDAO.checkPassword(rNo, password)) {
			boardDAO.deleteRecord(rNo);
		}
	}
}
