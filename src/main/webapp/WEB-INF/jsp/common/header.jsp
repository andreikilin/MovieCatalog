<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<style type="text/css">
	.error { color: red;}
</style>
</head>
<body>
<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
	<h5 align="right"><a href="${pageContext.request.contextPath}/registration">Register</a> 
						or <a href="${pageContext.request.contextPath}/login">login</a> </h5>
</sec:authorize>

<sec:authentication var="principal" property="principal" />
<sec:authorize ifAllGranted="ROLE_USER">
	<h5 align="right">Hi, ${principal.username} | <a href="<c:url value="/j_spring_security_logout" />" >Logout</a></h5>
 	<h5 align="right"><a href="${pageContext.request.contextPath}/profile.html">Profile</a></h5>
</sec:authorize>