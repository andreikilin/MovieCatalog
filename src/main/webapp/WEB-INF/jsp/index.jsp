<%@ include file="common/header.jsp" %>

<table border = "1" cellpadding="7" cellspacing="0" width="100%" height="100%">
    <tr height = "15%">
        <td colspan="2" bgcolor="#D3EDF6" align="center">
            <a href="/moviecatalog/add/movie">add new movie</a>
        </td>
        <td colspan="2" bgcolor="#D3EDF6" align="center">
            <a href="/moviecatalog/movies">movies</a>
        </td>
        <td colspan="2" bgcolor="#D3EDF6" align="center">
            <a href="/moviecatalog/reviews">reviews</a>
        </td>
        <td colspan="2" bgcolor="#D3EDF6" align="center">
            <a href="/moviecatalog/genres">genres</a>
        </td>
        <td colspan="2" bgcolor="#D3EDF6" align="center">
            <a href="/moviecatalog/countries">countries</a>
        </td>
        <td colspan="2" bgcolor="#D3EDF6" align="center">
            <a href="/moviecatalog/users">users</a>
        </td>
        
    </tr>
    <tr>
    	<td valign="top" align="center" colspan = "13">
    	<c:forEach items="${listMovie}" var="item">
        	<a href="/moviecatalog/movie/${item.id}">${item.caption}</a>
        	<sec:authorize ifAnyGranted="ROLE_ADMIN">
        		<a href="/moviecatalog/edit/movie/${item.id}">edit</a>
        		<a href="/moviecatalog/delete/movie/${item.id}">delete</a>
        	</sec:authorize>
        	<br><br>
        </c:forEach>
        </td>
    </tr>
</table>

<%@ include file="common/footer.jsp" %>