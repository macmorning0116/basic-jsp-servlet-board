<%--
  Created by IntelliJ IDEA.
  User: kim-yechan
  Date: 2024. 8. 19.
  Time: 오후 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
//  int no = (Integer) request.getAttribute("no");
    int no = Integer.parseInt(request.getParameter("no"));
%>

<form action="<%=request.getContextPath()%>/board.do?action=update&no=<%=no%>" method="post">
  <input type="text" placeholder="수정할 제목을 입력하세요." id="title" name="tttt"><br>
  <textarea placeholder="수정할 내용을 입력하세요." name="cccc"></textarea><br>
  <input type="submit" value="수정완료">
</form>

</body>
</html>
