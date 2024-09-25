<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
<head>
    <title>Add New User</title>
</head>
<body>
    <h2>Add New User</h2>
    <html:form action="/addUser">
        First Name: <html:text property="firstName"/><br>
        Last Name: <html:text property="lastName"/><br>
        Email: <html:text property="email"/><br>
        <html:submit value="Add User"/>
    </html:form>
</body>
</html>
