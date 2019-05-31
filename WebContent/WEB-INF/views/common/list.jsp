<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<c:forEach items="${commentsList}" var="comments">
		
			<tr>
				<td>${comments.name}</td>
				<td id="c${comments.cnum}">${comments.contents}</td>
				<td>${comments.reg_date}</td>
				<c:if test="${session.name eq comments.name}">
				<td><button class="upBtn" title="${comments.cnum}" data-toggle="modal" data-target="#myModal">Update</button><button class="delete" id="${comments.cnum}">Delete</button></td>
				</c:if>
			</tr>
			
		</c:forEach>
		
