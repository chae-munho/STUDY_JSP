<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 리스트</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <table class="noline">
        <tr><td class="header">게시글 리스트 출력</td></tr>
    </table>
    <hr align="center" width="610" size="3" noshade="noshade">
    
    <table class="main">
        <tr align="center">
            <td class="title1" width="30">번호</td>
            <td class="title1" width="350">글제목</td>
            <td class="title1" width="60">작성자</td>
            <td class="title1" width="70">작성일</td>
            <td class="title1" width="40">조회수</td>
        </tr>
        
        <!-- 게시글 출력 -->
        <c:choose>
            <c:when test="${recordList == null}">
                <tr align="center">
                    <td colspan="5">등록된 글이 없습니다.</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="record" items="${recordList}" varStatus="status">
                    <tr>
                        <!-- 게시글 번호 -->
                        <td class="content1">${totalRecord - (nowPage - 1) * pageRecords - (status.index)}</td>
                        <!-- 제목 -->
                        <td class="content1-left">
                        	<c:forEach begin="1" end="${record.rcdIndent }">
                        		&nbsp;
                        	</c:forEach>
                            <a href="${contextPath}/boardContent.do?rcdNO=${record.rcdNO}&col=${col}&key=${key}&nowPage=${nowPage}">
                                ${record.rcdSubject}
                            </a>
                        </td>
                        <!-- 작성자 -->
                        <td class="content1">${record.userName}</td>
                        <!-- 작성일 -->
                        <td class="content1">${record.rcdDate}</td>
                        <!-- 조회수 -->
                        <td class="content1">${record.rcdRefer}</td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </table>

    <!-- 검색 및 페이징 -->
    <form name="search" method="post" action="${contextPath}/boardList.do">
        <table align="center">
            <tr>
                <td align="left" width="50">
                    <input type="button" value="WRITE" 
                           onclick="location.replace('${contextPath}/boardWriteForm.do?col=${col}&key=${key}&nowPage=${nowPage}')">
                </td>
                <td class="links" width="320">
                	<c:if test="${totalRecord > 0}">
				    <!-- 이전 페이지 세트 -->
				    <c:choose>
				        <c:when test="${nowPage > pageSets}">
				            <c:set var="beforeSetPage" value="${nowPage - pageSets}" />
				            <a href="${contextPath}/boardList.do?nowPage=${beforeSetPage}&col=${col}&key=${key}">[PreSet]</a>
				        </c:when>
				        <c:otherwise>
				            [PreSet]
				        </c:otherwise>
				    </c:choose>
				
				    <!-- 이전 페이지 -->
				    <c:choose>
				        <c:when test="${nowPage > 1}">
				            <c:set var="beforePage" value="${nowPage - 1}" />
				            <a href="${contextPath}/boardList.do?nowPage=${beforePage}&col=${col}&key=${key}">[Pre]</a>
				        </c:when>
				        <c:otherwise>
				            [Pre]
				        </c:otherwise>
				    </c:choose>
				
				    <!-- 페이지 번호 -->
				    <c:forEach var="page" begin="1" end="${(totalRecord / pageRecords) + (totalRecord % pageRecords > 0 ? 1 : 0)}">
				        <c:choose>
				            <c:when test="${page == nowPage}">
				                <b>${page}</b>
				            </c:when>
				            <c:otherwise>
				                <a href="${contextPath}/boardList.do?nowPage=${page}&col=${col}&key=${key}">${page}</a>
				            </c:otherwise>
				        </c:choose>
				    </c:forEach>
				
				    <!-- 다음 페이지 -->
				    <c:choose>
				        <c:when test="${nowPage < (totalRecord / pageRecords) + (totalRecord % pageRecords > 0 ? 1 : 0)}">
				            <c:set var="nextPage" value="${nowPage + 1}" />
				            <a href="${contextPath}/boardList.do?nowPage=${nextPage}&col=${col}&key=${key}">[Next]</a>
				        </c:when>
				        <c:otherwise>
				            [Next]
				        </c:otherwise>
				    </c:choose>
				
				    <!-- 다음 페이지 세트 -->
				    <c:choose>
				        <c:when test="${(nowPage + pageSets) <= (totalRecord / pageRecords) + (totalRecord % pageRecords > 0 ? 1 : 0)}">
				            <c:set var="nextSetPage" value="${nowPage + pageSets}" />
				            <a href="${contextPath}/boardList.do?nowPage=${nextSetPage}&col=${col}&key=${key}">[NextSet]</a>
				        </c:when>
				        <c:otherwise>
				            [NextSet]
				        </c:otherwise>
				    </c:choose>
				</c:if>
                	
                	
					
                </td>
                <td align="right" width="220">
                    <select name="col">
                        <option value="rcdSubject" ${col == 'rcdSubject' ? 'selected' : ''}>제목</option>
                        <option value="rcdContent" ${col == 'rcdContent' ? 'selected' : ''}>내용</option>
                    </select>
                    <input type="text" name="key" size="10" value="${key}">
                    <input type="submit" value="검색">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
