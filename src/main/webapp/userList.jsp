<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jwt.struts.form.UserRegisterForm" %>
<jsp:include page="modal/modal_delete_user.jsp" />
<jsp:include page="/inc/header.jsp" />

<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
</head>
<body>

<div class="wrapper"  style="margin-top: 100px;">
    <div class="list">
        <div class="title">
            User List
        </div><br>

        

        <div class="table_container">
            <table id="userTable" class="display" style="width:100%">
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
                    <td><%= user.getCurrentPassword() %></td>
                    <td class="actions">
                        <div class="button-group">
                            <form action="updateUser.do" method="get" class="inline-form">
                                <input type="hidden" name="id" value="<%= user.getId() %>"/>
                                <input type="submit" value="Edit" class="submit_btn"/>
                            </form>
                            <button type="button" class="submit_btn delete_btn" onclick="openDeleteModal(<%= user.getId() %>);">Delete</button>
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
        </div>

        <div class="button-container" style="text-align:right;">
            <form action="addUser.jsp" method="get">
                <input type="submit" value="Add New User" class="submit_btn"/>
            </form>
        </div>
    </div>
</div>


<jsp:include page="/inc/footer.jsp" />
</body>
</html>
