<%@ include file="common/header.jsp" %>

<table border="1" cellpadding="7" cellspacing="0" width="100%" height="100%">
    <tr height = "15%">
        <td colspan="2" bgcolor="#D3EDF6" align="center">
            ${loggedUser} <a href="/moviecatalog/logout">logout</a>
        </td>
    </tr>
    <tr height = "25%">
        <td valign="top" align="center">${paramReviews}</td>
    </tr>
    <tr>
        <td valign="top" align="center">${listReviews}</td>
    </tr>
</table>

<%@ include file="common/footer.jsp" %>