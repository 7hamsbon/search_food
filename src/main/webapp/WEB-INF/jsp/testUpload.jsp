<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/2/19
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<form method="POST" enctype="multipart/form-data"  action="/upload">
    File to upload: <input type="file" name="file"><br />
    Name: <input  type="text" name="member" value="hello"><br /> <br />
    <input type="submit"  value="Upload"> Press here to upload the file!
</form>
</body>
</html>