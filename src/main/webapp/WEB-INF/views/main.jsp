<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<a href="<%=request.getContextPath()%>/board.do?action=list">[목록보기]</a>
<a href="<%=request.getContextPath()%>/board.do?action=writeForm">[작성하러가기]</a>
<%
    String longId = (String) session.getAttribute("loginId");
    if (longId == null) {
%>
<a href="<%=request.getContextPath()%>/login.do?action=login">[로그인 하러가기]</a>
<%
}else{ // 이미 로그인 내역이 있네?
%>
<br><%=longId%> 님 반갑습니다!<br>
    <a href="<%=request.getContextPath()%>/login.do?action=logout">[로그아웃]</a>
        <%
        }
    %>
</body>
</html>