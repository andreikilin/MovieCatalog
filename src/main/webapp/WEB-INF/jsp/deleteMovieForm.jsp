<%@ include file="common/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<table border="1" width="400" cellpadding="7" cellspacing="0" align="center">
	<form:form method="POST" action="${pageContext.request.contextPath}/${movieFormAction}" commandName="deleteMovie">    
    <tr>
        <td valign="top" align="center">${name}</td>
    </tr>
    <tr>
		<td>${genre}</td>    
    </tr>
    <tr>
    	<td>${year}</td>
    </tr>
    <tr>
    	<td>${staring}</td>
    </tr>
    <tr>
    	<td>${country}</td>
    </tr>
    <tr>
    	<td>${description}</td>
    </tr>
    <tr>
    	<td align="center"><input type="submit" value="Delete"></td>
    </tr>
	</form:form>    
</table>
	
    
   

<%@ include file="common/footer.jsp" %>