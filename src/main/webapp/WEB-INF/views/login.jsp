<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/login.do" method="post">
  ID : <label>
  <input type="text" name="userId" value="<%
        Cookie[] cookies = request.getCookies();
        String savedId = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("loginCheck".equals(cookie.getName())) {
                    savedId = cookie.getValue();
                    break;
                }
            }
        }
        out.print(savedId);
    %>"/>
</label><br>
  PW : <label>
  <input type="password" name="userPw"/>
</label><br>
  <input type="submit" value="로그인"/>
  <input type="checkbox" id="rememberMe" name="rememberMe"
          <%
            if (cookies != null) {
              boolean rememberChecked = false;
              for (Cookie cookie : cookies) {
                if ("loginCheck".equals(cookie.getName())) {
                  rememberChecked = true;
                  break;
                }
              }
              if (rememberChecked) {
                out.print("checked");
              }
            }
          %> />
  <label for="rememberMe">아이디 기억하기</label><br>
</form>
<a href="<%=request.getContextPath()%>/main.do">[홈으로 가기]</a>
</body>
</html>
