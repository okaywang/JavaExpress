<%--
  Created by IntelliJ IDEA.
  User: wgj
  Date: 2017/2/12
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <script>
      function test() {
          alert(333);
          window.location.href="/third.jsp";
      }
      alert(999999);
    </script>
    <title>$Title$</title>
  </head>
  <body>
  my jsp page:
  <%=session.getAttribute("server")%>
  <input type="button" value="login&auth" onclick="test()" />
  </body>
</html>
