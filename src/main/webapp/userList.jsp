<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jwt.struts.form.UserRegisterForm" %>

<!DOCTYPE html>
<html>
<head>
    <title>User List test 123</title>
</head>
<body>
    <h2>User List</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
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
            <td>
                <form action="updateUser.do" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= user.getId() %>"/>
                    <input type="submit" value="Edit"/>
                </form>
                <form action="deleteUser.do" method="post" style="display:inline;">
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

    <h3>Add New User</h3>
    <div style="color:red">
        <html:errors />
    </div>

    <html:form action="/addUser">
        
        <label>
            <bean:message key="label.user.firstName" /> :
            <html:text property="firstName" size="25" />
        </label>
        <br>
        <label>
            <bean:message key="label.user.lastName" /> :
            <html:text property="lastName" size="25" />
        </label>
        <br>
        <label>
            <bean:message key="label.user.email" /> :
            <html:text property="email" size="25" />
        </label>
        <br>
        <br>
        <html:submit>
            <bean:message key="label.user.button.submit" />
        </html:submit>
    </html:form>
</body>
</html>
