<!DOCTYPE html>
<html>
<head>
    <title>Request Access</title>
</head>
<body>
    <h2>Request Access to Software</h2>
    <form action="RequestServlet" method="post">
        <!-- Dropdown for software name -->
        Software Name:
        <select name="softwareId" required>
            <!-- Dynamically populated with available software applications -->
            <c:forEach var="software" items="${softwareList}">
                <option value="${software.id}">${software.name}</option>
            </c:forEach>
        </select><br><br>

        <!-- Dropdown for access type -->
        Access Type:
        <select name="accessType" required>
            <option value="Read">Read</option>
            <option value="Write">Write</option>
            <option value="Admin">Admin</option>
        </select><br><br>

        <!-- Text area for reason -->
        Reason for Request:<br>
        <textarea name="reason" rows="4" cols="50" required></textarea><br><br>

        <input type="submit" value="Submit Request">
    </form>
</body>
</html>
