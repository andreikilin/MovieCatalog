<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table border="1" cellpadding="7" cellspacing="0" width="100%" height="100%">
    <tr height = "15%">
        <td colspan="2" bgcolor="#D3EDF6" align="center">
            ${loggedUser} <a href="/moviecatalog/logout">logout</a>
        </td>
    </tr>
    <tr height = "25%">
        <td valign="top" align="center">${paramMovies}</td>
    </tr>
    <tr>
        <td valign="top" align="center">${list}</td>
    </tr>
</table>