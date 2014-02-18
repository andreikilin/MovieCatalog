<%@ include file="common/header.jsp" %>

<c:if test="${not empty param.error}">
    <font color="red"> loginerror
        : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </font>
</c:if>
<form method="POST" action="<c:url value="/j_spring_security_check" />">
    <table>
        <tr>
            <td align="right">login</td>
            <td><input type="text" name="j_username" /></td>
        </tr>
        <tr>
            <td align="right">password</td>
            <td><input type="password" name="j_password" /></td>
        </tr>
        <tr>
            <td align="right">remember</td>
            <td><input type="checkbox" name="_spring_security_remember_me" /></td>
        </tr>
        <tr>
            <td colspan="2" align="right"><input type="submit" value="Login" />
                <input type="reset" value="Reset" /></td>
        </tr>
    </table>
</form>

<%@ include file="common/footer.jsp" %>

