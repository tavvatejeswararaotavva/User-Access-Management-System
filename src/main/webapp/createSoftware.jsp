
<!DOCTYPE html>
<html>
<head>
    <title>Create Software</title>
</head>
<body>
    <h2>Create New Software</h2>
    <form action="SoftwareServlet" method="post">
        Software Name: <input type="text" name="name" required><br><br>
        Description:<br>
        <textarea name="description" rows="4" cols="50"></textarea><br><br>
        Access Levels:<br>
        <input type="checkbox" name="accessLevel" value="Read"> Read<br>
        <input type="checkbox" name="accessLevel" value="Write"> Write<br>
        <input type="checkbox" name="accessLevel" value="Admin"> Admin<br><br>
        <input type="submit" value="Add Software">
    </form>
</body>
</html>
