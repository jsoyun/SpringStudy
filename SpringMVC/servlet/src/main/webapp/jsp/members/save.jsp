<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %><%--
  Created by IntelliJ IDEA.
  User: soyunlee
  Date: 2023/11/10
  Time: 3:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //request, response 그대로 사용가능 .jsp 는 서블릿으로 자동변환되기에 두개는 그냥 쓸 수 있다.
    MemberRepository memberRepository = MemberRepository.getInstance();    String username = request.getParameter("username");
    int age=Integer.parseInt(request.getParameter("age"));
    Member member = new Member(username, age);
    memberRepository.save(member);

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공save.jsp
<ul>
    <li>id<%= member.getId()%></li>
    <li>username<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
