<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="col" value="${param.col }"></c:set>
<c:set var="key" value="${param.key }"></c:set>
<c:set var="rNo" value="${param.rcdNO }"></c:set>
<c:set var="nowPage" value="${param.nowPage}"></c:set>  <!-- 전달된 페이지번호를 추출합니다 -->
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
		<tr><td class="header">게시글 답변 입력</td></tr>
	</table>
	<hr align="center" width="500" size="3" noshade="noshade">
	<table class="main">
		<tr>
			<td class="title2">원글 제목</td>
			<td class="content2">${parent.rcdSubject }</td>
		</tr>
		<tr>
			<td class="title2">원글 내용</td>
			<td class="content2">${fn:replace(parent.rcdContent, newLine, br)}</td>
		</tr>
	</table>
	<br>
	<form name="boardWrite" method="post" action="${contextPath }/boardReply.do?rcdNO=${rNo}&col=${col}&key=${key}&nowPage=${nowPage}"> <!-- submit이 발생했을때 BoardController에 /boardReply.do 요청과 함께 페이지 번호를 추가해 전달한다.-->
			<table class="main">
			<tr>
				<td class="title2">작성자</td>
				<td class="content2">
					<input type="text" name="userName" size="10" required="required">
				</td>
			</tr>
			
			<tr>
				<td class="title2">이메일</td>
				<td class="content2">
					<input type="email" name="userMail" size="30">
				</td>
			</tr>
			
			<tr>
				<td class="title2">제목</td>
				<td class="content2">
					<input type="text" name="rcdSubject" required="required">
				</td>
			</tr>
			
			<tr>
				<td class="title2">내용</td>
				<td class="content2">
					<textarea rows="10" cols="50" name="rcdContent"></textarea>
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
					<input type="button" value="LIST" onclick="location.replace('${contextPath}/boardList.do?col=${col }&key=${key }&nowPage=${nowPage}')"> <!-- list 버튼을 클릭했을때 BoardController에 /boardList.do 요청과 함꼐 페이지 번호를 추가해 전달합니다. -->
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