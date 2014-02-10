<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MOVIE CATALOG</title>
</head>
<body>
<table border="1" cellpadding="7" cellspacing="0" width="100%" height="100%">
    <tr height = "15%">
        <td colspan="2" bgcolor="#D3EDF6" align="center">
            ${loggedUser} <a href="/moviecatalog/logout">logout</a>
        </td>
    </tr>
    <tr>
        <td valign="top" align="center">${movie}</td>
    </tr>
</table>
</body>
</html>