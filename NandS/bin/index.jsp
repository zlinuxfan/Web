<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 19.08.2016
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>index.jsp</title>
  </head>
  <body>
  <% java.util.Date d = new java.util.Date(); %>
<h1>
Today's date is <%= d.toString() %> and this jsp page worked!
</h1>
  </body>
</html>
