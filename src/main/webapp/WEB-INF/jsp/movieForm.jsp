<%@ include file="common/header.jsp" %>

<form action="${movieFormAction}">
    <b>Add new movie</b>
    <br>
    <br>Name:<br>
    <textarea name="name" cols="50" rows="4">${name}</textarea>
    <Br>
    <br>Country:<br>
    <select size="1" name="country" required>
        ${countriesList}
    </select>
    <br>
    <br>Genre:<br>
    <select size="1" name="genre" required>
        ${genresList}
    </select>
    <br>
    <br>
    Year
    <br>
    <input type = "text" size = 4 pattern = [0-9]{4} value="${year}">
    <br>
    <br>
    Starring
    <br>
    <textarea name="starring" cols="50" rows="4">${starring}</textarea>
    <br>
    <br>
    Description
    <br>
    <textarea name="description" cols="50" rows="4">${description}</textarea>
    <br><br>

    <input type="submit" value = "${buttonAction}">
</form>   </tr>

<%@ include file="common/footer.jsp" %>