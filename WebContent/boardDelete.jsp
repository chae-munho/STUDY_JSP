<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
	<c:set var="col" value="${param.col }"></c:set>
	<c:set var="key" value="${param.key }"></c:set>
	<c:set var="rNo" value="${param.rcdNO }"></c:set>
	<c:set var="nowPage" value="${param.nowPage }"></c:set>
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
		<tr><td class="header">게시글 삭제</td></tr>
	</table>
	<hr align="center" width="500" size="3" noshade="noshade">
	<form name="boardDelete" method="post" action="${contextPath }/boardDelete.do?rcdNO=${rNo}&col=${col}&key=${key}&nowPage=${nowPage}">
		<table class="main">
			<tr>
				<td class="title2">제목</td>
				<td class="content2">${record.rcdSubject}</td>	
			</tr>
			<tr>
				<td class="title2">내용</td>
				<td class="content2">${fn:replace(record.rcdContent, newLine, br)}</td>	
			</tr>
			
			<tr>
				<td class="title2">패스워드</td>
				<td class="content2">
					<input type="password" name="rcdPass" size="20" required="required">
				</td>	
			</tr>
		</table>
		<table align="center">
			<tr>
				<td width="80" align="left">
					<input type="button" value="LIST" onclick="location.replace('${contextPath}/boardList.do?col=${col }&key=${key }&nowPage=${nowPage}')">
				</td>
				<td width="400" align="right">
					<input type="submit" value="DELETE">
					<input type="reset" value="CANCEL">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>