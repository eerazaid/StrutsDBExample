<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ page import="com.jwt.struts.form.UserRegisterForm" %>
<jsp:include page="/inc/header.jsp" />

<html>
<head>
    <title>Add New User</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
    <div class="wrapper">
        <div class="registration_form">
            <div class="title">
                Add New User
            </div>
            <div style="color:red;">
                <html:errors />
            </div>

            

            <div class="form_wrap">
                <html:form action="/addUser">
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.firstName" /> :
                            <html:text property="firstName" size="25" />
                        </label>
                    </div>
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.lastName" /> :
                            <html:text property="lastName" size="25" />
                        </label>
                    </div>
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.email" /> :
                            <html:text property="email" size="25" />
                        </label>
                    </div>
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.loginId" /> :
                            <html:text property="loginId" size="25" />
                        </label>
                    </div>
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.password" /> :
                            <html:text property="password" size="25" />
                        </label>
                    </div>
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.confirmPassword" /> :
                            <html:text property="confirmPassword" size="25" />
                        </label>
                    </div>

                    <div class="input_wrap">
					    <input type="submit" value="<bean:message key='label.user.button.submit' />" class="submit_btn"/>
					</div>
					

                </html:form>
                
                <form action="userList.do">
                	<input type="submit" value="Back" class="submit_btn"/>
            	</form>
            </div>
        </div>
    </div>
</body>

<jsp:include page="/inc/footer.jsp" />

</html>
