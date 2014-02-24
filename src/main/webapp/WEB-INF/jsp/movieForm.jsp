<%@ include file="common/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form method="POST" action="${pageContext.request.contextPath}/${movieFormAction}" commandName="editMovie">
    <b>Add new movie</b>
    <br>
    <br>Name:<br>
    <form:textarea path="name" cols="50" rows="4"/>
    <form:hidden path="id"/>
    <span class="error"><form:errors path="name"/></span>
    <Br>
    <br>Country:<br>
    <form:select size="1" path="countryId" >
    	<form:options items="${countryList}" itemLabel="name" itemValue="id"/>
    </form:select>
    
    <br>
    <br>Genre:<br>
    <form:select size="1" path="genreId" >
    	<form:options items="${genreList}" itemLabel="name" itemValue="id"/>
    </form:select>
    <br>
    <br>
    Year
    <br>
    <form:select path="year" items="${yearList}" />
    <br>
    <br>
    Starring
    <br>
    <form:textarea path="starring" cols="50" rows="4"/>
    <span class="error"><form:errors path="starring"/></span>
    <br>
    <br>
    Description
    <br>
    <form:textarea path="description" cols="50" rows="4"/>
    <span class="error"><form:errors path="description"/></span>
    <br><br>

    <input type="submit" value="Save">
</form:form>   

<%@ include file="common/footer.jsp" %>