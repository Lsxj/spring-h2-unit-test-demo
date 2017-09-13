<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>your score</title>
</head>
<body>
<form action="playBowling" method="post" style="text-align: center;;">
    <p>Your pins record are: ${data}</p>
    <p>Your score is ${score}~ </p>
    <input type="hidden" name="pins" value="${data}" />
    <input type="hidden" name="score" value="${score}" />
    <p>Do you want to save? <input type="submit" value="save" name="saveButton"/></p>
</form>
</body>
</html>
