<!DOCTYPE html>
<html>
<head>
    <title>Pending Access Requests</title>
</head>
<body>
    <h2>Pending Access Requests</h2>
    <form action="ApprovalServlet" method="post">
        <table border="1">
            <tr>
                <th>Request ID</th>
                <th>Employee Name</th>
                <th>Software Name</th>
                <th>Access Type</th>
                <th>Reason for Request</th>
                <th>Action</th>
            </tr>
            <c:forEach var="request" items="${pendingRequests}">
                <tr>
                    <td>${request.id}</td>
                    <td>${request.employeeName}</td>
                    <td>${request.softwareName}</td>
                    <td>${request.accessType}</td>
                    <td>${request.reason}</td>
                    <td>
                        <button type="submit" name="action" value="approve_${request.id}">Approve</button>
                        <button type="submit" name="action" value="reject_${request.id}">Reject</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</body>
</html>
