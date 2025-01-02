<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
    <c:set var="col" value="${param.col }"></c:set>
    <c:set var="key" value="${param.key }"></c:set>
    <c:set var="nowPage" value="${param.nowPage}"></c:set>
    <%
		pageContext.setAttribute("newLine", "\r\n");
		pageContext.setAttribute("br", "<br>");
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 출력</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<table class="noline">
		<tr><td class="header">게시글 출력</td></tr>
	</table>
	<hr align="center" width="500" size="3" noshade="noshade">
	<table class="main">
		<tr>
			<td class="title2">작성자</td>
			<td class="content1" width="290">${record.userName }(${record.userMail})</td>
			<td class="title1" width="50">조회수</td>
			<td class="content1" width="50">${record.rcdRefer }</td>
		</tr>
		<tr>
			<td class="title2">제목</td>
			<td class="content2" colspan="3">${record.rcdSubject }</td>
		</tr>
		<tr>
			<td class="title2">내용</td>
			<td class="content2" colspan="3">${fn:replace(record.rcdContent, newLine, br)}</td>
		</tr>
	</table>
	<table align="center">
		<tr>
			<td width="80" align="left">
    			<input type="button" value="LIST" onclick="location.replace('${contextPath}/boardList.do?col=${col }&key=${key}&nowPage=${nowPage }')"> <!-- list 버튼을 클릭했을때 BoardController에 /boardList.do 요청과 함께 페이지 번호를 추가해 전달 -->
			</td>
			<td width="400" align="right">
				<input type="button" value="REPLY" onclick="location.replace('${contextPath}/boardReplyForm.do?rcdNO=${record.rcdNO }&col=${col }&key=${key }&nowPage=${nowPage}')"> <!-- reply 버튼을 클릭했을때 BoardController에 /boardReplyForm.do 요청과 함께 페이지 번호를 추가해 전달 -->
				<input type="button" value="MODIFY" onclick="location.replace('${contextPath}/boardModifyForm.do?rcdNO=${record.rcdNO }&col=${col }&key=${key }&nowPage=${nowPage}')"> <!-- modify 버튼을 클릭했을때 BoardController에 /boardModifyForm.do 요청과 함께 페이지 번호를 추가해 전달 -->
				<input type="button" value="DELETE" onclick="location.replace('${contextPath}/boardDeleteForm.do?rcdNO=${record.rcdNO }&col=${col }&key=${key }&nowPage=${nowPage}')"> <!-- delete 버튼을 클릭했을때 BoardController에 /boardDeleteForm.do 요청과 함께 페이지 번호를 추가해 전달 -->
			</td>
		</tr>
	</table>
</body>
</html>