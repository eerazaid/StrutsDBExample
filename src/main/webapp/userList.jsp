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
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styles.css">
    <script type="text/javascript">
        function confirmDelete() {
            return confirm("Are you sure you want to delete this user?");
        }
    </script>
</head>
<body>
<div class="wrapper">
    <div class="list">
        <div class="title">
            User List
        </div><br>
        
	     

        <table>
            <thead>
                <tr>
                    <th>Login ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Password</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<UserRegisterForm> users = (List<UserRegisterForm>) request.getAttribute("userList");
                if (users != null && !users.isEmpty()) {
                    for (UserRegisterForm user : users) {
            %>
            <tr>
                <td><%= user.getLoginId() %></td>
                <td><%= user.getFirstName() %></td>
                <td><%= user.getLastName() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.getPassword() %></td>
                <td class="actions">
				    <div class="button-group">
				        <form action="updateUser.do" method="get" class="inline-form">
				            <input type="hidden" name="id" value="<%= user.getId() %>"/>
				            <input type="submit" value="Edit" class="submit_btn"/>
				        </form>
				        <form action="deleteUser.do" method="post" class="inline-form" onsubmit="return confirmDelete();">
				            <input type="hidden" name="id" value="<%= user.getId() %>"/>
				            <input type="submit" value="Delete" class="submit_btn delete_btn"/>
				        </form>
				    </div>
				</td>

            </tr>
            <%
                    }
                } else {
                    out.println("<tr><td colspan='6'>No users found.</td></tr>");
                }
            %>
            </tbody>
        </table>
        <div class="button-container" style="text-align: right;">
            <form action="addUser.jsp" method="get">
                <input type="submit" value="Add New User" class="submit_btn"/>
            </form>
        </div>
        
    </div>
</div>
</body>
</html>
