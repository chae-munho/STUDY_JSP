<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	//getContextPath() 메서드를 이용해 컨텍스트 이름을 추출
	String path = request.getContextPath();
	
	response.sendRedirect(path + "/boardList.do");
%>


</body>
</html>