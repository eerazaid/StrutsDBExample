<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ page import="com.jwt.struts.form.UserRegisterForm" %>

<html>
<head>
    <title>Update User</title>
</head>
<body>
    <h2>Update User</h2>
    <form action="userList.do">
		<input type="submit" value="Back">
	</form>
    
    <div style="color:red;">
        <html:errors/>
    </div>

    <!-- Form for updating user information -->
    <html:form action="/updateUser" method="post">
        <html:hidden property="id"/>
        
        <label for="firstName">
            <bean:message key="label.user.firstName" /> :
            <html:text property="firstName" size="25" />
        </label>
        <br>
        <label for="lastName">
            <bean:message key="label.user.lastName" /> :
            <html:text property="lastName" size="25" />
        </label>
        <br>
        <label for="email">
            <bean:message key="label.user.email" /> :
            <html:text property="email" size="25" />
        </label>
        <br>
        <label>
            <bean:message key="label.user.currentPassword" /> :
            <html:text property="password" size="25" />
        </label>
        <br>
        <label>
            <bean:message key="label.user.newPassword" /> :
            <html:text property="password" size="25" />
        </label>
        <br><br>
        
        <html:submit>
            <bean:message key="label.user.button.submit" />
        </html:submit>
    </html:form>
</body>
</html>
