<%@ page import="com.grepp.day0819board.model.BoardDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: kim-yechan
  Date: 2024. 8. 19.
  Time: 오후 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시글 목록</title>
</head>
<table border="1">
  <body>
    <%
    List<BoardDTO> bList = (List<BoardDTO>) request.getAttribute("bList");
    for (BoardDTO b : bList) {
  %>
  <tr>
    <td><%=b.getNo()%></td>
    <td><a href="<%=request.getContextPath()%>/board.do?action=view&no=<%=b.getNo()%>"><%=b.getTitle()%></a></td>
    <td><%=b.getWriter()%></td>
    <td><%=b.getRegDate()%></td>
    <td><%=b.getReadCount()%></td>
  </tr>
    <%
    }
  %>
</table>
<a href="<%=request.getContextPath()%>">[홈으로 가기]</a>
<a href="<%=request.getContextPath()%>/board.do?action=writeForm">[게시물 작성하러 가기]</a>
</body>
</html>
