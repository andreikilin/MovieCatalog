<%@ include file="common/header.jsp" %>

<table border="1" cellpadding="7" cellspacing="0" align="center">
    
    <tr>
        <td valign="top" align="center">${name}</td>
    </tr>
    <tr>
		<td>${genre} ${year} ${Staring} ${country}</td>    
    </tr>
    
    <tr>
    	<td>${description}</td>
    </tr>
</table>

<%@ include file="common/footer.jsp" %>