<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jwt.struts.form.UserRegisterForm" %>

<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
</head>

<script type="text/javascript">
        function confirmDelete() {
            return confirm("Are you sure you want to delete this user?");
        }
    </script>
<body>
    <h2>User List</h2>
    
	<form action="addUser.jsp" method="get">
		<input type="submit" value="Add new user">
	</form><br>
    
    <table border="1">
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Password</th>
            <th>Actions</th>
        </tr>
        <%
            List<UserRegisterForm> users = (List<UserRegisterForm>) request.getAttribute("userList");
            if (users != null && !users.isEmpty()) {
                for (UserRegisterForm user : users) {
        %>
        <tr>
            <td><%= user.getId() %></td>
            <td><%= user.getFirstName() %></td>
            <td><%= user.getLastName() %></td>
            <td><%= user.getEmail() %></td>
            <td><%= user.getPassword() %></td>
            <td>
                <form action="updateUser.do" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= user.getId() %>"/>
                    <input type="submit" value="Edit"/>
                </form>
                <form action="deleteUser.do" method="post" style="display:inline;" onsubmit="return confirmDelete();">
		            <input type="hidden" name="id" value="<%= user.getId() %>"/>
		            <input type="submit" value="Delete"/>
		        </form>
            </td>
        </tr>
        <%
                }
            } else {
                out.println("<tr><td colspan='5'>No users found.</td></tr>");
            }
        %>
    </table>
    <br>

    
</body>
</html>
