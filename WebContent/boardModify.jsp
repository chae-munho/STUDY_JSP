<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>\
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
    <c:set var="col" value="${param.col}"></c:set>
    <c:set var="key" value="${param.key }"></c:set>
    
    <c:set var="nowPage" value="${param.nowPage }"></c:set>  <!-- 전달된 페이지 번호를 추출 -->
    <c:set var="rNo" value="${param.rcdNO }"></c:set>
    
    <%
    	pageContext.setAttribute("newLine", "\r\n");
    	pageContext.setAttribute("br", "<br>");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<table class="noline">
		<tr><td class="header">게시글 수정</td></tr>
	</table>
	<hr>
	<form action="${contextPath }/boardModify.do?rcdNO=${rNo}&col=${col}&key=${key}&nowPage=${nowPage}" name="boardModify" method="post"> <!-- submit이 발생했을때 BoardController에 /boardReply.do 요청과 함께 페이지 번호를 추가해 전달 -->
		<table class="main">
			<tr>
				<td class="title2">작성자</td>
				<td class="content2">${record.userName }</td>
			</tr>
			<tr>
				<td class="title2">이메일</td>
				<td class="content2">
					<input type="email" name="userMail" size="30" value="${record.userMail }">
				</td>
			</tr>
			<tr>
				<td class="title2">제목</td>
				<td class="content2">
					<input type="text" name="rcdSubject" size="45" required="required" value="${record.rcdSubject }">
				</td>
			</tr>
			<tr>
				<td class="title2">내용</td>
				<td class="content2">
					<textarea rows="10" cols="50" name="rcdContent">${record.rcdContent }</textarea>
				</td>
			</tr>
			<tr>
				<td class="title2">패스워드</td>
				<td class="content2">
					<input type="password" name="rcdPass" size="10" required="required">
				</td>
			</tr>
		</table>
		<table align="center">
			<tr>
				<td width="80" align="left">
					<input type="button" value="LIST" onclick="location.replace('${contextPath}/boardList.do?col=${col}&key=${key }&nowPage=${nowPage }')"> <!-- List 버튼을 추가했을때 BoardController에 /boardList.do 요청과 함께 페이지 번호를 추가해 전달 -->
				</td>
				<td width="400" align="right">
					<input type="submit" value="SAVE">
					<input type="reset" value="CANCEL">
				</td>
			</tr>
		</table>
	
	
	</form>

</body>
</html>