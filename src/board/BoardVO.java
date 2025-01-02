package board;
import java.util.Date;
public class BoardVO {
	private int rcdNO;
	private int rcdGrpNO;
	private String userName;
	private String userMail;
	private String rcdSubject;
	private String rcdContent;
	private String rcdPass;
	private Date rcdDate;
	private int rcdRefer;
	private int rcdIndent;
	private int rcdOrder;
	
	//rcdNO 변수의 Getter Setter
	public int getRcdNO() {
		return rcdNO;
	}
	public void setRcdNO(int rcdNO) {
		this.rcdNO = rcdNO;
	}
	public int getRcdGrpNO() {
		return rcdGrpNO;
	}
	public void setRcdGrpNO(int rcdGrpNO) {
		this.rcdGrpNO = rcdGrpNO;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getRcdSubject() {
		return rcdSubject;
	}
	public void setRcdSubject(String rcdSubject) {
		this.rcdSubject = rcdSubject;
	}
	public String getRcdContent() {
		return rcdContent;
	}
	public void setRcdContent(String rcdContent) {
		this.rcdContent = rcdContent;
	}
	public String getRcdPass() {
		return rcdPass;
	}
	public void setRcdPass(String rcdPass) {
		this.rcdPass = rcdPass;
	}
	public Date getRcdDate() {
		return rcdDate;
	}
	public void setRcdDate(Date rcdDate) {
		this.rcdDate = rcdDate;
	}
	public int getRcdRefer() {
		return rcdRefer;
	}
	public void setRcdRefer(int rcdRefer) {
		this.rcdRefer = rcdRefer;
	}
	public int getRcdIndent() {
		return rcdIndent;
	}
	public void setRcdIndent(int rcdIndent) {
		this.rcdIndent = rcdIndent;
	}
	public int getRcdOrder() {
		return rcdOrder;
	}
	public void setRcdOrder(int rcdOrder) {
		this.rcdOrder = rcdOrder;
	}
	
	
	


}
