<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="<%=application.getContextPath()%>/index.do">WebSiteName</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="<%=application.getContextPath()%>/notice/noticeList">Notice</a></li>
      <li class="active"><a href="<%=application.getContextPath()%>/qna/qnaList">Qna<span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Page 1-1</a></li>
          <li><a href="#">Page 1-2</a></li>
          <li><a href="#">Page 1-3</a></li>
        </ul>
      </li>
      <li><a href="#">Page 2</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
    <c:if test="${session != null }">
      <li><a href="${pageContext.request.contextPath}/member/myPage"><span class="glyphicon glyphicon-user"></span> My Page</a></li>
      <li><a href="${pageContext.request.contextPath}/member/memberLogout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
    </c:if>
    <c:if test="${session == null}">
    <li><a href="${pageContext.request.contextPath}/member/memberCheck"><span class="glyphicon glyphicon-user"></span> Join</a></li>
      <li><a href="${pageContext.request.contextPath}/member/memberLogin""><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
    </c:if>
    </ul>
  </div>
</nav>

